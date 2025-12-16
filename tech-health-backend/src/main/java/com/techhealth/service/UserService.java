package com.techhealth.service;

import com.techhealth.domain.Role;
import com.techhealth.domain.User;
import com.techhealth.domain.UserRole;
import com.techhealth.repository.UserRepository;
import com.techhealth.repository.UserRoleRepository;
import com.techhealth.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User create(User body) {
        body.setId(null);
        return userRepository.save(body);
    }

    public User update(Long id, User body) {
        User existing = getById(id);

        existing.setFirstName(body.getFirstName());
        existing.setLastName(body.getLastName());
        existing.setEmail(body.getEmail());
        existing.setPhone(body.getPhone());
        existing.setPasswordHash(body.getPasswordHash());
        existing.setIsActive(body.getIsActive());

        return userRepository.save(existing);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Role> getRolesForUser(Long userId) {
        getById(userId);
        return userRoleRepository.findByUser_Id(userId).stream()
                .map(UserRole::getRole)
                .toList();
    }
}
