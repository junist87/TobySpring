package Toby.MainBuild.Dto;

public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;


    public void upgradeLevel() {
        if (isUpgradable()) setLevel(getLevel().getNextLevel());
        else throw new IllegalStateException();
    }

    public boolean isUpgradable() {
        if (getLevel().getNextLevel() != null) return true;
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }


}
