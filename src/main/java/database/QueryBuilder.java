package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Provides building sql query for adding or deleting specified user.
 * @author Yury Suponev
 */
public class QueryBuilder {
    private static final String INSERT_USER = "INSERT INTO wordpress.wp_users (ID, user_login, user_pass, user_nicename," +
            " user_email, user_url, user_registered, user_activation_key, user_status, display_name)" +
            " VALUES (";
    private static final String INSERT_META = "INSERT INTO wordpress.wp_usermeta (umeta_id, user_id, meta_key, meta_value)" +
            " VALUES (NULL, ";
    private static final String DELETE_USER = "DELETE FROM wordpress.wp_users WHERE ID=";
    private static final String DELETE_META = "DELETE FROM wordpress.wp_usermeta WHERE user_id=";

    /**
     * Provides creating SQL INSERT query for adding user to database.
     * @param user to add
     * @return INSERT query
     */
    public String getInsertUserQuery(User user) {
        StringBuilder builder = new StringBuilder(INSERT_USER);
        builder.append(String.valueOf(user.getID())).append(", \"").append(user.getLogin()).
                append("\", MD5(\"").append(user.getPassword()).append("\"), \"").append(user.getLogin()).
                append("\", \"").append(user.getEmail()).append("\", \"\", \"").append(user.getCreationTime()).
                append("\", \"\", 0, \"").append(user.getLogin()).append("\");");
        return builder.toString();
    }

    /**
     * Provides creating SQL INSERT query for adding user's metadata to database.
     * @param ID
     * @param metaKey
     * @param metaValue
     * @return
     */
    public String  getInsertMetaQuery(long ID, String metaKey, String metaValue) {
        StringBuilder builder = new StringBuilder(INSERT_META);
        builder.append(String.valueOf(ID)).append(", \"").append(metaKey).append("\", \'").
                append(metaValue).append("\');");
        return builder.toString();
    }

    /**
     * Provides creating SQL DELETE query for deleting user from database.
     * @param ID user's ID
     * @return DELETE query
     */
    public String getDeleteUserQuery(long ID) {
        return DELETE_USER.concat(String.valueOf(ID)).concat(";");
    }

    /**
     * Provides creating SQL DELETE query for deleting user's metadata from database.
     * @param ID
     * @return
     */
    public String getDeleteMetaQuery(long ID) {
        return DELETE_META.concat(String.valueOf(ID)).concat(";");
    }
}
