package Toby.MainBuild.Service.UserLevelUpgradePolicy;

import Toby.MainBuild.Dto.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
