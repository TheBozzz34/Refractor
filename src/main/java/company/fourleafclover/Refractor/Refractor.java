package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;  


public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.1 Is Loaded");
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        
      
    

     
    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.1 is Unloaded");
    }



}

