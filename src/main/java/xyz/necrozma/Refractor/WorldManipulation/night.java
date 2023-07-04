package xyz.necrozma.Refractor.WorldManipulation;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Permission(name = "refractor.night", desc = "Allows night command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "night", desc = "Changes to time to midnight", permission = "refractor.night", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class night implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("night")) {
            player.getWorld().setTime(18000);
            player.sendMessage(ChatColor.BLUE+"Set time to night");
        }
        return true;
    }


}