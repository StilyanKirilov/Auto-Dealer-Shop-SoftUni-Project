package softuni.springbootvehicles.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.springbootvehicles.exception.EntityNotFoundException;
import softuni.springbootvehicles.exception.InvalidEntityException;
import softuni.springbootvehicles.model.entity.Role;
import softuni.springbootvehicles.model.entity.User;
import softuni.springbootvehicles.repository.UserRepository;
import softuni.springbootvehicles.service.UserService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with ID=%s not found.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User %s not found.", username)));
    }

    @Override
    public User createUser(User user) {

        this.userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException((String.format("User with username '%s' already exists.", user.getUsername())));
        });

        user.setCreated(new Date());
        user.setModified(user.getCreated());
        if (user.getRoles() == null || user.getRoles().size() == 0) {
            user.setRoles(Set.of(Role.SELLER));
        }

        user.setActive(true);
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        user.setModified(new Date());
        User old = this.getUserById(user.getId());
        if (user.getUsername() != null && !user.getUsername().equals(old.getUsername())) {
            throw new InvalidEntityException("Username of a user could not be changed.");
        }
        return this.userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User old = this.getUserById(id);
        this.userRepository.deleteById(id);
        return old;
    }

    @Override
    public long getUserCount() {
        return this.userRepository.count();
    }

    @Override
    public List<User> createUsersBatch(List<User> users) {
        return users.stream()
                .map(user -> this.createUser(user))
                .collect(Collectors.toList());
    }
}
