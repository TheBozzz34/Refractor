package xyz.necrozma.Refractor;


import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.dejvokep.boostedyaml.route.Route;

import io.sentry.Sentry;

import org.bstats.bukkit.Metrics;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.necrozma.Refractor.Events.OnJoin;
import xyz.necrozma.Refractor.Events.OnQuit;
import xyz.necrozma.Refractor.Packets.ProtocolManagerListener;
import xyz.necrozma.Refractor.Utilities.CommandManager;
import xyz.necrozma.Refractor.Utilities.Config;
import xyz.necrozma.Refractor.Utilities.Database;
import xyz.necrozma.Refractor.Utilities.PlayerUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JavaPlugin {

    public static Main plugin;
    public PluginDescriptionFile pdf;
    Logger logger = LoggerFactory.getLogger(Main.class);
    public static Database database;
    public static PlayerUtils playerUtils = new PlayerUtils();
    private CommandManager commandManager;


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

        commandManager = new CommandManager(this);
        commandManager.registerCommands();

        try {
            registerEvents();
        } catch(Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }

        database.RunQueries();

        ProtocolManagerListener.initialize(this);

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

