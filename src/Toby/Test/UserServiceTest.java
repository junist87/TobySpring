package Toby.Test;


import Toby.Dao.UserDao;
import Toby.Dto.User;
import Toby.Dto.UserDummy;
import Toby.Service.TestUserServiceImpl;
import Toby.Service.UserLevelUpgradePolicy.UserLevelUpgradePolicy;
import Toby.Service.UserService;
import Toby.Service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "/Toby/ApplicationContext/test-ApplicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserService testUserService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserLevelUpgradePolicy upgradePolicy;

    UserDummy dummy;
    List<User> users;

    User user01;
    User user02;
    User user03;
    User user04;
    User user05;

    @Before
    public void setUp() {
        dummy = new UserDummy();
        users = dummy.userList;

        user01 = dummy.user01;
        user02 = dummy.user02;
        user03 = dummy.user03;
        user04 = dummy.user04;
        user05 = dummy.user05;

        userDao.deleteAll();


    }

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
        assertThat(this.testUserService, is(notNullValue()));
        assertThat(this.userDao, is(notNullValue()));
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);


    }

    private void checkLevelUpgraded(User user,  boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().getNextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    @Test(expected = RuntimeException.class)
    public void exceptionTest() {
        addDummys();
        testUserService.upgradeLevels();
    }

    @Test
    public void upgradeAllorNothing() {
        userDao.deleteAll();

        addDummys();

        try {
            this.testUserService.upgradeLevels();
            fail();
        } catch (RuntimeException e) {

        }

        checkLevelUpgraded(user01, false);
    }

    @Test
    public void isTrasactioned() {
        addDummys();

        try {
            testUserService.upgradeLevels();
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkLevelUpgraded(user01, false);
        checkLevelUpgraded(user02, false);
        checkLevelUpgraded(user03, false);
        checkLevelUpgraded(user04, false);
        checkLevelUpgraded(user05, false);
    }


    private void addDummys() {
        for (User user : users) userDao.add(user);
    }

}
