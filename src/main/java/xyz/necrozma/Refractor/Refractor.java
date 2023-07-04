package xyz.necrozma.Refractor;



import dev.dejvokep.boostedyaml.route.Route;

import io.sentry.Sentry;

import org.bstats.bukkit.Metrics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.necrozma.Refractor.Events.OnJoin;
import xyz.necrozma.Refractor.Events.OnQuit;
import xyz.necrozma.Refractor.Events.OnServerLoad;
import xyz.necrozma.Refractor.Gamemodes.gma;
import xyz.necrozma.Refractor.Gamemodes.gmc;
import xyz.necrozma.Refractor.Gamemodes.gms;
import xyz.necrozma.Refractor.Gamemodes.gmsp;
import xyz.necrozma.Refractor.Packets.ProtocolManagerListener;
import xyz.necrozma.Refractor.PlayerManipulation.feed;
import xyz.necrozma.Refractor.PlayerManipulation.getinfo;
import xyz.necrozma.Refractor.PlayerManipulation.give;
import xyz.necrozma.Refractor.PlayerManipulation.heal;
import xyz.necrozma.Refractor.Utilities.CommandManager;
import xyz.necrozma.Refractor.Utilities.Config;
import xyz.necrozma.Refractor.Utilities.Database;
import xyz.necrozma.Refractor.Utilities.PlayerUtils;
import xyz.necrozma.Refractor.WorldManipulation.day;
import xyz.necrozma.Refractor.WorldManipulation.night;
import xyz.necrozma.Refractor.WorldManipulation.title;


@Plugin(name="Refractor", version="4.4.1")
@Description(value = "A simple, human-friendly plugin to ease server administration")
@Author(value = "Necrozma")
@Website(value = "necrozma.xyz")
@Dependency(value = "PlaceholderAPI")
@Dependency(value = "ProtocolLib")
@ApiVersion(ApiVersion.Target.v1_20)
public class Refractor extends JavaPlugin {

    public static Refractor plugin;
    public PluginDescriptionFile pdf;
    Logger logger = LoggerFactory.getLogger(Refractor.class);

    public static Database database;
    public static PlayerUtils playerUtils = new PlayerUtils();


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


        try {
            database = new Database(jdbcDriver, dbUrl, username, password);
            database.connect();
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


        try {
            registerEvents();
        } catch(Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }


        CommandManager commandManager = new CommandManager(this);
        commandManager.registerCommands();




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
        pm.registerEvents(new OnServerLoad(), this);
    }

}

