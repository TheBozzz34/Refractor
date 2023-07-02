package xyz.necrozma.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class dsc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        Logger logger = LoggerFactory.getLogger(dsc.class);


        if(cmd.getName().equalsIgnoreCase("dsc")) {
            String DISCORD = Main.getPlugin(Main.class).getConfig().getString("discord-link");
            player.sendMessage(ChatColor.GREEN + "Join our discord at " + DISCORD);

        }


        return true;
    }


}