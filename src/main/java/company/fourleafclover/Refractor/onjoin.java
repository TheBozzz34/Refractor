package company.fourleafclover.Refractor;


import me.clip.placeholderapi.PlaceholderAPI;
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
        String joinText = "&c%player_name% joined the server!";
        joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
        event.setJoinMessage(joinText);

    }
}