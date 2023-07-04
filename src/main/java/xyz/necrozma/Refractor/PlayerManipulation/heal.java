package xyz.necrozma.Refractor.PlayerManipulation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Permission(name = "refractor.heal", desc = "Allows heal command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "heal", desc = "Heals a player", permission = "refractor.heal", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class heal implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
                player.sendMessage(ChatColor.GOLD+"You have been healed!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
                player.sendMessage(ChatColor.GOLD + target.getDisplayName() + " has have been healed!");
            } else {
                player.sendMessage("That does not appear to be valid player!");
            };
        }
        return true;
    }
}
