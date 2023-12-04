package com.ssamz.jblog;

import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.persistence.UserDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDAOTest {

    @Autowired private UserDAO userDAO;

    @Test
    public void getUserListTest() {
        // given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test123");
        user.setEmail("test@gmail.com");

        // when
        int before = userDAO.getUserList().size();
        userDAO.insertUser(user);
        int after = userDAO.getUserList().size();

        // then
        assertThat(before + 1).isEqualTo(after);

    }

}