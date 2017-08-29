package Toby.Service;

import Toby.Dto.User;

public class TestUserServiceImpl extends UserServiceImpl {

    String id = "Citron";

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            System.out.println("!- Exception -!");
            throw new RuntimeException("!- Expected Exception -!");
        }
        super.upgradeLevel(user);
    }
}
