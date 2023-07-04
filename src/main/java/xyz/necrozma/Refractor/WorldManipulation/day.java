package xyz.necrozma.Refractor.WorldManipulation;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

@Permission(name = "refractor.day", desc = "Allows day command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "day", desc = "Changes the time to noon", permission = "refractor.night", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class day implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage(ChatColor.YELLOW + "Time set to day");

        if (cmd.getName().equalsIgnoreCase("day")) {
            player.getWorld().setTime(0);
        }
        return true;
    }

}