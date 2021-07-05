package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        int pluginId = 11941;
        Metrics metrics = new Metrics(this, pluginId);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }


}
