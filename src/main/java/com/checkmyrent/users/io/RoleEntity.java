package com.checkmyrent.users.io;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.io.Serializable;

@Entity(name="roles")
@Data
public class RoleEntity  implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @GeneratedValue
    private String roleId;

    @NotBlank
    private String name;
    private String description;

}
