package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Action;
import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.ProfileSystems;
import br.com.lamppit.accesscontrol.repository.ActionRepository;
import br.com.lamppit.accesscontrol.repository.ProfileActionsRepository;
import br.com.lamppit.accesscontrol.repository.ProfileRepository;
import br.com.lamppit.accesscontrol.repository.ProfileSystemsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileActionsControllerTest {

    @Autowired
    private ProfileActionsController profileActionsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfileSystemsRepository profileSystemsRepository;

    @Autowired
    private ActionRepository actionRepository;

    private RandomConfigure randomConfigure = new RandomConfigure();



    @Test
    void contextLoads() throws Exception {
        assertThat(profileActionsController).isNotNull();
    }

    @Test
    void ProfileActionsControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createProfileActions() throws Exception {

       ProfileActions profileActions = ProfileActions.builder()
                .profileSystems(profileSystemsRepository.findById(2L).get())
                .action(actionRepository.findById(2L).get())
                .build();
        String json = objectMapper.writeValueAsString(profileActions);
        this.mockMvc.perform(post("/profileActions/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk());
    }

}