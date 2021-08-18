package company.fourleafclover.Refractor;

import org.bukkit.event.EventHandler;
// EventHandler import needed for the event.
import org.bukkit.event.Listener;
// Listener import needed for the event.
import org.bukkit.event.player.PlayerJoinEvent;
// This is the import that holds when the player joins.

public class onjoin implements Listener {
    @EventHandler
    // EventHandler to recognize the event.
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("§cWelcome to the server %player_name%!");

    }
}