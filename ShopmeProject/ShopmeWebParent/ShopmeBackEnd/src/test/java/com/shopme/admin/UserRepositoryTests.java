package com.shopme.admin;

import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWith1Role() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user1 = new User("tuyenbeo2k3@gmail.com", "buituyen10x", "Bùi", "Tuyến");
        user1.addRole(roleAdmin);

        User savedUser = userRepository.save(user1);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWith2Role() {
        User user2 = new User("vanhung@gmail.com", "vanhung10x", "Phạm", "Hùng");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        user2.addRole(roleEditor);
        user2.addRole(roleAssistant);

        User savedUser = userRepository.save(user2);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void listAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void findUserById() {
        User user1 = userRepository.findById(1).get();
        System.out.println(user1);
        assertThat(user1).isNotNull();
    }

    @Test
    public void testUpdateUserDetail() {
        User user1 = userRepository.findById(1).get();
        user1.setEnabled(true);
        user1.setEmail("quangtuyen10x@gmail.com");

        userRepository.save(user1);
    }

    @Test
    public void testUpdateUserRole() {
        User user2 = userRepository.findById(3).get();
        Role roleEditor = new Role(3);
        user2.getRoles().remove(roleEditor);

        Role roleSalesPerson = new Role(2);
        user2.addRole(roleSalesPerson);

        userRepository.save(user2);
    }

    @Test
    public void testUpdateUserRole2() {
        User user1 = userRepository.findById(1).get();
        Role roleAdmin = new Role(1);

        user1.addRole(roleAdmin);

        userRepository.save(user1);
    }

    @Test
    public void testDeleteUser() {
        int userId = 3;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "tuyenbeo2k3@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer userId = 1;
        Long countById = userRepository.countById(userId);

        assertThat(countById).isNotNull().isGreaterThan(0);

        System.out.println(countById);
    }

    @Test
    public void enableUser() {
        Integer id = 1;
        userRepository.updateEnabledStatus(id, true);
    }

    @Test
    public void testFirstPage() {
        int pageNum = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<User> page  = userRepository.findAll(pageable);

        List<User> users = page.getContent();
        for (User user : users) {
            System.out.println(user);
        }

        assertThat(users.size()).isEqualTo(pageSize);
    }
}
