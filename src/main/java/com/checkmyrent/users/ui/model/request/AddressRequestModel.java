package com.checkmyrent.users.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestModel implements Serializable {
    private String id;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

}
