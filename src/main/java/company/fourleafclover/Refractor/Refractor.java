package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.1 Is Loaded");
        FileConfiguration config = this.getConfig();
        config.addDefault("youAreAwesome", true);
        config.options().copyDefaults(true);
        saveConfig();





     
    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.1 is Unloaded");
    }


}
