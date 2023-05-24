package br.com.lamppit.core.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.lamppit.core.dto.FileDTO;
import br.com.lamppit.core.dto.UploadResponseDTO;

@Service
public class StorageService {
    private final String URL = "https://storage-service-usvc6en2sq-uk.a.run.app/storage";

    public UploadResponseDTO upload(FileDTO file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        JsonMapper mapper = new JsonMapper();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            var entity = new HttpEntity<>(mapper.writeValueAsString(file), headers);
            UploadResponseDTO response = restTemplate.postForObject(URL + "/upload", entity, UploadResponseDTO.class);
            return response;
        } catch (Exception e) {
            throw e;
        }

    }

    public FileDTO download(FileDTO file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        try {
            FileDTO response = restTemplate.getForObject(URL + "/download/" + file.getSystem() + "/" + file.getHash(),
                    FileDTO.class);
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(FileDTO file) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(URL + "/delete/" + file.getSystem() + "/" + file.getHash());
        } catch (Exception e) {
            throw e;
        }
    }
}
