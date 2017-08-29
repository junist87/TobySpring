package Toby.MainBuild.Dao;


import Toby.MainBuild.Dto.Level;
import Toby.MainBuild.Dto.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoJdbc implements UserDao{


    JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User get(String userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM spring.account WHERE id=?", new Object[]{userId},
                (resultSet, i) -> resultSet2User(resultSet));

    }

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM spring.account", (resultSet, i) -> resultSet2User(resultSet));

    }

    public void add(User user) {
        jdbcTemplate.update("INSERT INTO spring.account (id, name, password, level, login, recommend) VALUES (?,?,?,?,?,?)", new Object[] {
                user.getId(), user.getName(), user.getPassword(), Level.getIntValue(user.getLevel()), user.getLogin(), user.getRecommend()
        });
    }

    public void update(String id, User user) {
        jdbcTemplate.update("UPDATE spring.account SET name=?, password=?, level=?, login=?, recommend=? WHERE id = ?",
                new Object[] { user.getName(), user.getPassword(), Level.getIntValue(user.getLevel()), user.getLogin(), user.getRecommend(), id});
    }

    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM spring.account WHERE id=?", new Object[] {id});
    }
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM spring.account");
    }


    public int getCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM spring.account",
                (resultSet, i) -> resultSet.getInt(1));
    }

    private User resultSet2User(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setLevel(Level.getUserLevel(resultSet.getInt("level")));
        user.setLogin(resultSet.getInt("login"));
        user.setRecommend(resultSet.getInt("recommend"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }


}
