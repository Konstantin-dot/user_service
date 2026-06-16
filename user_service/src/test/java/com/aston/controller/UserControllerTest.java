package com.aston.controller;

import com.aston.dto.UserDto;
import com.aston.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Получение всех пользователей должно возвращать список пользователей")
    @Test
    void shouldGetAllUsers() throws Exception {
        when(userService.getAll())
                .thenReturn(List.of(new UserDto()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @DisplayName("Получение пользователя по ID должно возвращать корректного пользователя")
    @Test
    void shouldGetUserById() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("John");

        when(userService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @DisplayName("Создать пользователя")
    @Test
    void shouldCreateUser() throws Exception {
        UserDto dto = new UserDto();
        dto.setName("John");
        dto.setEmail("john@mail.com");
        dto.setAge(25);

        when(userService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @DisplayName("Удаление пользователя по id")
    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService).delete(1L);
    }

    @DisplayName("Проверка валидации")
    @Test
    void shouldReturnBadRequestWhenInvalidEmail() throws Exception {
        UserDto dto = new UserDto();
        dto.setName("John");
        dto.setEmail("invalid");
        dto.setAge(20);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}