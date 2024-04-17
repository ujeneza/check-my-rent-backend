package com.checkmyrent.users.ui.model.request;

import java.util.List;

import com.checkmyrent.users.shared.dto.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailRequestModel implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String nationalNumber;
    private String password;
    private List<AddressRequestModel> addresses;
    private List<String> userRoles;
    private Boolean isActive;

}
