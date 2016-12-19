package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides common functionality for database Connection management.
 */
public class ConnectionManager {
    protected DatabaseConfig config = DatabaseConfig.getConfig();

    /**
     * Creates a Connection to a database with specified Connection properties (url, username, password).
     * @return database Connection
     */
    public Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            System.out.println("Error in connection to database server.");
        }
        return connection;
    }

    /**
     * Drops database Connection.
     * @param connection Connection to drop (close)
     */
    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in closing database connection.");
        }
    }
}
