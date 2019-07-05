package dao;

import entity.UserProfile;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UserProfileDao implements Dao<UserProfile> {

    private Executor executor;

    public UserProfileDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    @Override
    public UserProfile get(String login) throws SQLException {
            return executor.execQuery("SELECT * FROM users WHERE login='" + login + "'",
                    resultSet -> {
                        resultSet.next();
                        return new UserProfile(resultSet.getString(2), resultSet.getString(3));
                    });
    }

    public Long getIdByName(String name) throws SQLException {
        return executor.execQuery("select * from users where login='" + name + "'", resultSet -> {
            resultSet.next();
            return resultSet.getLong(1);
        });
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment," +
                "login varchar(256), password varchar(256), primary key(id))");
    }

    public void insertUser(String name, String password) throws SQLException {
        executor.execUpdate("insert into users (login, password) values " +
                "('" + name + "'" + password + "')");
    }
}
