package com.checkmyrent.users.service.impl;

import com.checkmyrent.users.exceptions.UserServiceException;
import com.checkmyrent.users.io.AddressEntity;
import com.checkmyrent.users.io.UserEntity;
import com.checkmyrent.users.repository.UserRepository;
import com.checkmyrent.users.shared.dto.AddressDTO;
import com.checkmyrent.users.shared.dto.UserDTO;
import com.checkmyrent.users.shared.dto.Utils;
import com.checkmyrent.users.ui.model.response.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    String userId = "hhty57ehfy";
    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        userEntity =  UserEntity.builder()
                .id(1L)
                .userId(userId)
                .firstName("ujeneza")
                .email("test@test.com")
                .gender("F")
                .emailVerificationToken("7htnfhr758")
                .birthDate(LocalDate.of(2020, Month.JANUARY, 8))
                .lastName("geo")
                .nationalNumber("88665656565")
                .phoneNumber("0471005656")
                .addresses(getAddressesEntity() )
                .build();

    }


    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDTO userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("ujeneza", userDto.getFirstName());
    }


    @Test
    final void testGetUser_UsernameNotFoundException ( ) {
        when( userRepository.findByEmail( anyString( ) ) ).thenReturn( null );
        Exception thrown = assertThrows( UserServiceException.class,
                ( ) -> {
                    userService.getUser( "test@test.com" );
                }
        );
        assertEquals( ErrorMessages.NO_RECORD_FOUND.getErrorMessage( ), thrown.getMessage( ) );
    }
    @Test
    final void testCreateUser()
    {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("hgfnghtyrir884");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO userDto = new UserDTO();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("ujeneza");
        userDto.setLastName("geo");
        userDto.setPassword("12345678");
        userDto.setEmail("test@test.com");

        UserDTO storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils,times(storedUserDetails.getAddresses().size())).generateAddressId(30);
        // verify(bCryptPasswordEncoder, times(1)).encode("12345678");
        verify(userRepository,times(1)).save(any(UserEntity.class));
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

    private List<AddressEntity> getAddressesEntity()
    {
        List<AddressDTO> addresses = getAddressesDto();

        Type listType = new TypeToken<List<AddressEntity>>() {}.getType();

        return new ModelMapper().map(addresses, listType);
    }
}