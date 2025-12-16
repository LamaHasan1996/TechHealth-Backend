package com.techhealth.service;

import com.techhealth.domain.Role;
import com.techhealth.domain.User;
import com.techhealth.domain.UserRole;
import com.techhealth.domain.UserRole.UserRoleId;
import com.techhealth.repository.RoleRepository;
import com.techhealth.repository.UserRepository;
import com.techhealth.repository.UserRoleRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserRole getById(Long userId, Long roleId) {
        UserRoleId id = new UserRoleId(userId, roleId);
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserRole not found: userId=" + userId + ", roleId=" + roleId));
    }

    public UserRole create(Long userId, Long roleId) {
        if (userRoleRepository.existsByUser_IdAndRole_Id(userId, roleId)) {
            return getById(userId, roleId);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleId));

        UserRole ur = new UserRole();
        ur.setId(new UserRoleId(userId, roleId));
        ur.setUser(user);
        ur.setRole(role);

        return userRoleRepository.save(ur);
    }

    public void delete(Long userId, Long roleId) {
        UserRoleId id = new UserRoleId(userId, roleId);
        if (!userRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserRole not found: userId=" + userId + ", roleId=" + roleId);
        }
        userRoleRepository.deleteById(id);
    }
}

