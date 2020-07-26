package com.example.application.service;


import com.example.domain.primary.dto.BookDetailDTO;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.dto.user.UserUpdateInformationDTO;
import com.example.domain.primary.dto.user.VerificationDTO;
import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbTransportRequest;
import com.example.domain.primary.entity.DtbUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUserDTO();

    List<DtbUser> getAllUser();

    String bookTransportRequest(BookDetailDTO bookDetailDTO);

    String denyTransportRequest(Long tranportRequestId);

    List<DtbTransportRequest> getPostedTransportRequest(Long id);

    List<DtbTransportRequest> getReceivedTransportRequest(Long id);

    DtbUser addRoleToUser(String userName, DtbRole role);

    Page<DtbUser> getAllUser(Pageable pageable);

    UserDTO insertUser(UserDTO userDTO, DtbRole role);

    UserDTO updateUser(UserDTO userDTO);

    void approveUser(Long id);

    void deleteUser(Long id);

    UserDTO getUser(Long id);

    void removeUser(Long id);

    String validate(UserDTO userDTO);

    boolean isExist(String userName);

    boolean isExist(Long id);

    boolean isDelete(Long id);

    void addUserVerification(String phoneNumber);

    boolean verifycationUser(VerificationDTO verificationDTO);

    boolean updatePassowrd(UserUpdateInformationDTO userUpdateInformationDTO);
}
