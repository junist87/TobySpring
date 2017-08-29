package Toby.Service.UserLevelUpgradePolicy;

import Toby.Dto.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
