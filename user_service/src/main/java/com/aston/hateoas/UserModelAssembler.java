package com.aston.hateoas;

import com.aston.controller.UserController;
import com.aston.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto user) {

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("all-users"),
                linkTo(methodOn(UserController.class).update(user.getId(), user)).withRel("update"),
                linkTo(methodOn(UserController.class).delete(user.getId())).withRel("delete")
        );
    }
}