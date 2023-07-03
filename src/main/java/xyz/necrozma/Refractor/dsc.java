package xyz.necrozma.Refractor;

import dev.dejvokep.boostedyaml.route.Route;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Utilities.Config;

public class dsc implements CommandExecutor {
    Config configManager = Config.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;


        if(cmd.getName().equalsIgnoreCase("dsc")) {
            String DISCORD = configManager.getString(Route.from("discord-link"));
            player.sendMessage(ChatColor.GREEN + "Join our discord at " + DISCORD);

        }


        return true;
    }


}