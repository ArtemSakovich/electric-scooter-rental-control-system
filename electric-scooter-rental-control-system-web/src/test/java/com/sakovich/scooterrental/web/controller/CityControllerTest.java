package com.sakovich.scooterrental.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.model.dto.CityDto;
import com.sakovich.scooterrental.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class CityControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    private final CityDto cityDto = CityDto.builder().id(1L).name("Hrodna").build();

    @Test
    public void shouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/api/cities")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundStatus() throws Exception {
        this.mockMvc.perform(get("/api/city")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUnauthorizedStatus() throws Exception {
        when(cityService.getById(1L)).thenReturn(cityDto);
        this.mockMvc.perform(get("/api/user/cities/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void shouldReturnForbiddenStatus() throws Exception {
        when(cityService.addCity(cityDto)).thenReturn(cityDto);
        this.mockMvc.perform(post("/api/manager/city")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void shouldReturnExpectedJsonFromAddCity() throws Exception {
        when(cityService.addCity(cityDto)).thenReturn(cityDto);
        this.mockMvc.perform(post("/api/manager/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cityDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Hrodna'}"));
    }

    @Test
    public void shouldReturnExpectedJsonFromGetAll() throws Exception {
        List<CityDto> cities = new ArrayList<>();
        cities.add(cityDto);
        when(cityService.getAll()).thenReturn(cities);
        this.mockMvc.perform(get("/api/cities")).andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Hrodna'}]"));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void shouldReturnExpectedJsonFromGetById() throws Exception {
        when(cityService.getById(1L)).thenReturn(cityDto);
        this.mockMvc.perform(get("/api/user/cities/1")).andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'Hrodna'}"));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void shouldReturnExpectedJsonFromUpdate() throws Exception {
        CityDto updatedCityDto = CityDto.builder().id(1L).name("HrodnaUpdated").build();
        when(cityService.update(updatedCityDto)).thenReturn(updatedCityDto);
        this.mockMvc.perform(put("/api/manager/city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updatedCityDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'HrodnaUpdated'}"));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void shouldReturnExpectedJsonFromDelete() throws Exception {
        this.mockMvc.perform(delete("/api/manager/city/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="manager",roles={"MANAGER"})
    public void shouldReturnExpectedErrorJsonFromGetById() throws Exception {
        when(cityService.getById(2L)).thenThrow(new OperationCancelledException("City was not found!"));
        this.mockMvc.perform(get("/api/user/cities/2")).andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'City was not found!','httpStatus':'BAD_REQUEST',"));
    }

    private String toJson(CityDto cityDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(cityDto);
    }
}
