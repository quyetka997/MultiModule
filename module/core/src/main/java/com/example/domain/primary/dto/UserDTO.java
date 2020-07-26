package com.example.domain.primary.dto;



import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDTO implements Serializable {

    @NotNull(message = "Username not null")
    @Size(min = 8, max = 50, message = "Fullname have 8 to 50 character")
    @Pattern(regexp = "^(84|66)((3|5|7|8|9){1})(\\d{8})$", message = "Phone need validate")
    public String userName;

    public String fullName;

    @NotNull(message = "Password not null")
    @Size(min = 8, max = 50, message = "Password have 8 to 50 character")
    public String passWord;

    @NotNull
    public String roleName;


    public UserDTO toDTO(DtbUser user){
        UserDTO userDTO = new UserDTO();
        userDTO.userName = user.getUserName();
        userDTO.fullName = user.getFullName();
        return userDTO;
    }

    public DtbUser toEntity(UserDTO userDTO, DtbUser user) {
        user.setPassWord(userDTO.passWord);
        user.setFullName(userDTO.fullName);
        user.setUserName(userDTO.userName);
        return user;
    }
}
