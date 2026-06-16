package com.aston.controller;

import com.aston.dto.UserDto;
import com.aston.hateoas.UserModelAssembler;
import com.aston.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller")
public class UserController {

    private final UserService service;
    private final UserModelAssembler assembler;

    public UserController(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getById(@PathVariable Long id) {
        return assembler.toModel(service.getById(id));
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAll() {
        var users = service.getAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @PostMapping
    public EntityModel<UserDto> create(@RequestBody @Valid UserDto dto) {
        return assembler.toModel(service.create(dto));
    }

    @PutMapping("/{id}")
    public EntityModel<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto dto) {
        return assembler.toModel(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}