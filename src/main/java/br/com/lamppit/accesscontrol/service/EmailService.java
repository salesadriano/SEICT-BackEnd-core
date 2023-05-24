package br.com.lamppit.accesscontrol.service;

import br.com.lamppit.core.dto.EmailPasswordDTO;
import br.com.lamppit.core.dto.UploadResponseDTO;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    private final String URL = "https://email-service-usvc6en2sq-uk.a.run.app/mail";

    private static final RestTemplate restTemplate = new RestTemplate();
 
    public void sendEmailRecover(EmailPasswordDTO email) throws Exception {
        JsonMapper mapper = new JsonMapper();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            var entity = new HttpEntity<>(mapper.writeValueAsString(email), headers);
            restTemplate.postForObject(URL + "/newPassword", entity, UploadResponseDTO.class);
        } catch (Exception e) {
            throw new Exception("Erro ao enviar email");
        }
    }

}
