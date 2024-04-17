package com.checkmyrent.users.ui.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponseModel implements Serializable {
    private String id;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
}
