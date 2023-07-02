package xyz.necrozma.Refractor.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OnQuit implements Listener {
    Logger logger = LoggerFactory.getLogger(OnQuit.class);
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
            String quitText = ChatColor.AQUA + "%player_name% has left the server!";
            quitText = PlaceholderAPI.setPlaceholders(event.getPlayer(), quitText);
            event.setQuitMessage(quitText);
        }
}
