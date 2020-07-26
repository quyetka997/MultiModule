package com.example.domain.primary.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VerificationDTO {

    @NotNull(message = "Username not null")
    @Size(min = 8, max = 50, message = "Fullname have 8 to 50 character")
    @Pattern(regexp = "^(84|66)((3|5|7|8|9){1})(\\d{8})$", message = "Phone need validate")
    public String userName;

    @NotNull
    public String verificationCode;
}
