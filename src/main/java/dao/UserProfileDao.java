package dao;

import entity.UserProfile;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UserProfileDao implements Dao<UserProfile> {

    private Executor executor;
    private static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT AUTO_INCREMENT, login VARCHAR(256), password VARCHAR(256), PRIMARY KEY (id))";

    public UserProfileDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    @Override
    public UserProfile get(String login) {
        try {
            return executor.execQuery("SELECT * FROM users WHERE login='" + login + "'",
                    resultSet -> {
                        resultSet.next();
                        return new UserProfile(resultSet.getString(2), resultSet.getString(3));
                    });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getIdByName(String name){
        try {
            return executor.execQuery("SELECT * FROM users WHERE login='" + name + "'",
                    resultSet -> {
                        resultSet.next();
                        return resultSet.getLong(1);
                    });
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public void createTable() {
        try {
            executor.execUpdate(CREATE_USERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUser(String name, String password) {
        try {
            executor.execUpdate("INSERT INTO users (login, password) VALUES ('" + name + "', '" + password + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
