package xyz.necrozma.Refractor.Gamemodes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

@Permission(name = "refractor.GamemodeAdventure", desc = "Allows gma command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "gma", desc = "Changes a players gamemode to adventure", permission = "refractor.GamemodeAdventure", permissionMessage = "You do not have permission to use this command!", usage = "/<command> (player)"))
public class gma implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("gma")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GOLD + "Set Gamemode to Adventure!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GOLD + "Set " + target.getDisplayName() + "'s gamemode to Adventure!");
            } else {
                player.sendMessage("That does not appear to be valid player!");
            }
        }


        return true;
    }


}