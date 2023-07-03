package xyz.necrozma.Refractor.Events;


import io.sentry.Sentry;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Utilities.Database;
import static xyz.necrozma.Refractor.Main.database;

import java.sql.Connection;

public class OnJoin implements Listener {
    Logger logger = LoggerFactory.getLogger(OnJoin.class);




    @EventHandler
    // EventHandler to recognize the event.
    public void onPlayerJoin(PlayerJoinEvent event){
        // todo Automatically download the "PLAYER" expansion
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        if (database.isPlayerBanned(playerUUID)) {
            player.kickPlayer(ChatColor.RED + "You are banned from this server.");
            return;
        }


        String joinText = ChatColor.AQUA + "Welcome to the server, %player_name%!";
        joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
        event.setJoinMessage(joinText);
    }

}



