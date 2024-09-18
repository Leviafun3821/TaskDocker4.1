package com.itm.space.backendresources.service;

import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(username = "Vit", roles = "MODERATOR")
    public void testCreateUser() {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest userRequest = new UserRequest("test" + uniqueSuffix, "test" + uniqueSuffix + "@example.com", "password", "Test", "Test");
        userService.createUser(userRequest);
    }

    @Test
    @WithMockUser(username = "Vit", roles = "MODERATOR")
    public void testGetUserById() {
        UUID id = UUID.fromString("28d57efc-a828-4fb9-8b12-e442acc27b70");
        UserResponse userResponse = userService.getUserById(id);
        assertNotNull(userResponse);
    }
}
