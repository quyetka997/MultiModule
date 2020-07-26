package com.example.domain.primary.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserUpdateInformationDTO {

    @NotNull
    @Pattern(regexp = "^(84|66)((3|5|7|8|9){1})(\\d{8})$", message = "Phone need validate")
    public String userName;

    @NotNull
    public String password;
}
