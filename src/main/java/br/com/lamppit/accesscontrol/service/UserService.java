package br.com.lamppit.accesscontrol.service;

import jakarta.annotation.PostConstruct;

import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.ProfileUser;
import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.model.dto.SystemsUserDTO;
import br.com.lamppit.accesscontrol.model.dto.UserAccesDTO;
import br.com.lamppit.accesscontrol.repository.ResponsibleAreaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.repository.UserRepository;
import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.service.ServiceJpa;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceJpa<User, Long> {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfileUserService profileUserService;

	@Autowired
	ProfileActionsService profileActionsService;

	@Autowired
	ResponsibleAreaUserRepository responsibleAreaUserRepository;

	public boolean existsByOffice_Id(Long id) {
		return userRepository.existsByOffice_Id(id);
	}

	@Override
	public JpaRepository<User, Long> getRepository() {
		return userRepository;
	}

	@Override
	protected void validateSave(User entity) throws EntityValidationException {
//		if (entity.getName().equals("root@gmail.com"))
//			throw new EntityValidationException(getMessageSource().getMessage("user.root", null, null));
	}

	@Override
	protected void validateUpdate(User entity) throws EntityValidationException {

	}

	@Override
	public void validateDelete(Long id) throws EntityValidationException {
		super.validateDelete(id);

		if (profileUserService.existsByUser_Id(id)) {
			throw new EntityValidationException(getMessageSource()
					.getMessage("entity.delete.has.dependency", null, null));
		}
		;

	}

	@Override
	@PostConstruct
	protected void init() {
		setCacheKey("user.cache.key");
		setEntityKey("user");
	}

	public List<User> findAllUsers() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	public boolean existsByRole_Id(Long id) {
		return userRepository.existsByRole_Id(id);
	}


	//método para validar se uma área responsável está associada a um usuário
	public boolean existsByUser_Id(Long id) {
		return responsibleAreaUserRepository.existsByUser_Id(id);
	}

	public List<UserAccesDTO> findAllAccess(Long userId) {

		List<UserAccesDTO> response = new ArrayList<UserAccesDTO>();

		//Listando todos os sistemas que o usuário tem acesso
		List<Systems> systems = profileUserService.getSystemsByUserId(userId);

		//Para cada sistema faça:
		systems.forEach(system -> {
			//Criar um novo objeto de acesso
			UserAccesDTO userAccesDTO = new UserAccesDTO();
			userAccesDTO.setSystems(system);

			//Lista todos os Perfis que o usuário tem acesso ao sistema
			List<SystemsUserDTO> profileSystems = new ArrayList<SystemsUserDTO>();
			List<ProfileUser> profileUsers = profileUserService.findByUser_IdAndProfileSystems_System_Id(userId, system.getId());

			profileUsers.forEach(profileUser -> {
				SystemsUserDTO systemsUserDTO = new SystemsUserDTO();

				//Setando perfil ao objeto de acesso
				systemsUserDTO.setProfile(profileUser.getProfileSystems().getProfile());

				//Setando todas as ações do perfil ao objeto de acesso
				Iterable<ProfileActions> actions = profileActionsService.findByProfileSystemsId(profileUser.getProfileSystems().getId());
				systemsUserDTO.setActions(actions);
				profileSystems.add(systemsUserDTO);
			});
			userAccesDTO.setProfileSystems(profileSystems);
			response.add(userAccesDTO);
		});

		return response;

	}
}
