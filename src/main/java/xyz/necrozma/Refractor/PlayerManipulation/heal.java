package xyz.necrozma.Refractor.PlayerManipulation;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Permission(name = "refractor.heal", desc = "Allows heal command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "heal", desc = "Heals a player", permission = "refractor.heal", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        player.sendMessage(ChatColor.GOLD+"You have been healed!");

        //generate execution
        if(cmd.getName().equalsIgnoreCase("heal")) {

        }


        return true;
    }
}
