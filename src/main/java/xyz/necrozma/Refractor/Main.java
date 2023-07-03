package xyz.necrozma.Refractor;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.route.Route;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
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
import xyz.necrozma.Refractor.Utilities.Config;
import xyz.necrozma.Refractor.Utilities.Database;
import xyz.necrozma.Refractor.WorldManipulation.day;
import xyz.necrozma.Refractor.WorldManipulation.night;
import xyz.necrozma.Refractor.WorldManipulation.title;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class Main extends JavaPlugin {

    public static Main plugin;
    public PluginDescriptionFile pdf;
    Logger logger = LoggerFactory.getLogger(Main.class);

    private Config configManager;
    public static Database database;


    @Override
    public void onEnable() {

        plugin = this;

        pdf = this.getDescription();

        Config configManager = Config.getInstance();

        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            options.setTracesSampleRate(1.0);
            // Set to true for init messages
            options.setDebug(configManager.getBoolean(Route.from("sentry-debug")));
        });


        String jdbcDriver = configManager.getString(Route.from("mysql-driver"));
        String dbUrl = configManager.getString(Route.from("mysql-url"));
        String username = configManager.getString(Route.from("mysql-username"));
        String password = configManager.getString(Route.from("mysql-password"));

        Connection connection = null;

        try {
            database = new Database(jdbcDriver, dbUrl, username, password);
            database.connect();
            connection = database.getConnection();
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }


        if (configManager.getBoolean(Route.from("bstats"))) {
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
                            + "player_uuid VARCHAR(255) NOT NULL,"
                            + "ban_reason VARCHAR(255) NOT NULL,"
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
        logger.info("Performing shutdown steps.");
        database.disconnect();

    }

    void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),  this);
        pm.registerEvents(new OnQuit(), this);
    }

}

