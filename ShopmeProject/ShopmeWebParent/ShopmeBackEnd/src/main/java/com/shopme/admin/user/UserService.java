package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public List<User> listAllUsers () {
        return userRepository.findAll();
    }

    public List<Role> listAllRoles() {
        return roleRepository.findAll();
    }

    private void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public void save(User user) {
        boolean isUpdatingUser = (user.getId() != null);

        if (isUpdatingUser) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);

            if (existingUser != null) {
                if (user.getPassword().isEmpty()) {
                    user.setPassword(existingUser.getPassword());
                } else {
                    encryptPassword(user);
                }
            } else {
                // Xử lý trường hợp không tìm thấy người dùng với ID cụ thể
                throw new IllegalArgumentException("User not found with ID: " + user.getId());
            }
        } else {
            encryptPassword(user);
        }
        userRepository.save(user);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) return false;
        }
        return userByEmail.getId().equals(id);
    }

    public User get(Integer id) throws UserNotFoundExp {
        try {
            return userRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            throw new UserNotFoundExp("Could not find any user with id: " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundExp {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundExp("Could not find any user with ID " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) throws UserNotFoundExp {
        userRepository.updateEnabledStatus(id, enabled);
    }
}
