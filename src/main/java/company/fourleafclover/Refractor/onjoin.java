package company.fourleafclover.Refractor;


import io.sentry.Sentry;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// EventHandler import needed for the event.
// Listener import needed for the event.
// This is the import that holds when the player joins.

public class onjoin implements Listener {
    @EventHandler
    // EventHandler to recognize the event.
    public void onPlayerJoin(PlayerJoinEvent event){

        try {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String ecloud = "papi ecloud download player";
            String reload = "papi reload";
            Bukkit.dispatchCommand(console, ecloud);
        } catch (Exception error) {
            error.printStackTrace();
            Sentry.captureException(error);

        }
        String joinText = "&c%player_name% joined the server!";
        joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
        event.setJoinMessage(joinText);

    }
}



