package xyz.necrozma.Refractor.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Refractor;

import java.io.File;

import static xyz.necrozma.Refractor.Refractor.plugin;

public class OnServerLoad implements Listener {
    Logger logger = LoggerFactory.getLogger(OnServerLoad.class);
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        File playerExpansion = Refractor.getPlugin(Refractor.class).getDataFolder().getParentFile();
        File playerExpansionJar = new File(playerExpansion.getPath()+File.separator+"PlaceholderAPI"+File.separator+"expansions"+File.separator+"Expansion-player.jar");

        boolean expansionExists = playerExpansionJar.exists();

        if (!expansionExists) {
            logger.error("No PLAYER expansion, Please run /papi ecloud download player. This plugin will now be disabled.");
            Refractor.getPlugin(Refractor.class).getPluginLoader().disablePlugin(plugin);
        }
    }
}