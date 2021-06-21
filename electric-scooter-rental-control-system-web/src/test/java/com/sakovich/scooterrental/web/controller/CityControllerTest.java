package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.dto.CityDto;
import com.sakovich.scooterrental.service.CityService;
import com.sakovich.scooterrental.web.security.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CityControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    CityService cityService;

    MockMvc mockMvc;

    UserDetails user;
    UserDetails manager;
    UserDetails admin;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        user = UserDetailsImpl.builder().authorities(Collections.singletonList(new SimpleGrantedAuthority("USER"))).build();
        manager = UserDetailsImpl.builder().authorities(Collections.singletonList(new SimpleGrantedAuthority("MANAGER"))).build();
        admin = UserDetailsImpl.builder().authorities(Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))).build();
    }

    @WithAnonymousUser
    @Test
    void addCityUnauthorized() throws Exception {
        mockMvc.perform(post("/api/manager/city"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addCityManager() throws Exception {
        mockMvc.perform(post("/api/manager/city")
                .with(user(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/api/cities")
                .with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/api/manager/cities/1")
                .with(user(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getByIdNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/manager/cities/1")
                .with(user(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(get("/api/manager/city")
                .with(user(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/manager/city/1"))
                .andExpect(status().isUnauthorized());
    }
}