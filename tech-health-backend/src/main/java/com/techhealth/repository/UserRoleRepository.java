package com.techhealth.repository;

import com.techhealth.domain.UserRole;
import com.techhealth.domain.UserRole.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_Id(Long userId);
    List<UserRole> findByRole_Id(Long roleId);
    boolean existsByUser_IdAndRole_Id(Long userId, Long roleId);
}


