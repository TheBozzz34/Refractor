package xyz.necrozma.Refractor;

import io.sentry.Sentry;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Events.OnJoin;
import xyz.necrozma.Refractor.Events.OnQuit;
import xyz.necrozma.Refractor.Gamemodes.gma;
import xyz.necrozma.Refractor.Gamemodes.gmc;
import xyz.necrozma.Refractor.Gamemodes.gms;
import xyz.necrozma.Refractor.Gamemodes.gmsp;
import xyz.necrozma.Refractor.Moderation.Ban;
import xyz.necrozma.Refractor.PlayerManipulation.feed;
import xyz.necrozma.Refractor.PlayerManipulation.getinfo;
import xyz.necrozma.Refractor.PlayerManipulation.give;
import xyz.necrozma.Refractor.PlayerManipulation.heal;
import xyz.necrozma.Refractor.Utilities.Database;
import xyz.necrozma.Refractor.WorldManipulation.day;
import xyz.necrozma.Refractor.WorldManipulation.night;
import xyz.necrozma.Refractor.WorldManipulation.title;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.bukkit.Bukkit.getServer;


public class Main extends JavaPlugin {

    public static Main plugin;
    public PluginDescriptionFile pdf;
    Logger logger = LoggerFactory.getLogger(Main.class);

    String jdbcDriver = this.getConfig().getString("mysql-driver");
    String dbUrl = this.getConfig().getString("mysql-url");
    String username = this.getConfig().getString("mysql-username");
    String password = this.getConfig().getString("mysql-password");
    public static Database database;


    @Override
    public void onEnable() {

        plugin = this;

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                logger.info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                logger.info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        FileConfiguration config = this.getConfig();
        config.addDefault("bstats", true);
        config.addDefault("sentry-debug", false);
        config.addDefault("discord-link", "Discord Server Invite URL");
        config.addDefault("mysql-driver", "com.mysql.cj.jdbc.Driver  # DO NOT CHANGE UNLESS YOU KNOW WHAT YOU ARE DOING");
        config.addDefault("mysql-url", "jdbc:mysql://localhost:3306/mydatabase");
        config.addDefault("mysql-username", "your-username");
        config.addDefault("mysql-password", "your-password");
        config.options().copyDefaults(true);
        saveConfig();


        pdf = this.getDescription();

        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            options.setTracesSampleRate(1.0);
            // Set to true for init messages
            options.setDebug(config.getBoolean("sentry-debug"));
        });

        Connection connection = null;

        try {
            database = new Database(jdbcDriver, dbUrl, username, password);
            database.connect();
            connection = database.getConnection();
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }


        if (config.getBoolean("bstats")) {
            int pluginId = 12406;
            Metrics metrics = new Metrics(this, pluginId);
            logger.info("Enabled Bstats");
        } else {
            logger.info("Disabling bstats because of config");
        }

        File playerExpansion = getDataFolder().getParentFile();
        File playerExpansionJar = new File(playerExpansion.getPath()+File.separator+"PlaceholderAPI"+File.separator+"expansions"+File.separator+"Expansion-player.jar");
        //File playerExpansion = new File("/PlaceholderAPI/expansions/Expansion-player.jar");

        boolean expansionExists = playerExpansionJar.exists();

        if (!expansionExists) {
            logger.error("No PLAYER expansion, please install the PLAYER expansion from PlaceHolderAPI");
            logger.error("-----------------------------------------------------");
            logger.error("You can do so via \"/papi ecloud download player\"");

        }

        commands commands = new commands();

        try {
            getCommand("generror").setExecutor(commands);
            getCommand("dsc").setExecutor(new dsc());
            getCommand("feed").setExecutor(new feed());
            getCommand("heal").setExecutor(new heal());
            getCommand("day").setExecutor(new day());
            getCommand("night").setExecutor(new night());
            getCommand("gmc").setExecutor(new gmc());
            getCommand("gms").setExecutor(new gms());
            getCommand("gma").setExecutor(new gma());
            getCommand("gmsp").setExecutor(new gmsp());
            getCommand("dadjoke").setExecutor(new dadjoke());
            getCommand("getinfo").setExecutor(new getinfo());
            getCommand("refractor").setExecutor(new info());;
            getCommand("title").setExecutor(new title());
            getCommand("give").setExecutor(new give());
            getCommand("Ban").setExecutor(new Ban());
            logger.info("Successfully Loaded Commands");

        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        try {
            registerEvents();
        } catch(Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        logger.info("Running Database Tasks");
        try {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    String createTableQuery = "CREATE TABLE IF NOT EXISTS player_bans ("
                            + "id INT PRIMARY KEY AUTO_INCREMENT,"
                            + "player_name VARCHAR(255) NOT NULL,"
                            + "ban_reason VARCHAR(255) NOT NULL,"
                            + "ban_duration INT NOT NULL,"
                            + "ban_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                            + ") COLLATE=utf8_general_ci";
                    statement.executeUpdate(createTableQuery);
                    logger.info("Tasks queried successfully!");
                } catch (SQLException e) {
                    logger.error("Failed to create table 'player_bans'. Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                logger.error("Failed to establish a database connection.");
            }


        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }

    }

    public void onDisable() {
        logger.info("Shutting down database connection");
        database.disconnect();

    }

    void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),  this);
        pm.registerEvents(new OnQuit(), this);
    }

}

