package company.fourleafclover.Refractor;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.5 Is Loaded");
        int pluginId = 12406; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);


    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.5 is Unloaded");
    }



}

