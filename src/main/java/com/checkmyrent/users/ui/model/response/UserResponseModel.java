package com.checkmyrent.users.ui.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponseModel implements Serializable {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String nationalNumber;
    private List<AddressResponseModel> addresses;
    private List<String> userRoles;
    private boolean isActive;

}
