package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.repository.UserRepository;
import br.com.lamppit.core.dto.ExceptionDTO;
import br.com.lamppit.core.exception.EntityValidationException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest{
    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RandomConfigure randomConfigure = new RandomConfigure();

    public UserControllerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        Integer id = generateNumber();
        this.mockMvc.perform(get("/user/" + id)).andDo(print()).andExpect(status().isNotFound());
//				.andExpect(result -> assertTrue(result.getResponse().getContentAsString()));
    }

    @Test
    public void creationUserSuccefull() throws Exception {

        User user = User.builder().password("123456")
                .name(randomConfigure.randomNames())
                .email(randomConfigure.randomNames() + "@email.com")
                .build();

        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void creationUserFailsRequiredFields() throws Exception {
        User user = User.builder().build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    public void creationUserFailsWhenNameIsNull() throws Exception {
        User user = User.builder()
                .password("123456")
                .name(null)
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    private int generateNumber() {
        return (int) Math.random();
    }

    @Test
    public void createUserFailsLoginAlreadyExists() throws Exception {
        User user = User.builder()
                .password("123456")
                .name(randomConfigure.randomNames())
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityValidationException));
    }

    @Test
    void createUserFailWhenPasswordIsNull() throws Exception {
        User user = User.builder()
                .password(null)
                .name(randomConfigure.randomNames())
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void createUserFailWhenNameContainsNumbers() throws Exception {
        User user = User.builder()
                .password("123456")
                .name("123")
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void createUserFailWhenNameContainsSpecialCharacters() throws Exception {
        User user = User.builder()
                .password("123456")
                .name("Andre!")
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void createUserFailWhenNameSmallerThan3() throws Exception {
        User user = User.builder()
                .password("123456")
                .name("AB")
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void createUserFailWhenNameIsEmpty() throws Exception {
        User user = User.builder()
                .password("123456")
                .name("")
                .email(randomConfigure.randomNames() + "@email.com")
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void createUserFailEmailIsNull() throws Exception {
        User user = User.builder()
                .password("123456")
                .name(randomConfigure.randomNames())
                .email(null)
                .build();
        String userJson = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserSuccess() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = randomConfigure.randomNames();
        user.setName(name);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateUserFailWhenPasswordIsNull() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = randomConfigure.randomNames();
        user.setName(name);
        user.setPassword(null);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserFailWhenEmailIsNull() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = randomConfigure.randomNames();
        user.setName(name);
        user.setEmail(null);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserFailWhenNameIsEmpty() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = "";
        user.setName(name);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserFailWhenNameContainsNumbers() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = "123";
        user.setName(name);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserFailWhenNameContainsSpecialCharacters() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = "!@#$%^&*()_+";
        user.setName(name);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }

    @Test
    void updateUserFailWhenNameSmallerThan3Characters() throws Exception {
        User user = userRepository.findAll().get(0);
        String name = "AB";
        user.setName(name);
        String json = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(
                        objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class)
                                .getErrors().isEmpty()));
    }
}
