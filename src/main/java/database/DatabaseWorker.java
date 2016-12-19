package database;

import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Provides functionality for adding and deleting users in database.
 * @author Yury Suponev
 */
public class DatabaseWorker {
    private ConnectionManager connectionManager = new ConnectionManager();

    /**
     * Allows to add defined user to database.
     * @param user to add
     */
    public void addUser(User user) {
        Connection connection = connectionManager.openConnection();
        QueryBuilder queryBuilder = new QueryBuilder();
        try {
            connection.createStatement().executeUpdate(queryBuilder.getInsertUserQuery(user));
            for (Map.Entry<String, String> entry : user.getMeta().entrySet()) {
                connection.createStatement().
                            executeUpdate(queryBuilder.
                                    getInsertMetaQuery(user.getID(), entry.getKey(), entry.getValue()));
            }
        } catch (SQLException e) {
            System.out.println("Can't add user.");
        }
        connectionManager.closeConnection(connection);
    }

    /**
     * Allows to delete user from database by ID.
     * @param userID
     */
    public void deleteUser(long userID) {
        Connection connection = connectionManager.openConnection();
        QueryBuilder queryBuilder = new QueryBuilder();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryBuilder.getDeleteUserQuery(userID));
            statement.executeUpdate(queryBuilder.getDeleteMetaQuery(userID));
        } catch (SQLException e) {
            System.out.println("Can't delete user.");
        }
        connectionManager.closeConnection(connection);
    }
}
