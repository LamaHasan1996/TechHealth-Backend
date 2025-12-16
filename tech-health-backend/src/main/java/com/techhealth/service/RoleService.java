package com.techhealth.service;

import com.techhealth.domain.Role;
import com.techhealth.domain.User;
import com.techhealth.domain.UserRole;
import com.techhealth.repository.RoleRepository;
import com.techhealth.repository.UserRoleRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleService(RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));
    }

    public Role create(Role body) {
        body.setId(null);
        return roleRepository.save(body);
    }

    public Role update(Long id, Role body) {
        Role existing = getById(id);

        existing.setName(body.getName());
        existing.setDescription(body.getDescription());

        return roleRepository.save(existing);
    }

    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found: " + id);
        }
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersForRole(Long roleId) {
        getById(roleId);
        return userRoleRepository.findByRole_Id(roleId).stream()
                .map(UserRole::getUser)
                .toList();
    }
}

