package com.checkmyrent.users.io;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name="addresses")
@Setter
@Getter
@NoArgsConstructor
public class AddressEntity implements Serializable {


    @Id
    @GeneratedValue
    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userDetails;
}
