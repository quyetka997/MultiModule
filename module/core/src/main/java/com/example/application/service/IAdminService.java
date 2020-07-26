package com.example.application.service;


import com.example.domain.primary.dto.AdminDTO;
import com.example.domain.primary.entity.DtbAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAdminService {

    List<AdminDTO> getAllAdmin();

    Page<DtbAdmin> getAllAdmin(Pageable pageable);

    AdminDTO insert(AdminDTO adminDTO);

    AdminDTO updateAdmin(AdminDTO adminDTO);

    void approveAdmin(Long id);

    void deleteAdmin(Long id);

    AdminDTO getAdmin(Long id);

    void removeAdmin(Long id);

    boolean isExist(String username);

    boolean isDelete(Long id);
}

