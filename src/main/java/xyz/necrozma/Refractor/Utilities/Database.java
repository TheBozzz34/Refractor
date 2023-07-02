package xyz.necrozma.Refractor.Utilities;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
                logger.error("Failed to establish a database connection.", ex);
                Sentry.captureException(ex);
                return;
            }

            // Connection successful
            logger.info("Connected to the database.");

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
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
}
