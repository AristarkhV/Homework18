package dao;

import java.sql.SQLException;

public interface Dao<T> {

    T get(String login) throws SQLException;

    Long getIdByName(String name) throws SQLException;

    void createTable() throws SQLException;

    void insertUser(String name, String password) throws SQLException;
}
