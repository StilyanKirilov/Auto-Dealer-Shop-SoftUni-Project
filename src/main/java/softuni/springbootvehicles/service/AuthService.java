package softuni.springbootvehicles.service;

import softuni.springbootvehicles.model.entity.User;

public interface AuthService {
    User register(User user);
    User login(String username, String password);
}
