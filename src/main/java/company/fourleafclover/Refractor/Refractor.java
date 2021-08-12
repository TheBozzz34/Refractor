package company.fourleafclover.Refractor;

import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import io.sentry.Sentry;
import java.lang.Exception;


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.5 Is Loaded");
        int pluginId = 12406; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        this.getCommand("error").setExecutor(new CommandKit());
        Sentry.init(options -> {
            options.setDsn("https://438653d78f4044eabce86bfac30ec13b@o561860.ingest.sentry.io/5904137");
            // Set traces_sample_rate to 1.0 to capture 100% of transactions for performance monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
        });


    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.5 is Unloaded");
    }





}

