package Toby.Test;

import Toby.Dao.UserDaoJdbc;
import Toby.Dto.User;
import Toby.Dto.UserDummy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/Toby/ApplicationContext/test-ApplicationContext.xml")
public class DaoTest {

    @Autowired
    UserDaoJdbc dao;


    User user01;
    User user02;
    User user03;
    User user04;
    User user05;

    List<User> userList;

    UserDummy dummy;

    @Before
    public void setUp() {
        dummy = new UserDummy();
        user01 = dummy.user01;
        user02 = dummy.user02;
        user03 = dummy.user03;
        user04 = dummy.user04;
        user05 = dummy.user05;

        userList = dummy.userList;
        dao.deleteAll();

    }

    @Test
    public void addAndGet() {
        User getUser;

        dao.add(user01);
        assertThat(dao.getCount(), is(1));
        getUser = dao.get(user01.getId());
        isSameUser(user01, getUser);

        dao.add(user02);
        assertThat(dao.getCount(), is(2));
        getUser = dao.get(user02.getId());
        isSameUser(user02, getUser);

        dao.add(user03);
        assertThat(dao.getCount(), is(3));
        getUser = dao.get(user03.getId());
        isSameUser(user03, getUser);

        dao.add(user04);
        assertThat(dao.getCount(), is(4));
        getUser = dao.get(user04.getId());
        isSameUser(user04, getUser);

        dao.add(user05);
        assertThat(dao.getCount(), is(5));
        getUser = dao.get(user05.getId());
        isSameUser(user05, getUser);
    }


    @Test
    public void update() {
        User getUser;
        dao.add(user01);

        dao.update(user01.getId(), user02);
        getUser = dao.get(user01.getId());
        isSameUserWithoutId(user02, getUser);


        dao.add(user02);
        dao.update(user02.getId(), user03);
        getUser = dao.get(user02.getId());
        isSameUserWithoutId(user03, getUser);

        dao.add(user03);
        dao.update(user03.getId(), user04);
        getUser = dao.get(user03.getId());
        isSameUserWithoutId(user04, getUser);

        dao.add(user04);
        dao.update(user04.getId(), user05);
        getUser = dao.get(user04.getId());
        isSameUserWithoutId(user05, getUser);
    }

    @Test (expected = EmptyResultDataAccessException.class)
    public void remove() {
        for (User user : userList) {
            dao.add(user);
        }

        dao.delete(user01.getId());

        User getUser = dao.get(user01.getId());
    }

    @Test
    public void getAll() {
        for (User user : userList) {
            dao.add(user);
        }

        List<User> getUserList = dao.getAll();
        Collections.sort(getUserList, new upperArray());


    }

    class upperArray implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }


    private void isSameUserWithoutId(User u1, User u2) {
        assertThat(u1.getLevel(), is(u2.getLevel()));
        assertThat(u1.getLogin(), is(u2.getLogin()));
        assertThat(u1.getName(), is(u2.getName()));
        assertThat(u1.getPassword(), is(u2.getPassword()));
        assertThat(u1.getRecommend(), is(u2.getRecommend()));
    }

    private void isSameUser(User u1, User u2) {
        assertThat(u1.getId(), is(u2.getId()));
        isSameUserWithoutId(u1, u2);
    }

}
