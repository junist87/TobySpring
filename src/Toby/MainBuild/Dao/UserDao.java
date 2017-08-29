package Toby.MainBuild.Dao;

import Toby.MainBuild.Dto.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
    void update(String id, User user);
}
