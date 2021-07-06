package company.fourleafclover.Refractor;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;  
import io.sentry.Sentry;
import java.lang.Exception;



public class Refractor extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Refractor 1.4 Is Loaded");
        Sentry.init(options -> {
            options.setDsn("https://6ed02058198b475ea5c08ee012467b5b@o561860.ingest.sentry.io/5851198");
            // Set traces_sample_rate to 1.0 to capture 100% of transactions for performance monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
          });
          try {
            throw new Exception("This is a test.");
          } catch (Exception e) {
            Sentry.captureException(e);
          }
        
       
        
      
    

     
    }
    @Override
    public void onDisable() {
        getLogger().info("Refractor 1.4 is Unloaded");
    }



}

