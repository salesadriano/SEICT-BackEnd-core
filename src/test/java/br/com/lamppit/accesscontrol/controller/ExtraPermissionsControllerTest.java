package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.repository.ExtraPermissionsRepository;
import br.com.lamppit.accesscontrol.repository.ProfileActionsRepository;
import br.com.lamppit.accesscontrol.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExtraPermissionsControllerTest {

    @Autowired
    private ExtraPermissionsRepository extraPermissionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileActionsRepository profileActionsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RandomConfigure randomConfigure = new RandomConfigure();

    @Test
    void contextLoads() throws Exception {
        assertThat(extraPermissionsRepository).isNotNull();
    }

    @Test
    void ExtraPermissionsControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllExtraPermissions() throws Exception {
        this.mockMvc.perform(get("/extraPermissions/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getExtraPermissionById() throws Exception {
        this.mockMvc.perform(get("/extraPermissions/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createExtraPermission() throws Exception {
        ExtraPermissions extraPermissions = ExtraPermissions.builder()
                .user(userRepository.findAll().get(0))
                .profileAction(profileActionsRepository.findAll().get(1))
                .build();

        String json = objectMapper.writeValueAsString(extraPermissions);

        this.mockMvc.perform(post("/extraPermissions/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateExtraPermission() throws Exception {
        ExtraPermissions extraPermissions = extraPermissionsRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        extraPermissions.setUser(user);

        String json = objectMapper.writeValueAsString(extraPermissions);
        this.mockMvc.perform(post("/extraPermissions/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(user.getId()));
    }

}