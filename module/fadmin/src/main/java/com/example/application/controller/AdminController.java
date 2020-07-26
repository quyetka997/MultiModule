package com.example.application.controller;

import com.example.application.exception.CustomException;
import com.example.application.exception.ExistException;
import com.example.application.exception.NotFoundException;
import com.example.application.service.IAdminService;
import com.example.application.service.IUserService;
import com.example.domain.primary.dto.AdminDTO;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.entity.DtbAdmin;
import com.example.domain.primary.entity.DtbUser;
import com.example.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.function.Function;

import static com.example.application.utils.SecurityConstants.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    IUserService userService;

    @Autowired
    IAdminService adminService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AdminDTO adminDTO){

        if(adminService.isExist(adminDTO.userName)) {
            throw new ExistException(EXCEPTION_EXIST);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminService.insert(adminDTO));
    }


    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/get_all_user")
    ResponseEntity<?> getAllUser() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAllUser());
    }

//    @SortDefault.SortDefaults({
//            @SortDefault(sort = "name", direction = Sort.Direction.DESC),
//            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
//    })
    @GetMapping("/get_user_page")
    ResponseEntity<?> getUserFollowPage(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        Page<DtbUser> userPages = userService.getAllUser(pageable);

        Page<UserDTO> pageDto = userPages.map(new Function<DtbUser, UserDTO>() {
            @Override
            public UserDTO apply(DtbUser user) {
                UserDTO userDto = new UserDTO();
                return userDto.toDTO(user);
            }
        });

        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageDto);
        return ResponseEntity.ok().headers(headers).body(pageDto.getContent());
    }

    @GetMapping("/approve_user/{id}")
    ResponseEntity<?> approveUser(@PathVariable Long id) {

        if(userService.isExist(id)) {
            if(userService.isDelete(id)) {
                throw new CustomException(EXCEPTION_DELETE);
            }
        } else {
            throw new NotFoundException(EXCEPTION_NOT_EXIST);
        }

        userService.approveUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(APPROVE_SUCCESSFUL);
    }

    @GetMapping("/delete_user/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if(userService.isExist(id)) {
            if(userService.isDelete(id)) {
                throw new CustomException(EXCEPTION_DELETE);
            }
        } else {
            throw new NotFoundException(EXCEPTION_NOT_EXIST);
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(DELETE_SUCCESSFUL);
    }


}
