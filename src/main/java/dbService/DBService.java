package dbService;

import entity.UserProfile;
import dao.UserProfileDao;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBService {
    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(DBService.class.getName());

    public DBService() throws SQLException {
        connection = getH2Connection();
    }

    public static Connection getH2Connection() throws SQLException {
            static final String URL = "jdbc:h2:./h2db";
            static final String NAME = "tully";
            static final String PASSWORD = "tully";
        try {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(URL);
            dataSource.setUser(NAME);
            dataSource.setPassword(PASSWORD);

            Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed connect", e);
        }
        throw new SQLException();
    }

    public UserProfile getUser(String login) throws SQLException {
        return new UserProfileDao(connection).get(login);
    }

    public long addUser(String name, String password) throws SQLException {
        try {
            connection.setAutoCommit(false);
            UserProfileDao dao = new UserProfileDao(connection);
            dao.createTable();
            dao.insertUser(name, password);
            connection.commit();
            return dao.getIdByName(name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Failed connect ", e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed user addition ", e);
            }
        }
        throw new SQLException();
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed print connect ", e);
        }
    }
}
