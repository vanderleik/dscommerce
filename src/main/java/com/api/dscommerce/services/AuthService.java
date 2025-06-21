package com.api.dscommerce.services;

import com.api.dscommerce.entities.User;
import com.api.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void validateSelfOrAdmin(Long userId) {
        User user = userService.authenticated();
        if (user == null || (!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN"))) {
            throw new ForbiddenException("Access denied");
        }
    }

}
