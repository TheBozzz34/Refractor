package xyz.necrozma.Refractor.Events;

import io.sentry.Sentry;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Main;

import java.io.File;

import static xyz.necrozma.Refractor.Main.plugin;


public class OnServerLoad implements Listener {
    Logger logger = LoggerFactory.getLogger(OnServerLoad.class);
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        File playerExpansion = Main.getPlugin(Main.class).getDataFolder().getParentFile();
        File playerExpansionJar = new File(playerExpansion.getPath()+File.separator+"PlaceholderAPI"+File.separator+"expansions"+File.separator+"Expansion-player.jar");

        boolean expansionExists = playerExpansionJar.exists();

        if (!expansionExists) {
            logger.error("No PLAYER expansion, Please run /papi ecloud download player. This plugin will now be disabled.");
            Main.getPlugin(Main.class).getPluginLoader().disablePlugin(plugin);
        }
    }
}
