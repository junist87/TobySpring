package Toby.Service;


import Toby.Dao.UserDao;
import Toby.Dto.User;
import Toby.Service.UserLevelUpgradePolicy.UserLevelUpgradePolicy;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao;
    UserLevelUpgradePolicy upgradePolicy;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUpgradePolicy(UserLevelUpgradePolicy upgradePolicy) {
        this.upgradePolicy = upgradePolicy;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();

        for (User user : users) {
            upgradeLevel(user);
        }
    }

    protected void upgradeLevel(User user) {
        if (upgradePolicy.canUpgradeLevel(user)) {
            upgradePolicy.upgradeLevel(user);
        }
    }





}
