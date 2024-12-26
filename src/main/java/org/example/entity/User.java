package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.RoleEnum;

@Getter
@Setter
@AllArgsConstructor
public class User   {
    private Integer id;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    private String username;
    private String password;
    private String phoneNumber;

    public User(Integer id, String firstName, String lastName, String username, RoleEnum role, String password, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}

