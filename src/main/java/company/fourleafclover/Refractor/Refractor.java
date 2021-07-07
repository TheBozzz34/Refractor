package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;  

import company.fourleafclover.Metrics;



public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.4 Is Loaded");
        int pluginId = 11941; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        
          
        
       
        
      
    

     
    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.4 is Unloaded");
    }



}

