package br.com.lamppit.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonMapperUtil {

        public static String mapToJson(Object object) throws Exception {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.findAndRegisterModules();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new Exception(e);
            }
        }

        public static <T> T mapFromJson(String json, Class<T> clazz) throws Exception {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(json, clazz);
            } catch (JsonProcessingException e) {
                throw new Exception(e);
            }
        }
}
