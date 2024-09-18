package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.BaseIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.space.backendresources.api.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Vit", roles = "MODERATOR")
    public void testCreateUser() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest userRequest = new UserRequest("test" + uniqueSuffix, "test" + uniqueSuffix + "@example.com", "password", "Test", "Test");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "Vit", roles = "MODERATOR")
    public void testGetUserById() throws Exception {
        UUID id = UUID.fromString("28d57efc-a828-4fb9-8b12-e442acc27b70");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "Vit", roles = "MODERATOR")
    public void testHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vit"));
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
