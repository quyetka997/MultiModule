package com.example.domain.primary.dto.user;


import com.example.domain.primary.entity.DtbUser;
import org.springframework.security.core.GrantedAuthority;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInformation extends org.springframework.security.core.userdetails.User {

    private Long id;

    private String userName;

    private String fullName;

    private String passWord;

    private boolean isApproved;

    private boolean isDeleted;


    private Collection<GrantedAuthority> authorities;

    public UserInformation(DtbUser user, Collection<GrantedAuthority> grantedAuthorities) {
        super(user.getUserName(), user.getPassWord(), grantedAuthorities);
        this.id = user.getId();
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.fullName = user.getFullName();
        this.isApproved = user.isApproved();
        this.isDeleted = user.isDeleted();
        this.authorities = grantedAuthorities;
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

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
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
