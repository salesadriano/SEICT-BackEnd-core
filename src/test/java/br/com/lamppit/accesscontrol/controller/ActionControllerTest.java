package br.com.lamppit.accesscontrol.controller;


import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Action;
import br.com.lamppit.accesscontrol.repository.ActionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActionControllerTest {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RandomConfigure randomConfigure = new RandomConfigure();

    public ActionControllerTest(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(actionRepository).isNotNull();
    }

    @Test
    public void getAllActions() throws Exception {
        this.mockMvc.perform(get("/action/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createAction() throws Exception {

        Action action = Action.builder()
                .id(randomConfigure.randomLong())
                .name("Teste2")
                .description("Teste2")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createActionFailWithIdIsNull() throws Exception {

        Action action = Action.builder()
                .name("Teste")
                .description("Teste")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createActionFailWithNameIsEmpty() throws Exception {

        Action action = Action.builder()
                .name("")
                .description("Teste")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(400);
                });
    }

    @Test
    public void createActionFailWithNameIsNull() throws Exception {

        Action action = Action.builder()
                .name(null)
                .description("Teste")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(400);
                });
    }

    @Test
    public void updateAction() throws Exception {

        Action action = actionRepository.findAll().get(0);

        action.setName(randomConfigure.randomNames());


        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(put("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateActionFailWithNameIsEmpty() throws Exception {

        Action action = Action.builder()
                .id(randomConfigure.randomLong())
                .name("Teste")
                .description("Teste")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        action.setName("");
        Long id = action.getId();
        String url = "/action/" + id;

        json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(400);
                });
    }

    @Test
    public void updateActionFailWithNameIsNull() throws Exception {

        Action action = Action.builder()
                .id(randomConfigure.randomLong())
                .name("Teste")
                .description("Teste")
                .build();

        String json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(post("/action/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        action.setName(null);
        Long id = action.getId();
        String url = "/action/" + id;

        json = objectMapper.writeValueAsString(action);

        this.mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(400);
                });
    }

    @Test
    public void deleteAction() throws Exception {

        Action action = this.actionRepository.findAll().get(0);
        Long id = action.getId();
        String url = "/action/" + id;

        this.mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteActionIsFail() throws Exception {

        this.mockMvc.perform(delete("/action/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}