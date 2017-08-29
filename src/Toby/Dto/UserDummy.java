package Toby.Dto;

import java.util.Arrays;
import java.util.List;

import static Toby.Service.UserLevelUpgradePolicy.EventPeriod.MIN_LOGCOUNT_FOR_SILVER;
import static Toby.Service.UserLevelUpgradePolicy.EventPeriod.MIN_RECOMMEND_FOR_GOLD;

public class UserDummy {
    public User user01;
    public User user02;
    public User user03;
    public User user04;
    public User user05;
    public List<User> userList;

    public UserDummy() {
        user01 = new User();
        user01.setId("Apple");
        user01.setPassword("1234");
        user01.setName("Lee");
        user01.setLevel(Level.BASIC);
        user01.setRecommend(1);
        user01.setLogin(MIN_LOGCOUNT_FOR_SILVER-1);


        user02 = new User();
        user02.setId("Banana");
        user02.setPassword("afde");
        user02.setName("Ciao");
        user02.setLevel(Level.BASIC);
        user02.setRecommend(1);
        user02.setLogin(MIN_LOGCOUNT_FOR_SILVER);


        user03 = new User();
        user03.setId("Citron");
        user03.setPassword("vdsaf");
        user03.setName("Choi");
        user03.setLevel(Level.SILVER);
        user03.setRecommend(MIN_RECOMMEND_FOR_GOLD-1);
        user03.setLogin(MIN_LOGCOUNT_FOR_SILVER);


        user04 = new User();
        user04.setId("Durian");
        user04.setPassword("!@#!@");
        user04.setName("Park");
        user04.setLevel(Level.SILVER);
        user04.setRecommend(MIN_RECOMMEND_FOR_GOLD);
        user04.setLogin(MIN_LOGCOUNT_FOR_SILVER);


        user05 = new User();
        user05.setId("Elderberry");
        user05.setPassword("vzcd");
        user05.setName("Joo");
        user05.setLevel(Level.GOLD);
        user05.setRecommend(MIN_RECOMMEND_FOR_GOLD);
        user05.setLogin(MIN_LOGCOUNT_FOR_SILVER);

        userList = Arrays.asList(user01, user02, user03, user04, user05);
    }
}
