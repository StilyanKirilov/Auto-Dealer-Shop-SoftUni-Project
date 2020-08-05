package softuni.springbootvehicles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springbootvehicles.exception.InvalidEntityException;
import softuni.springbootvehicles.model.entity.Role;
import softuni.springbootvehicles.model.entity.User;
import softuni.springbootvehicles.service.AuthService;
import softuni.springbootvehicles.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User register(User user) {

        if (user.getRoles().contains(Role.ADMIN)) {
            throw new InvalidEntityException("Admin cannot self register.");
        }
        return userService.createUser(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userService.getUserByUsername(username);

        if (user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
}
