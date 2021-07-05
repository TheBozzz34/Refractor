package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
     
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }


}
