package com.mycompany;

import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // Import Assertion from AssertJ

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("Dani@gmail.com");
        user.setPassword("kecebanget");
        user.setFirstname("Dani");
        user.setLastname("Slibaw");

        User savedUser = repo.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        assertThat(users).hasSizeGreaterThan(0);

        for (User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("hyaaa12");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        assertThat(updatedUser.getPassword()).isEqualTo("hyaaa12");
    }

    @Test
    public void testGet(){
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isNotPresent();
    }
}
