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

@Permission(name = "refractor.GamemodeSpectator", desc = "Allows gmsp command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "gmsp", desc = "Changes a players gamemode to spectator", permission = "refractor.GamemodeSpectator", permissionMessage = "You do not have permission to use this command!", usage = "/<command> (player)"))
public class gmsp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("gmsp")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.GOLD + "Set Gamemode to Spectator!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.GOLD + "Set " + target.getDisplayName() + "'s gamemode to Spectator!");
            } else {
                player.sendMessage("That does not appear to be valid player!");
            }
        }


        return true;
    }


}
