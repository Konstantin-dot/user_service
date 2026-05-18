package com.aston.entity;

import com.aston.dto.UserDto;
import com.aston.repository.UserRepository;
import com.aston.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @DisplayName("Создание пользователя, должен вернуть UserDto")
    @Test
    void create_shouldReturnDto() {
        UserDto dto = new UserDto();
        dto.setName("John");
        dto.setEmail("john@mail.com");
        dto.setAge(25);

        User saved = new User();
        saved.setId(1L);
        saved.setName("John");

        when(repository.save(any())).thenReturn(saved);

        UserDto result = userService.create(dto);

        assertEquals("John", result.getName());
    }

    @DisplayName("Получение пользователя по ID, должен вернуть UserDto")
    @Test
    void getById_shouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        UserDto result = userService.getById(1L);

        assertEquals("John", result.getName());
    }

    @DisplayName("Удаление пользователя, должен вызвать repository.deleteById()")
    @Test
    void delete_shouldCallRepository() {
        doNothing().when(repository).deleteById(1L);

        userService.delete(1L);

        verify(repository).deleteById(1L);
    }
}