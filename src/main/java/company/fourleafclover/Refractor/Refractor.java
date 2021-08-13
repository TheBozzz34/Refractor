package company.fourleafclover.Refractor;


import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import io.sentry.Sentry;

import java.io.File;
import java.lang.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);

        }
        FileConfiguration config = this.getConfig();
        config.addDefault("bstats", true);
        config.addDefault("version", "1.7.1");
        config.options().copyDefaults(true);
        saveConfig();
        String version = getConfig().getString("version");
        Logger logger = LoggerFactory.getLogger(Refractor.class);
        if (config.getBoolean("bstats")) {
            int pluginId = 12406; // <-- Replace with the id of your plugin!
            Metrics metrics = new Metrics(this, pluginId);
            logger.info("Enabled Bstats");
        } else {
            logger.info("Disabling bstats because of config");
        }
        commands commands = new commands();
        getCommand("generror").setExecutor(commands);
        getCommand("dsc").setExecutor(new dsc());
        getCommand("dsc").setExecutor(new feed());
        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            // Set traces_sample_rate to 1.0 to capture 100% of transactions for performance monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
        });

        getLogger().info(ChatColor.GREEN + "Refractor " + version + " Loaded");
        logger.info("Hello World");


    }
    @Override
    public void onDisable() {
        String version = getConfig().getString("version");
        getLogger().info(ChatColor.RED + "Refractor "  + version +  " is Unloaded");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("Welcome, " + e.getPlayer().getDisplayName() + ", have a great time!");
    }




}


