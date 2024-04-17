package com.checkmyrent.users.io;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name="user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity  implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;
    private String phoneNumber;
    private String gender;
    private String nationalNumber;
    // private String password;

    @Past(message = "Birth Date should be in the past")
    private LocalDate birthDate;

    @OneToMany(mappedBy="userDetails", cascade= CascadeType.ALL)
    private List<AddressEntity> addresses;

    // @OneToMany(mappedBy = "user")
    // private List<RoleEntity> userRoles;


    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;

    private Boolean isActive;

}
