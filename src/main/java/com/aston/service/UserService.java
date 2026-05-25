package com.aston.service;

import com.aston.dto.UserDto;
import com.aston.entity.User;
import com.aston.exception.UserNotFoundException;
import com.aston.mapper.UserMapper;
import com.aston.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Управление бизнес-логикой
@Service // Для регистрацйи в контексте Spring
public class UserService {

    // Зависимость, работа с БД
    private final UserRepository repository;

    // Конструкторная инъекция (Dependency Injection), Spring сам передаст сюда UserRepository
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Создание пользователя
    public UserDto create(UserDto dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toDto(repository.save(user));
    }

    // получение пользователя по id
    public UserDto getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return UserMapper.toDto(user);
    }

    // получение всех пользователей
    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    // обновление пользователей
    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        return UserMapper.toDto(repository.save(user));
    }

    // удаление пользователей
    public void delete(Long id) {
        repository.deleteById(id);
    }
}