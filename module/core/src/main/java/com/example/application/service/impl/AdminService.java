package com.example.application.service.impl;

import com.example.application.service.IAdminService;
import com.example.domain.primary.dto.AdminDTO;
import com.example.domain.primary.entity.DtbAdmin;
import com.example.domain.primary.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<AdminDTO> getAllAdmin() {
        List<DtbAdmin> admins = adminRepository.findAll();
        if(admins != null) {
            AdminDTO adminDTO = new AdminDTO();
            List<AdminDTO> adminDTOs = new ArrayList<>();
            for (DtbAdmin admin: admins) {
                adminDTOs.add(adminDTO.toDTO(admin));
            }
            return adminDTOs;
        }
        return null;
    }

    @Override
    public Page<DtbAdmin> getAllAdmin(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public AdminDTO insert(AdminDTO adminDTO) {
        adminDTO.passWord = bCryptPasswordEncoder.encode(adminDTO.passWord);
        DtbAdmin admin = adminDTO.toEntity(adminDTO, new DtbAdmin());
        admin = adminRepository.save(admin);
        return adminDTO.toDTO(admin);
    }


    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO) {
        DtbAdmin admin = adminRepository.findOneByUserName(adminDTO.userName);
        admin = adminDTO.toEntity(adminDTO, admin);
        return adminDTO.toDTO(adminRepository.save(admin));
    }


    @Override
    public void approveAdmin(Long id) {
        DtbAdmin admin = adminRepository.findOneById(id);
        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        DtbAdmin admin = adminRepository.findOneById(id);
        adminRepository.save(admin);
    }

    @Override
    public AdminDTO getAdmin(Long id) {
        DtbAdmin admin = adminRepository.findOneById(id);
        AdminDTO adminDTO = new AdminDTO();
        return adminDTO.toDTO(adminRepository.save(admin));
    }

    @Override
    public void removeAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public boolean isExist(String username) {
        DtbAdmin admin = adminRepository.findOneByUserName(username);
        return admin != null;
    }

    @Override
    public boolean isDelete(Long id) {
        DtbAdmin admin = adminRepository.findOneById(id);
        return admin != null;
    }
}
