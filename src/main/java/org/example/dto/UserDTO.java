package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    private String username;
    private String password;
    private String phoneNumber;

    public UserDTO(Integer id, String firstName, String lastName, String username, String password, String phoneNumber, String user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(int id, String firstName, String lastName, RoleEnum role, String password, String phoneNumber) {

    }
}
