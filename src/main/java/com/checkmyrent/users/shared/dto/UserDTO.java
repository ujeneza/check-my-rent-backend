package com.checkmyrent.users.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String nationalNumber;
    private String password;
    // private String encryptedPassword;
    // private String emailVerificationToken;
    // private Boolean emailVerificationStatus = false;
    private List<AddressDTO> addresses;
    private Boolean isActive;
}
