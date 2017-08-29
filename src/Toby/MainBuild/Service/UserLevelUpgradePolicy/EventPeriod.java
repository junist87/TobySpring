package Toby.MainBuild.Service.UserLevelUpgradePolicy;

import Toby.MainBuild.Dao.UserDao;
import Toby.MainBuild.Dto.Level;
import Toby.MainBuild.Dto.User;

public class EventPeriod implements UserLevelUpgradePolicy {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 50;

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevel(User user) {
        if (!user.isUpgradable()) return;
        user.upgradeLevel();
        userDao.update(user.getId(), user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {

    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }
}
