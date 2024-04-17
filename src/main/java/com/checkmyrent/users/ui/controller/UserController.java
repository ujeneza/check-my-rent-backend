package com.checkmyrent.users.ui.controller;

import com.checkmyrent.users.service.UserService;
import com.checkmyrent.users.shared.dto.UserDTO;
import com.checkmyrent.users.ui.model.request.RequestOperationName;
import com.checkmyrent.users.ui.model.request.RequestOperationStatus;
import com.checkmyrent.users.ui.model.request.UserDetailRequestModel;
import com.checkmyrent.users.ui.model.response.OperationStatusModel;
import com.checkmyrent.users.ui.model.response.UserResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "User management APIs")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "Retrieve all users",
            tags = { "Users", "get" })
    @GetMapping("/users")
    public List<UserResponseModel> retrieveAllUsers() {
        return userService.getUsers();
    }

    @Operation(
            summary = "Create a new user with the minimum required info",
            tags = { "Users", "post" })
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDetailRequestModel.class), mediaType = "application/json") }),
//            @ApiResponse(responseCode = "404", description = "Not created", content = { @Content(schema = @Schema()) })
//    })
    @PostMapping("/users")
    public UserResponseModel createUser(@Valid @RequestBody UserDetailRequestModel userDetailsRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetailsRequestModel, UserDTO.class);
        UserDTO savedUser =  userService.createUser(userDTO);
        return modelMapper.map(savedUser, UserResponseModel.class) ;
    }
    @Operation(
            summary = "Get user by using his userId",
            tags = { "Users", "get" })
    @GetMapping(path = "/user/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserResponseModel getUser(@PathVariable String id) {

        UserDTO userDto = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        UserResponseModel returnValue = modelMapper.map(userDto, UserResponseModel.class);


        return returnValue;

        //return EntityModel.of(returnValue).add(userResourceLink).add(addressesResourceLink);
    }

    @Operation(
            summary = "delete selected users",
            tags = { "Users", "delete" })
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header") })
    @Transactional
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @Operation(
            summary = "Update current user",
            tags = { "Users", "put" })
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header") })
    public UserResponseModel updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {
        UserResponseModel returnValue = new UserResponseModel();

        UserDTO userDto = new UserDTO();
        userDto = new ModelMapper().map(userDetails, UserDTO.class);

        UserDTO updateUser = userService.updateUser(id, userDto);
        returnValue = new ModelMapper().map(updateUser, UserResponseModel.class);

        return returnValue;
    }
}
