package xyz.necrozma.Refractor.Utilities;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Main;

import java.sql.*;

public class Database {
    private final Logger logger = LoggerFactory.getLogger(Database.class);

    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;
    private Connection connection;

    public Database(String jdbcDriver, String dbUrl, String username, String password) {
        JDBC_DRIVER = jdbcDriver;
        DB_URL = dbUrl;
        USERNAME = username;
        PASSWORD = password;
    }

    public void connect() {
        try {
            // Register JDBC driver
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException ex) {
                logger.error("Failed to register JDBC driver.", ex);
                Sentry.captureException(ex);
                return;
            }

            // Open a connection
            logger.info("Connecting to the database...");
            try {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {
                logger.error("Failed to establish a database connection. \n Have you edited the config.yml?", ex);
                Sentry.captureException(ex);
                return;
            }

            // Connection successful
            logger.info("Connected to the database.");

        } catch (Exception e) {
            logger.error("An unexpected exception occurred while connecting to the database. \n Have you edited the config.yml?");
            Sentry.captureException(e);
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                logger.info("Disconnected from the database.");
            }
        } catch (SQLException ex) {
            logger.error("Failed to close the database connection.", ex);
            Sentry.captureException(ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isPlayerBanned(String playerUUID) {
        Connection connection = null;
        boolean isBanned = false;

        try {
            connection = getConnection();
            String query = "SELECT COUNT(*) FROM player_bans WHERE player_uuid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerUUID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            statement.close();

            isBanned = count > 0;
        } catch (SQLException e) {
            logger.error("Failed to check player ban status. Error: " + e.getMessage());
            Sentry.captureException(e);
        }

        return isBanned;
    }
}
