package xyz.necrozma.Refractor.Gamemodes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Permission(name = "refractor.GamemodeSurvival", desc = "Allows gms command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "gms", desc = "Changes a players gamemode to survival", permission = "refractor.GamemodeSurvival", permissionMessage = "You do not have permission to use this command!", usage = "/<command> (player)"))
public class gms implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("gms")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.GOLD + "Set Gamemode to Survival!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.GOLD + "Set " + target.getDisplayName() + "'s gamemode to Survival!");
            } else {
                player.sendMessage("That does not appear to be valid player!");
            }
        }

        return true;
    }

}