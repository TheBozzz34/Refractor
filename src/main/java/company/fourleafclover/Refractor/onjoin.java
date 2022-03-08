package company.fourleafclover.Refractor;


import io.sentry.Sentry;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

// EventHandler import needed for the event.
// Listener import needed for the event.
// This is the import that holds when the player joins.

public class onjoin implements Listener {
    Logger logger = LoggerFactory.getLogger(onjoin.class);

    @EventHandler
    // EventHandler to recognize the event.
    public void onPlayerJoin(PlayerJoinEvent event){

        File playerExpansion = Refractor.getPlugin(Refractor.class).getDataFolder().getParentFile();
        File playerExpansionJar = new File(playerExpansion.getPath()+File.separator+"PlaceholderAPI"+File.separator+"expansions"+File.separator+"Expansion-player.jar");
        //File playerExpansion = new File("/PlaceholderAPI/expansions/Expansion-player.jar");

        boolean expansionExists = playerExpansionJar.exists();

        if (expansionExists == false) {
            logger.error("No PLAYER expansion, notifying server ops");
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                if (all.isOp()) {
                    all.sendMessage(ChatColor.DARK_RED + "Please install the PLAYER PlaceHolderAPI expansion.");
                    all.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");
                    all.sendMessage(ChatColor.GREEN + "You can do so via \"/papi ecloud download player\"");
                }
            }

            String joinText = "&cNo PlaceHolderAPI extension";
            joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
            event.setJoinMessage(joinText);


        }   else {
            logger.info("Player Expansion is Loaded!");

            String joinText = "&c%player_name% joined the server!";
            joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
            event.setJoinMessage(joinText);

        }





    }
}



