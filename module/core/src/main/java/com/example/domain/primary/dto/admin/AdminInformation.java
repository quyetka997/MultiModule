package com.example.domain.primary.dto.admin;


import com.example.domain.primary.entity.DtbAdmin;
import com.example.domain.primary.entity.DtbUser;
import org.springframework.security.core.GrantedAuthority;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminInformation extends org.springframework.security.core.userdetails.User {

    private Long id;

    private String userName;

    private String fullName;

    private String passWord;

    private boolean isApproved;

    private boolean isDeleted;



    public AdminInformation(DtbAdmin admin, Collection<GrantedAuthority> grantedAuthorities) {
        super(admin.getUserName(), admin.getPassWord(), grantedAuthorities);
        this.id = admin.getId();
        this.userName = admin.getUserName();
        this.passWord = admin.getPassWord();
        this.fullName = admin.getFullName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

