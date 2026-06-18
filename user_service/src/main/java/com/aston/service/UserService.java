package com.aston.service;

import com.aston.constants.EventTypes;
import com.aston.dto.UserDto;
import com.aston.entity.User;
import com.aston.exception.UserNotFoundException;
import com.aston.kafka.UserEvent;
import com.aston.mapper.UserMapper;
import com.aston.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Управление бизнес-логикой
@Service // Для регистрацйи в контексте Spring
public class UserService {

    // Зависимость, работа с БД
    private final UserRepository repository;
    private final KafkaProducerService kafkaProducerService;

    // Конструкторная инъекция (Dependency Injection), Spring сам передаст сюда UserRepository
    public UserService(UserRepository repository, KafkaProducerService kafkaProducerService) {
        this.repository = repository;
        this.kafkaProducerService = kafkaProducerService;
    }

    // Отправка события при создании
    public UserDto create(UserDto dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = repository.save(user);
        kafkaProducerService.sendEvent(
                new UserEvent(
                        EventTypes.CREATE,
                        savedUser.getEmail()
                )
        );
        return UserMapper.toDto(savedUser);
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

    // отправка событий при удалении
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
        repository.deleteById(id);
        kafkaProducerService.sendEvent(
                new UserEvent(
                        EventTypes.DELETE,
                        user.getEmail()
                )
        );
    }
}