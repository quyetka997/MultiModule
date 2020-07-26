package com.example.application.service;

import com.example.application.exception.AppLogicException;
import com.example.application.exception.CustomException;
import com.example.domain.primary.dto.admin.AdminInformation;
import com.example.domain.primary.dto.user.UserInformation;
import com.example.domain.primary.entity.DtbAdmin;
import com.example.domain.primary.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.Collections;

@Service
public class LoginAdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadAdminByUsername");
        DtbAdmin admin = adminRepository.findOneByUserName(username);
        if(admin == null) {
            throw new AppLogicException("Admin not found", Status.NOT_FOUND, "Admin not found");
        }
        return new AdminInformation(admin, Collections.emptyList());
        //return new UserInformation(user);
    }
}
