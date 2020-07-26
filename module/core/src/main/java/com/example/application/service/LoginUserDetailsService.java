package com.example.application.service;

import com.example.application.exception.AppLogicException;
import com.example.application.exception.CustomException;
import com.example.application.exception.TaskNotFoundProblem;
import com.example.domain.primary.dto.user.UserInformation;
import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbUser;
import com.example.domain.primary.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

import static com.example.application.utils.SecurityConstants.ROLE;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        DtbUser user = userRespository.findUserByUserName(username);
        if (user == null) {
            throw new AppLogicException("USER",Status.UNAUTHORIZED,"Not Found User");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user.getRoles() != null) {
            for (DtbRole role: user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE + role.getRoleName()));
            }
        }
        return new UserInformation(user, grantedAuthorities);
        //return new UserInformation(user);
    }
}