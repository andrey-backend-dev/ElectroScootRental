package com.example.electroscoot;

import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLIntegrityConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void repositorySaveTest() {
//        init data
        String username = "usedreamless";
        String password = "12345";
        String phone1 = "3214";
        String phone2 = "32145";
        String phone3 = "34521";

//        creation
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone1);
        userRepository.save(user);

        User sameUsernameUser = new User();
        sameUsernameUser.setUsername(username);
        sameUsernameUser.setPassword(password);
        sameUsernameUser.setPhone(phone2);

        User withoutUsernameUser = new User();
        withoutUsernameUser.setPassword(password);
        withoutUsernameUser.setPhone(phone3);

//        selection
        User userFromDb = entityManager.find(User.class, user.getId());

//        assert
        Assert.assertEquals(user.getUsername(), userFromDb.getUsername());

        Assert.assertThrows(DataIntegrityViolationException.class, () -> {
//            unique issue
            userRepository.save(sameUsernameUser);
        });

        Assert.assertThrows(DataIntegrityViolationException.class, () -> {
//            not null issue
            userRepository.save(withoutUsernameUser);
        });
    }

    @Test
    public void repositoryFindByUsernameTest() {
//        init data
        String username = "usedreamless";
        String password = "12345";
        String phone = "3214";

//        creation
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        entityManager.persistAndFlush(user);

//        selection
        User userFromDbByUsername = userRepository.findByUsername(username);

//        assert
        Assert.assertEquals(user.getUsername(),userFromDbByUsername.getUsername());

        Assert.assertNull(userRepository.findByUsername("Not existing username"));
    }

    @Test
    public void repositoryDeleteByUsernameTest() {
//        init data
        String username = "test1";
        String password = "12345";
        String phone = "3214";

//        creation
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        entityManager.persistAndFlush(user);

//        selection
        User userFromDb = entityManager.find(User.class, user.getId());

//        assert
        Assert.assertNotNull(userFromDb);

//        delete
        userRepository.deleteByUsername(username);
        userFromDb = entityManager.find(User.class, user.getId());

//        assert
        Assert.assertNull(userFromDb);
    }

}
