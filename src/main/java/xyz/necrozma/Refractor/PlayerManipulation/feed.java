package xyz.necrozma.Refractor.PlayerManipulation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Permission(name = "refractor.feed", desc = "Allows feed command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "feed", desc = "Feeds a player", permission = "refractor.feed", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class feed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        Logger logger = LoggerFactory.getLogger(feed.class);


        if(cmd.getName().equalsIgnoreCase("feed")) {
            player.setFoodLevel(20);
            player.sendMessage(ChatColor.GOLD+"You have been fed!");

        }


        return true;

    }

}
