package com.itm.space.backendresources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itm.space.backendresources.api.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false);

    @Test
    public void testHandleMethodArgumentNotValidException() throws Exception {
        String userRequestJson = objectMapper.writeValueAsString(new UserRequest("", "invalid", "test", "Vit", "User"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Username should be between 2 and 30 characters long"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("Email should be valid"));
    }

    @Test
    public void testHandleBackendResourcesException() throws Exception {
        String invalidUserId = "invalid";

        mockMvc.perform(get("/api/users/" + invalidUserId))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}
