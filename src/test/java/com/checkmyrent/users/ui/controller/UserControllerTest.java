package com.checkmyrent.users.ui.controller;

import com.checkmyrent.users.io.UserEntity;
import com.checkmyrent.users.repository.UserRepository;
import com.checkmyrent.users.service.impl.UserServiceImpl;
import com.checkmyrent.users.shared.dto.AddressDTO;
import com.checkmyrent.users.shared.dto.UserDTO;
import com.checkmyrent.users.ui.model.request.AddressRequestModel;
import com.checkmyrent.users.ui.model.request.UserDetailRequestModel;
import com.checkmyrent.users.ui.model.response.UserResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    UserDTO userDto;
    UserDetailRequestModel userDetailRequestModel;

    final String USER_ID = "bfhry47fhdjd7463gdh";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDTO();
        userDto.setFirstName("Sergey");
        userDto.setLastName("Kargopolov");
        userDto.setEmail("test@test.com");
        // userDto.setEmailVerificationStatus(Boolean.FALSE);
        // userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
        userDto.setAddresses(getAddressesDto());
        // userDto.setEncryptedPassword("xcf58tugh47");
        userDto.setPhoneNumber("ffdfdf");
        userDto.setGender("dfdf");
        userDto.setPassword("xcf58tugh47");
        userDto.setIsActive(true);
        userDto.setNationalNumber( "jfjfjfj" );

        userDetailRequestModel =  UserDetailRequestModel.builder()
    .email("test@test.com")
    .firstName("Sergey")
    .lastName("Kargopolov")
    .password("xcf58tugh47").isActive(true)
    .nationalNumber("jfjfjfj")
    .gender("dfdf")
    .phoneNumber("ffdfdf")
    .addresses(getAddressesRequestModel())
    .build();


    }


    @Test
    void retrieveAllUsers ( ) {

    }

    @Test
    void createUser ( ) {
        when(userService.createUser(any())).thenReturn(userDto);

        UserDetailRequestModel userDetailRequestModel = new UserDetailRequestModel();
        UserResponseModel userRest = userController.createUser(userDetailRequestModel);

        assertNotNull( userRest );
    }

    @Test
    public void testGetUser() {
        UserResponseModel expectedResponse = new UserResponseModel(); // Create an expected UserResponseModel

        // Mock the behavior of userService
        when(userService.getUserByUserId(USER_ID)).thenReturn(userDto);

        // Mock the behavior of modelMapper
        when(modelMapper.map(userDto, UserResponseModel.class)).thenReturn(expectedResponse);

        // When
        UserResponseModel actualResponse = userController.getUser(USER_ID);

        // Then
        // Verify that userService.getUserByUserId was called with the correct argument
        verify(userService).getUserByUserId(USER_ID);

        // Verify that modelMapper.map was called with the correct arguments
        verify(modelMapper).map(userDto, UserResponseModel.class);

        // Perform assertions
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void testCreateUser() {
        // Given
        UserDetailRequestModel requestModel = new UserDetailRequestModel(); // Create a test request model
        UserDTO mappedUserDTO = new UserDTO(); // Create a mapped UserDTO
        UserDTO savedUserDTO = new UserDTO(); // Create a saved UserDTO
        UserResponseModel expectedResponse = new UserResponseModel(); // Create an expected UserResponseModel

        // Mock the behavior of modelMapper
        when(modelMapper.map(userDetailRequestModel, UserDTO.class)).thenReturn(mappedUserDTO);
        when(modelMapper.map(savedUserDTO, UserResponseModel.class)).thenReturn(expectedResponse);

        // Mock the behavior of userService
        when(userService.createUser(mappedUserDTO)).thenReturn(savedUserDTO);

        // When
        UserResponseModel actualResponse = userController.createUser(requestModel);

        // Then
        // Verify that modelMapper.map was called with the correct arguments
        verify(modelMapper).map(requestModel, UserDTO.class);
        verify(modelMapper).map(savedUserDTO, UserResponseModel.class);

        // Verify that userService.createUser was called with the correct argument
        verify(userService).createUser(mappedUserDTO);

        // Perform assertions
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteUser ( ) {
        // Arrange
        // String userId = "1a2b3c";
        when(userService.getUser(anyString())).thenReturn(userDto);

        // Act
        userController.deleteUser(USER_ID);

        // Assert
        verify(userService, times(1)).deleteUser(USER_ID);
    
    }

    @Test
    void updateUser ( ) {
    }

    @Test
    void verifyEmailToken ( ) {
    }

    private List<AddressDTO> getAddressesDto() {
        AddressDTO addressDto = new AddressDTO();
        addressDto.setType("shipping");
        addressDto.setCity("Vancouver");
        addressDto.setCountry("Canada");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreetName("123 Street name");

        AddressDTO billingAddressDto = new AddressDTO();
        billingAddressDto.setType("billling");
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("123 Street name");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }


    private List<AddressRequestModel> getAddressesRequestModel() {
        List<AddressRequestModel> addresses = new ArrayList<>();
        AddressRequestModel address = new AddressRequestModel();
        address.setType("shipping");
        address.setCity("Vancouver");
        address.setCountry("Canada");
        address.setPostalCode("ABC123");
        address.setStreetName("ddd");

        AddressRequestModel billingAddress = new AddressRequestModel();
        billingAddress.setType("billing");
        billingAddress.setCity("Vancouver");
        billingAddress.setCountry("Canada");
        billingAddress.setPostalCode("ABC123");
        billingAddress.setStreetName("eee"); 
    
        addresses.add(address);
        addresses.add(billingAddress);

        return addresses;
    }
}