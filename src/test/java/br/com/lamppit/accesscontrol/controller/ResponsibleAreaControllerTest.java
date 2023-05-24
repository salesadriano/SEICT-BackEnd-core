package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.ResponsibleArea;
import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.repository.ResponsibleAreaRepository;
import br.com.lamppit.accesscontrol.repository.UserRepository;
import br.com.lamppit.core.dto.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jsoup.HttpStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResponsibleAreaControllerTest {

    @Autowired
    private ResponsibleAreaController responsibleAreaController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private RandomConfigure randomConfigure = new RandomConfigure();

    @Autowired
    private ResponsibleAreaRepository responsibleAreaRepository;

    @Test
    void ResponsibleAreaControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(responsibleAreaController).isNotNull();
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        Integer id = generateNumber();
        this.mockMvc.perform(get("/responsiblearea/" + id)).andDo(print()).andExpect(status().isNotFound());
    }

    private int generateNumber() {
        return (int) Math.random();
    }


    @Test
    public void creationResponsibleAreaSuccefull() throws Exception {
        User user = userRepository.findById(2L).get();

        ResponsibleArea responsibleArea = ResponsibleArea.builder()
                .id(randomConfigure.randomLong())
                .nameArea(randomConfigure.randomNames())
                .manager(user)
                .build();
        String json = objectMapper.writeValueAsString(responsibleArea);

        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }


    @Test
    public void creationResponsibleAreaFailed() throws Exception {
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea(randomConfigure.randomNames()).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));

    }

    @Test
    public void createResponsibleAreaWithNameAreaNull() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea(null).manager(userBase).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createResponsibleAreaWithNameAreaIsEmpty() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea("").manager(userBase).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createResponsibleAreaWithAccountableNull() throws Exception {
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea(randomConfigure.randomNames()).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createResponsibleAreaWithAccountableNameContainNumbers() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = ResponsibleArea.builder()
                .id(randomConfigure.randomLong())
                .nameArea(randomConfigure.randomNames())
                .manager(userBase)
                .build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createResponsibleAreaWithAccountableNameContainSpecialCharacters() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = ResponsibleArea.builder()
                .id(randomConfigure.randomLong())
                .nameArea(randomConfigure.randomNames())
                .manager(userBase).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createResponsibleAreaWithAccountableNameIsEmpty() throws Exception {
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea(randomConfigure.randomNames()).manager(null).build();
        String responsibleAreaJson = objectMapper.writeValueAsString(responsibleArea);
        this.mockMvc.perform(post("/responsibleArea").contentType(MediaType.APPLICATION_JSON).content(responsibleAreaJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }



    @Test
    public void getAllResponsibleArea() throws Exception {
        this.mockMvc.perform(get("/responsibleArea"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getResponsibleAreaById() throws Exception {
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getResponsibleAreaFail() throws Exception {
        this.mockMvc.perform(get("/responsibleArea/0")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void updateNameOfResponsibleArea() throws Exception {
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        responsibleArea.setNameArea(randomConfigure.randomNames());
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateResponsibleAreaWithNameAreaIsNull() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = ResponsibleArea.builder().nameArea("Variavel").manager(userBase).build();
        responsibleArea.setNameArea(null);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateResponsibleAreaWithAccountableNameContainNumbers() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        responsibleArea.setNameArea(randomConfigure.randomNames());
        responsibleArea.setManager(userBase);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateResponsibleAreaWithAccountableNameContainSpecialCharacters() throws Exception {
        User userBase = userRepository.findAll().get(0);
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        responsibleArea.setNameArea(randomConfigure.randomNames());
        responsibleArea.setManager(userBase);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateResponsibleAreaWithAccountableNameIsEmpty() throws Exception {
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        responsibleArea.setNameArea(randomConfigure.randomNames());
        responsibleArea.setManager(null);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateResponsibleAreaWithAccountableNameIsNull() throws Exception {
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        responsibleArea.setNameArea(randomConfigure.randomNames());
        responsibleArea.setManager(null);
        String json = objectMapper.writeValueAsString(responsibleArea);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void deleteResponsibleArea() throws Exception {
        ResponsibleArea responsibleArea = this.responsibleAreaRepository.findAll().get(0);
        Long id = responsibleArea.getId();
        String url = "/responsibleArea/" + id;
        this.mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteResponsibleAreaFail() throws Exception {
        this.mockMvc.perform(delete("/responsibleArea/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}