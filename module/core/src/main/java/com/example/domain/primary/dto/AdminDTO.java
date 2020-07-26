package com.example.domain.primary.dto;


import com.example.domain.primary.entity.DtbAdmin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminDTO {

    @NotNull(message = "Username not null")
    @Size(min = 8, max = 50, message = "Fullname have 8 to 50 character")
    public String userName;

    @NotNull(message = "Fullname not null")
    @Size(min = 8, max = 50, message = "Fullname have 8 to 50 character")
    public String fullName;

    @NotNull(message = "Password not null")
    @Size(min = 8, max = 50, message = "Password have 8 to 50 character")
    public String passWord;

    public AdminDTO toDTO(DtbAdmin admin){
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.userName = admin.getUserName();
        adminDTO.fullName = admin.getFullName();
        return adminDTO;
    }

    public DtbAdmin toEntity(AdminDTO adminDTO, DtbAdmin admin) {
        admin.setPassWord(adminDTO.passWord);
        admin.setFullName(adminDTO.fullName);
        admin.setUserName(adminDTO.userName);
        return admin;
    }
}
