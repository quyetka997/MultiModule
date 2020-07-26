package com.example.application.service.impl;

import com.example.application.exception.AppLogicException;
import com.example.application.service.IRoleService;
import com.example.application.service.IUserService;
import com.example.domain.primary.dto.BookDetailDTO;
import com.example.domain.primary.dto.UserDTO;
import com.example.domain.primary.dto.user.UserUpdateInformationDTO;
import com.example.domain.primary.dto.user.VerificationDTO;
import com.example.domain.primary.entity.DtbBookDetail;
import com.example.domain.primary.entity.DtbRole;
import com.example.domain.primary.entity.DtbTransportRequest;
import com.example.domain.primary.entity.DtbUser;
import com.example.domain.primary.repository.BookDetailRepository;
import com.example.domain.primary.repository.TransportRespository;
import com.example.domain.primary.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.zalando.problem.Status;

import java.sql.Timestamp;
import java.util.*;

import static com.example.application.utils.SecurityUtil.getAuthenticationName;
import static com.example.util.Constant.EXPIRED_TIME;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRespository userRespository;

    @Autowired
    TransportRespository transportRespository;

    @Autowired
    BookDetailRepository bookDetailRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDTO> getAllUserDTO() {
        List<DtbUser> users = userRespository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        UserDTO userDTO = new UserDTO();

        for (DtbUser user: users) {
            userDTOs.add(userDTO.toDTO(user));
        }
        return userDTOs;
    }

    @Override
    public List<DtbUser> getAllUser() {
        List<DtbUser> users = userRespository.findAll();
        return users;
    }

    @Override
    public String bookTransportRequest(BookDetailDTO bookDetailDTO) {
        DtbTransportRequest dtbTransportRequest;
        try {
            dtbTransportRequest = transportRespository.findById(bookDetailDTO.transportRequestId).get();
        } catch (Exception ex) {
            throw new AppLogicException("TRANSPORTREQUEST", Status.BAD_REQUEST,"Transport Reques not exist");
        }
        DtbUser dtbUser = userRespository.findUserByUserName(getAuthenticationName());
        //check dtbBookDetail
        for (DtbBookDetail bookDetail: dtbUser.getDetailBooks()) {
            if(bookDetail.getTransportRequest().getId() == bookDetailDTO.transportRequestId) {
                return "Transport Request update successful";
            }
        }
        DtbBookDetail dtbBookDetail = new DtbBookDetail();
        dtbBookDetail.setTransportRequest(dtbTransportRequest);
        dtbBookDetail.setUser(dtbUser);
        dtbBookDetail.setOtherInformation(bookDetailDTO.otherInformation);
        dtbBookDetail.setPrice(bookDetailDTO.price);
        dtbUser.getDetailBooks().add(dtbBookDetail);
        bookDetailRepository.save(dtbBookDetail);
        userRespository.save(dtbUser);
        return "Book successful";
    }

    @Override
    public String denyTransportRequest(Long tranportRequestId) {
        DtbUser dtbUser = userRespository.findUserByUserName(getAuthenticationName());
        //check dtbBookDetail
        for (int i = 0; i < dtbUser.getDetailBooks().size(); i++) {
            if(dtbUser.getDetailBooks().get(i).getTransportRequest().getId() == tranportRequestId) {
                bookDetailRepository.delete(dtbUser.getDetailBooks().get(i));
                dtbUser.getDetailBooks().remove(i);
                userRespository.save(dtbUser);
                return  "Delete Successful";
            }
        }
        throw new AppLogicException("TRANSPORTREQUEST", Status.BAD_REQUEST,"Transport Request not book");
    }

    @Override
    public List<DtbTransportRequest> getPostedTransportRequest(Long id) {
        return userRespository.findById(id).get().getPostedTransportRequests();
    }

    @Override
    public List<DtbTransportRequest> getReceivedTransportRequest(Long id) {
        return userRespository.findById(id).get().getReceivedTransportRequests();
    }

    @Autowired
    IRoleService roleService;

    @Override
    public DtbUser addRoleToUser(String userName, DtbRole role) {
        DtbUser user = userRespository.findUserByUserName(userName);
        if(user.getRoles().contains(role)) return null;
        user.getRoles().add(role);
        return userRespository.save(user);
    }

    @Override
    public Page<DtbUser> getAllUser(Pageable pageable) {
        return userRespository.findAll(pageable);
    }

    @Override
    public UserDTO insertUser(@RequestBody UserDTO userDTO, DtbRole role) {
        userDTO.passWord = bCryptPasswordEncoder.encode(userDTO.passWord);
        DtbUser user = userDTO.toEntity(userDTO, new DtbUser());
        user.getRoles().add(role);
        return userDTO.toDTO(userRespository.save(user));
    }

    @Override
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        DtbUser user = userRespository.findUserByUserName(userDTO.userName);
        DtbUser newUser = userRespository.save(userDTO.toEntity(userDTO, user));
        return userDTO.toDTO(newUser);
    }

    @Override
    public void approveUser(Long id) {
        DtbUser user = userRespository.findUserById(id);
        user.setApproved(true);
        userRespository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        DtbUser user = userRespository.findUserById(id);
        user.setDeleted(true);
        userRespository.save(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO = userDTO.toDTO(userRespository.findUserById(id));
        return userDTO;
    }

    @Override
    public void removeUser(Long id) {
        DtbUser user = userRespository.findUserById(id);
        userRespository.delete(user);
    }

    @Override
    public String validate(UserDTO userDTO) {
        return null;
    }

    @Override
    public boolean isExist(String userName) {
        DtbUser user = userRespository.findUserByUserName(userName);
        return user != null;
    }

    @Override
    public boolean isExist(Long id) {
        DtbUser user = userRespository.findUserById(id);
        return user != null;
    }

    @Override
    public boolean isDelete(Long id) {
        DtbUser user = userRespository.findUserById(id);
        return user.isDeleted();
    }

    @Override
    public void addUserVerification(String phoneNumber) {
        DtbUser dtbUser = userRespository.findUserByUserName(phoneNumber);
        dtbUser.setVerificationTime(new Timestamp(System.currentTimeMillis()));
        dtbUser.setVerificationCode(generateVerificationCode());
        userRespository.save(dtbUser);
    }


    @Override
    public boolean verifycationUser(VerificationDTO verificationDTO) {
        DtbUser dtbUser = userRespository.findUserByUserName(verificationDTO.userName);
        if(verificationDTO.verificationCode.equals(dtbUser.getVerificationCode())) {
            if(System.currentTimeMillis() - dtbUser.getVerificationTime().getTime() <= EXPIRED_TIME) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassowrd(UserUpdateInformationDTO userUpdateInformationDTO) {
        DtbUser dtbUser = userRespository.findUserByUserName(userUpdateInformationDTO.userName);
        dtbUser.setPassWord(bCryptPasswordEncoder.encode(userUpdateInformationDTO.password));
        return userRespository.save(dtbUser) != null;
    }


    String generateVerificationCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i = 1; i <= 8; i++) {
            code.append(random.nextInt(9));
        }
        return code.toString();
    }
}
