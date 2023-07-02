package xyz.necrozma.Refractor;

import io.sentry.Sentry;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;


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
        pm.registerEvents(new onjoin(),  this);
    }

}

