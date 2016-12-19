package util;

import java.util.Properties;

/**
 * Database connection configuration storage.
 * @author Yury Suponev
 */
public class DatabaseConfig {
    private static final DatabaseConfig config = new DatabaseConfig();
    /**
     * Configurations source file.
     */
    private static final String configSource = "datasource.properties";

    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    private DatabaseConfig() {
        Properties properties = PropertiesExtractor.getPropertiesForPrefix(configSource, "datasource.");
        driverClassName = properties.getProperty("datasource.driverClassName");
        url = properties.getProperty("datasource.url");
        username = properties.getProperty("datasource.username");
        password = properties.getProperty("datasource.password");
        try {
            // Load the Database Driver Class
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't load database driver");
        }
    }

    /**
     * Allows to get database configuration.
     * @return config
     */
    public static DatabaseConfig getConfig() {
        return config;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
