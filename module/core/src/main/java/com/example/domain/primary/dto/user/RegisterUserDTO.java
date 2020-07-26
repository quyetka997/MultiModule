package com.example.domain.primary.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserDTO {

    @NotNull(message = "Username not null")
    @Size(min = 8, max = 50)
    @Pattern(regexp = "^(84|66)((3|5|7|8|9){1})(\\d{8})$", message = "Phone need validate")
    private String userName;

    @NotNull
    @Size(min = 8, max = 50)
    private String passWord;

    @NotNull
    @Size(min = 8, max = 50)
    private String fullName;

    private String role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
