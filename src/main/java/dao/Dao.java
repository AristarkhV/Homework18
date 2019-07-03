package dao;

public interface Dao<T> {

    T get(String login);

    Long getIdByName(String name);

    void createTable();

    void insertUser(String name, String password);
}
