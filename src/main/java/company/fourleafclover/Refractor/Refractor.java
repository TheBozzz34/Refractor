package company.fourleafclover.Refractor;

//imports
import io.sentry.Sentry;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class Refractor extends JavaPlugin implements Listener {

    private Economy econ;

    @Override
    public void onEnable() {
        Logger logger = LoggerFactory.getLogger(Refractor.class);
        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            // Set traces_sample_rate to 1.0 to capture 100% of transactions for performance monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
        });

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

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
        config.options().copyDefaults(true);
        saveConfig();
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
            logger.info("Successfully Loaded Commands");

        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);

        }


        //getLogger().info(ChatColor.GREEN + "Refractor " + version + " Loaded");
        logger.info("Hello World");


    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


}