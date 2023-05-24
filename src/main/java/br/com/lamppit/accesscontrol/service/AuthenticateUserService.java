package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.accesscontrol.model.AuthenticationUser;
import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.model.dto.ActionPermissionDTO;
import br.com.lamppit.accesscontrol.model.dto.ChangePasswordParamDTO;
import br.com.lamppit.accesscontrol.model.dto.UserLoginDto;
import br.com.lamppit.accesscontrol.model.enumerated.ImportantRoles;
import br.com.lamppit.accesscontrol.repository.*;
import br.com.lamppit.accesscontrol.util.JsonMapperUtil;
import br.com.lamppit.accesscontrol.util.JwtUtilities;
import br.com.lamppit.accesscontrol.util.Utils;
import br.com.lamppit.core.dto.EmailPasswordDTO;
import br.com.lamppit.core.dto.MessageDTO;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthenticateUserService extends ServiceJpa<AuthenticationUser, Long> {

    @Autowired
    private AuthenticationUserRepository authenticationUserRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileUserRepository profileUserRepository;

    @Autowired
    private ProfileActionsRepository profileActionsRepository;

    @Autowired
    private ExtraPermissionsRepository extraPermissionsRepository;

    @Autowired
    private ResponsibleAreaRepository responsibleAreaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private SystemRepository systemRepository;

    @Override
    public AuthenticationUserRepository getRepository() {
        return authenticationUserRepository;
    }

    @Override
    protected void validateSave(AuthenticationUser entity) throws EntityValidationException {

    }

    @Override
    protected void validateUpdate(AuthenticationUser entity) throws EntityValidationException {

    }

    @Override
    protected void init() {

    }


    public MessageDTO recoverPassword(String email, Long systemId) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EntityValidationException("Email não cadastrado");
        }

        sendEmailToResetPassword(user.getId(), systemId);
        return new MessageDTO("Email enviado com sucesso");
    }

    //Método para comunicar com email

    public void sendEmailToResetPassword(Long id, Long systemId) throws Exception {

        AuthenticationUser authenticationUser = gerarToken(id);

        EmailPasswordDTO email = new EmailPasswordDTO();


        Systems system = systemRepository.findById(systemId).get();

        boolean isPresent = systemRepository.findById(systemId).isPresent();

        if (!isPresent) {
            throw new EntityValidationException("Sistema não encontrado");
        }

        String urlSystem = system.getHost();
        String systemName = system.getName();

//        String urlHost = "https://appcds-dev.lampp-it.com.br/";

        email.setName(authenticationUser.getUser().getName());
        email.setSystem(systemName);
        email.setDateOcorrence(Utils.getDateTimeFormat());
        email.setUrl(urlSystem + "/reset-password?id="
                + authenticationUser.getUser().getId() + "&token="
                + authenticationUser.getToken());
        email.setEmail(authenticationUser.getUser().getEmail());
        emailService.sendEmailRecover(email);

    }


    public AuthenticationUser gerarToken(Long id) throws Exception {

        User user = userRepository.findById(id).get();

        AuthenticationUser authenticationUser = new AuthenticationUser();

        authenticationUser.setUser(user);

        authenticationUser.setToken(encoder.encode(System.currentTimeMillis() + user.toString()));

        authenticationUser.setUsed(false);

        return authenticationUserRepository.save(authenticationUser);
    }


    public UserLoginDto login(String email, String password, Long systemId) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new EntityValidationException("Email não cadastrado");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new EntityValidationException("Senha incorreta");
        }

        UserLoginDto userLoginDto = new UserLoginDto();
        /// Carregar Todas as Actions dos perfis + Extra Permissions menos as que estão revogadas

        List<Profile> profiles = profileUserRepository.getProfilesByUserAndSystems(user.getId(), systemId);
        userLoginDto.setProfiles(profiles);

        List<ActionPermissionDTO> actions = profileActionsRepository.getActionsPermited(profiles, user.getId(), systemId);
        userLoginDto.setActions(actions);

        List<ActionPermissionDTO> extraPermissions = extraPermissionsRepository.getExtraPermissionsByUserAndSystems(user.getId(), systemId);
        userLoginDto.getActions().addAll(extraPermissions);

        userLoginDto.setUser(user);

        boolean isManager = responsibleAreaRepository.existsByManager_Id(user.getId());

        userLoginDto.setManager(isManager);

        if (user.getRole() != null && user.getRole().getImportantRoles() != null) {
            boolean isRhManager = user.getRole().getImportantRoles().equals(ImportantRoles.RH_MANAGER);
            userLoginDto.setRhManager(isRhManager);
        } else {
            userLoginDto.setRhManager(false);
        }

        String jsonReturn = JsonMapperUtil.mapToJson(userLoginDto);

        String token = jwtUtilities.createJWT(user.getId().toString(), user.getEmail(), jsonReturn, new Date().getTime() + 3600000);

        userLoginDto.setToken(token);

        return userLoginDto;

    }

    public MessageDTO changePassword(ChangePasswordParamDTO param) {

        AuthenticationUser authenticationUser = authenticationUserRepository.findByToken(param.getToken());

        if (authenticationUser == null) {
            throw new EntityValidationException("Link inválido");
        }

        if (authenticationUser.isUsed()) {
            throw new EntityValidationException("Link já utilizado");
        }

        User user = authenticationUser.getUser();

        if (user.getId() != param.getUserId()) {
            throw new EntityValidationException("Link inválido");
        }

        user = userRepository.findById(param.getUserId()).get();

        user.setPassword(encoder.encode(param.getPassword()));

        userRepository.save(user);

        authenticationUser.setUsed(true);

        authenticationUserRepository.save(authenticationUser);

        return new MessageDTO("Senha alterada com sucesso");


    }

    public AuthenticationUser findFirstByOrderByIdDesc(Long userId) {
        Pageable first = PageRequest.of(0, 1);
        List<AuthenticationUser> authenticationUsers = authenticationUserRepository.getLastByUser_Id(userId, first);
        return authenticationUsers.get(0);
    }
}
