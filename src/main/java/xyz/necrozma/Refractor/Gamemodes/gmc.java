package xyz.necrozma.Refractor.Gamemodes;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Permission(name = "refractor.GamemodeCreative", desc = "Allows gmc command", defaultValue = PermissionDefault.OP)
@org.bukkit.plugin.java.annotation.command.Command(name = "gmc", desc = "Changes a players gamemode to creative", permission = "refractor.GamemodeCreative", permissionMessage = "You do not have permission to use this command!", usage = "/<command>")
public class gmc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;


        if (cmd.getName().equalsIgnoreCase("gmc")) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GOLD + "Set Gamemode to Creative!");
        }


        return true;
    }

}
