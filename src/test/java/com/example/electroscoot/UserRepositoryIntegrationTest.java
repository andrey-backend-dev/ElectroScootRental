package com.example.electroscoot;

import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenFindByUsername_thenReturnUser() {
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
        User userFromDb = userRepository.findByUsername(username);

        Assert.isTrue(user == userFromDb, "Failed");
    }
}
