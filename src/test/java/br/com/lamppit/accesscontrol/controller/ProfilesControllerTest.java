package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.repository.ProfileRepository;
import br.com.lamppit.accesscontrol.repository.SystemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfilesControllerTest {

    @Autowired
    private ProfilesController profilesController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;


    RandomConfigure randomConfigure = new RandomConfigure();
//
//    @Test
//    void SystemControllerTest() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//    }
//
//
//    @Test
//    void contextLoads() throws Exception {
//        assertThat(profilesController).isNotNull();
//    }
//
//
//    @Test
//    public void createProfileIsSuccess() throws Exception {
//       Profile profile = Profile.builder()
//               .id(randomConfigure.randomLong())
//               .name(randomConfigure.randomNames())
//               .build();
//       String json = objectMapper.writeValueAsString(profile);
//
//         this.mockMvc.perform(post("/profile/")
//                 .contentType("application/json")
//                 .content(json))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").exists());
//
//    }
//
//    @Test
//    public void createProfileIsFailWhenIdIsNotFound() throws Exception {
//        Profile profile = Profile.builder()
//                .name(randomConfigure.randomNames())
//                .build();
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(post("/profile/")
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void createProfileIsFailWhenNameIsNotFound() throws Exception {
//        Profile profile = Profile.builder()
//                .id(randomConfigure.randomLong())
//                .build();
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(post("/profile/")
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void createProfileIsFailWhenNameIsEmpty() throws Exception {
//        Profile profile = Profile.builder()
//                .id(randomConfigure.randomLong())
//                .name("")
//                .build();
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(post("/profile/")
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getProfileIsSuccess() throws Exception {
//        this.mockMvc.perform(get("/profile/"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getProfileByIdIsSuccess() throws Exception {
//        Profile profile = profileRepository.findAll().get(0);
//        Long id = profile.getId();
//        this.mockMvc.perform(get("/profile/" + id))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getProfileIsFailWhenIdIsNotFound() throws Exception {
//        this.mockMvc.perform(get("/profile/0"))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateProfileIsSuccessWhenModifyName() throws Exception {
//        Profile profile = profileRepository.findAll().get(0);
//        Long id = profile.getId();
//        String name = randomConfigure.randomNames();
//        profile.setName(name);
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(put("/profile/" + id)
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(name));
//    }
//
//    @Test
//    public void updateProfileIsFailWhenIdIsNotFound() throws Exception {
//        this.mockMvc.perform(put("/profile/0")
//                .contentType("application/json"))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void updateProfileIsFailWhenNameIsEmpty() throws Exception {
//        Profile profile = profileRepository.findAll().get(0);
//        Long id = profile.getId();
//        profile.setName("");
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(put("/profile/" + id)
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void updateProfileIsFailWhenNameIsNull() throws Exception {
//        Profile profile = profileRepository.findAll().get(0);
//        Long id = profile.getId();
//        profile.setName(null);
//        String json = objectMapper.writeValueAsString(profile);
//
//        this.mockMvc.perform(put("/profile/" + id)
//                .contentType("application/json")
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void deleteProfileIsSuccess() throws Exception {
//        Profile profile = profileRepository.findAll().get(0);
//        Long id = profile.getId();
//        this.mockMvc.perform(delete("/profile/" + id))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteProfileIsFailWhenIdIsNotFound() throws Exception {
//        this.mockMvc.perform(delete("/profile/0"))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }


}