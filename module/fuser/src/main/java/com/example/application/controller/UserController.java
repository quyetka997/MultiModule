package com.example.application.controller;


import com.example.application.exception.AppLogicException;
import com.example.application.exception.CustomException;
import com.example.application.exception.ExistException;
import com.example.application.service.IRoleService;
import com.example.application.service.IUserService;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.dto.user.LoginUserDTO;
import com.example.domain.primary.dto.user.RegisterUserDTO;
import com.example.domain.primary.dto.user.UserUpdateInformationDTO;
import com.example.domain.primary.dto.user.VerificationDTO;
import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbUser;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;


import javax.validation.Valid;

import static com.example.UserAppApplication.ACCOUNT_SID;
import static com.example.UserAppApplication.AUTH_TOKEN;
import static com.example.application.utils.SecurityConstants.EXCEPTION_EXIST;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;


    @GetMapping("/forget_password")
    public ResponseEntity<?> forgetPassword(@RequestParam String phoneNumber) {
        if(!userService.isExist(phoneNumber)) {
            throw new AppLogicException("PHONE NUMBER",Status.BAD_REQUEST,"Phonenumber not exist");
        }
        userService.addUserVerification(phoneNumber);
        return ResponseEntity.accepted().body("Send Verification Successful");
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verificationPhoneNumber(@Valid @RequestBody VerificationDTO verificationDTO) {
        if(!userService.isExist(verificationDTO.userName)) {
            throw new AppLogicException("PHONE NUMBER",Status.BAD_REQUEST,"Phonenumber not exist");
        }
        if(userService.verifycationUser(verificationDTO)) {
            return ResponseEntity.accepted().body("ok");
        } else {
            return ResponseEntity.accepted().body("Code is wrong or expired, Please send again!");
        }
    }

    @PostMapping("/updatePasswork")
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdateInformationDTO userUpdateInformationDTO) {
        if(!userService.isExist(userUpdateInformationDTO.userName)) {
            throw new AppLogicException("PHONE NUMBER",Status.BAD_REQUEST,"Phonenumber not exist");
        }
        if(userService.updatePassowrd(userUpdateInformationDTO)) {
            return ResponseEntity.accepted().body("ok");
        } else {
            return ResponseEntity.accepted().body("Code is wrong or expired, Please send again!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO){

        DtbRole role = roleService.findOneByRoleName(userDTO.roleName);

        if(role == null) {
            throw new AppLogicException("ROLE", Status.UNAUTHORIZED,"Role not exist");
        }

        if(userService.isExist(userDTO.userName)) {

            DtbUser user = userService.addRoleToUser(userDTO.userName, role);

            if(user != null) {
                userDTO = userDTO.toDTO(user);
            } else {
                throw new ExistException("User have role");
            }
        } else {
            userDTO = userService.insertUser(userDTO,role);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
    }
}
