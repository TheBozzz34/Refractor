package xyz.necrozma.Refractor.Events;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Main;

import java.io.File;

import static org.bukkit.Bukkit.getServer;


// EventHandler import needed for the event.
// Listener import needed for the event.
// This is the import that holds when the player joins.

public class OnJoin implements Listener {
    Logger logger = LoggerFactory.getLogger(OnJoin.class);

    @EventHandler
    // EventHandler to recognize the event.
    public void onPlayerJoin(PlayerJoinEvent event){
        // todo Automatically download the "PLAYER" expansion

        String joinText = ChatColor.AQUA + "Welcome to the server, %player_name%!";
        joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
        event.setJoinMessage(joinText);
    }

}



