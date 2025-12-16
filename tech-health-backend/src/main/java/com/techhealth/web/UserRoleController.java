package com.techhealth.web;

import com.techhealth.domain.UserRole;
import com.techhealth.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserRole> getAll() {
        return service.getAll();
    }

    @GetMapping("/{userId}/{roleId}")
    public UserRole getById(@PathVariable Long userId, @PathVariable Long roleId) {
        return service.getById(userId, roleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole create(@RequestParam Long userId, @RequestParam Long roleId) {
        return service.create(userId, roleId);
    }

    @DeleteMapping("/{userId}/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long roleId) {
        service.delete(userId, roleId);
    }
}
