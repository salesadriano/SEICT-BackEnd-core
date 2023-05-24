package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Roles;
import br.com.lamppit.accesscontrol.repository.RolesRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RolesControllerTest {

    @Autowired
    private RolesController rolesController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private RandomConfigure randomConfigure = new RandomConfigure();

    @Autowired
    private RolesRepository rolesRepository;

    @Test
    void rolesControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(rolesController).isNotNull();
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        Integer id = generateNumber();
        this.mockMvc.perform(get("/roles/" + id)).andDo(print()).andExpect(status().isNotFound());
    }

    private Integer generateNumber() {
        return (int) Math.random();
    }

    @Test
    public void creationRolesSuccefull() throws Exception {
        Roles roles = Roles.builder().name(randomConfigure.randomNames()).id(1L).build();
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void creationRolesFailedWithNoUniqueName() throws Exception {
        Roles roles = Roles.builder().name(randomConfigure.randomNames()).id(1L).build();
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
        Roles roles2 = Roles.builder().name(roles.getName()).id(2L).build();
        String rolesJson2 = objectMapper.writeValueAsString(roles2);
        this.mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON).content(rolesJson2))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void creationRolesFailedWithNullName() throws Exception {
        Roles roles = Roles.builder().name(null).id(1L).build();
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void creationRolesFailedWithEmptyName() throws Exception {
        Roles roles = Roles.builder().name("").id(1L).build();
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }




    @Test
    public void updateRolesNameSuccefull() throws Exception {
        Roles roles = Roles.builder().name(randomConfigure.randomNames()).id(1L).build();
        roles.setName(randomConfigure.randomNames());
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void updateRolesNameFailedWithNullName() throws Exception {
        Roles roles = Roles.builder().name(randomConfigure.randomNames()).id(1L).build();
        roles.setName(null);
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateRolesNameFailedWithEmptyName() throws Exception {
        Roles roles = Roles.builder().name("Cargo1").id(1L).build();
        roles.setName("");
        String rolesJson = objectMapper.writeValueAsString(roles);
        this.mockMvc.perform(put("/roles/1").contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void deleteRolesSuccefull() throws Exception {
        Roles roles = this.rolesRepository.findAll().get(0);
        String rolesJson = objectMapper.writeValueAsString(roles);
        Long id = roles.getId();
        String url = "/roles/" + id;

        this.mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(rolesJson))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void deleteRolesFailed() throws Exception {
        this.mockMvc.perform(delete("/roles/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}


