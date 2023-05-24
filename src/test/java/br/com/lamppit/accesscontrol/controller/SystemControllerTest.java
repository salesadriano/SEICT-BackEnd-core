package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.repository.SystemRepository;
import br.com.lamppit.core.dto.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest {

    @Autowired
    private SystemController systemController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SystemRepository systemRepository;


    RandomConfigure randomConfigure = new RandomConfigure();

    List<Systems> systemsList;

    @Test
    void SystemControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        assertThat(systemController).isNotNull();
    }

    public SystemControllerTest() {
        this.systemsList = new ArrayList<>();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(systemController).isNotNull();
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        Integer id = generateNumber();
        this.mockMvc.perform(get("/system/" + id)).andDo(print()).andExpect(status().isNotFound());
    }

    private Integer generateNumber() {
        return (int) Math.random();
    }

    @Test
    public void getAllSystemsSuccefull() throws Exception {
        this.mockMvc.perform(get("/systems/")).andDo(print()).andExpect(status().isOk());




    }

    @Test
    public void getSystemsFailed() throws Exception {
        this.mockMvc.perform(get("/systems/963")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void creationSystemSuccefull() throws Exception {
        Systems system = Systems.builder().name("Teste")
                .initials("TST")
                .database("Oracle")
                .id(randomConfigure.randomLong()).build();

        systemsList.add(system);
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());


    }

    @Test
    public void creationSystemFailedWithEmptyName() throws Exception {
        Systems system = Systems.builder().name("").initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemWithNameAlreadyExists() throws Exception {
        Systems system = this.systemRepository.findAll().get(0);

        Systems system2 = Systems.builder().name(system.getName()).initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system2);

        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemWithNameSmallerThan3() throws Exception {
        Systems system = Systems.builder().name("ab").initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemWithNameContainNumbers() throws Exception {
        Systems system = Systems.builder().name("ab1").initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithNameContainSpecialCharacters() throws Exception {
        Systems system = Systems.builder().name("ab@").initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithEmptyInitials() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials("").build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithNullInitials() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials(null).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithNullDatabase() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials(randomConfigure.randomNames()).database(null).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithNoDatabaseInsert() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials(randomConfigure.randomNames()).build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationSystemFailedWithEmptyDatabase() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials(randomConfigure.randomNames()).database("").build();
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }


    @Test
    public void updateSystemsSuccefull() throws Exception {
        Systems system = Systems.builder().name(randomConfigure.randomNames()).initials(randomConfigure.randomNames()).database(randomConfigure.randomNames()).id(randomConfigure.randomLong()).build();
        system.setDatabase("MySQL");
        String systemJson = objectMapper.writeValueAsString(system);
        this.mockMvc.perform(post("/systems").contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.database").value("MySQL"));
    }


    @Test
    public void updateSystemsFailedWithEmptyName() throws Exception {
        Systems system = this.systemRepository.findAll().get(0);
        system.setName("");
        String systemJson = objectMapper.writeValueAsString(system);
        Long id = system.getId();
        String url = "/systems/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateSystemsFailedWithNullName() throws Exception {
        Systems system = this.systemRepository.findAll().get(0);
        system.setName(null);
        String systemJson = objectMapper.writeValueAsString(system);
        Long id = system.getId();
        String url = "/systems/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateSystemsFailedWithNullInitials() throws Exception {
        Systems system = this.systemRepository.findAll().get(0);
        system.setInitials(null);
        String systemJson = objectMapper.writeValueAsString(system);
        Long id = system.getId();
        String url = "/systems/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateSystemsFailedWithEmptyInitials() throws Exception {
        Systems system = this.systemRepository.findAll().get(0);
        system.setInitials("");
        String systemJson = objectMapper.writeValueAsString(system);
        Long id = system.getId();
        String url = "/systems/" + id;
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(systemJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }


    @Test
    public void deleteSystemsSuccefull() throws Exception {
           Systems system = this.systemRepository.findAll().get(0);
           String json = objectMapper.writeValueAsString(system);
           Long id = system.getId();
           String url = "/systems/" + id;
                this.mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isOk());
    }


    @Test
    public void deleteSystemsFailed() throws Exception {
        this.mockMvc.perform(delete("/systems/6365")).andDo(print()).andExpect(status().isNotFound());
    }


}