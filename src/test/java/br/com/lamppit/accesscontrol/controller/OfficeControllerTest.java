package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.configure.RandomConfigure;
import br.com.lamppit.accesscontrol.model.Office;
import br.com.lamppit.accesscontrol.repository.OfficeRepository;
import br.com.lamppit.core.dto.ExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfficeControllerTest {

    @Autowired
    private OfficeController officeController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private RandomConfigure randomConfigure = new RandomConfigure();

    @Autowired
    private OfficeRepository officeRepository;

    @Test
    void OfficeControllerTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(officeRepository).isNotNull();
    }

    private int generateNumber() {
        return (int) Math.random();
    }

    @Test
    public void getAllOffices() throws Exception {
        this.mockMvc.perform(get("/office/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOfficeById() throws Exception {
        Long id = officeRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/office/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createOffice() throws Exception {
        Office office = Office.builder().name(randomConfigure.randomNames()).description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createOfficeFailWithNameIsNull() throws Exception {
        Office office = Office.builder().description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void createOfficeFWithNameIsEmpty() throws Exception {
        Office office = Office.builder().name("").description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateOfficeSuccess() throws Exception {
        Office office = Office.builder().name(randomConfigure.randomNames()).description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());

        office.setName(randomConfigure.randomNames());
        json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateOfficeFailWithNameIsNull() throws Exception {
        Office office = Office.builder().name(randomConfigure.randomNames()).description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());

        office.setName(null);
        json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void updateOfficeFailWithNameIsEmpty() throws Exception {
        Office office = Office.builder().name(randomConfigure.randomNames()).description(randomConfigure.randomNames()).build();
        String json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());

        office.setName("");
        json = objectMapper.writeValueAsString(office);
        this.mockMvc.perform(post("/office/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertFalse(objectMapper.readValue(result.getResponse().getContentAsString(), ExceptionDTO.class).getErrors().isEmpty()));
    }

    @Test
    public void deleteOfficeSuccess() throws Exception {
        Office office = this.officeRepository.findAll().get(0);
        Long id = office.getId();
        String url = "/office/" + id;
        this.mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOfficeFailWhenIdNotFound() throws Exception {
        this.mockMvc.perform(delete("/office/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}