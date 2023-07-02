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
import xyz.necrozma.Refractor.PlayerManipulation.feed;
import xyz.necrozma.Refractor.PlayerManipulation.getinfo;
import xyz.necrozma.Refractor.PlayerManipulation.give;
import xyz.necrozma.Refractor.PlayerManipulation.heal;
import xyz.necrozma.Refractor.WorldManipulation.day;
import xyz.necrozma.Refractor.WorldManipulation.night;
import xyz.necrozma.Refractor.WorldManipulation.title;

import java.io.File;

import static org.bukkit.Bukkit.getServer;


public class Main extends JavaPlugin {

    public static Main plugin;

    public PluginDescriptionFile pdf;

    @Override
    public void onEnable() {

        plugin = this;
        Logger logger = LoggerFactory.getLogger(Main.class);

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
        config.options().copyDefaults(true);
        saveConfig();


        pdf = this.getDescription();

        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            options.setTracesSampleRate(1.0);
            // Set to true for init messages
            options.setDebug(config.getBoolean("sentry-debug"));
        });


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

    }

    void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoin(),  this);
        pm.registerEvents(new OnQuit(), this);
    }

}

