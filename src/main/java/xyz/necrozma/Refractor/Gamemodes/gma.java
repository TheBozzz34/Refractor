package xyz.necrozma.Refractor.Gamemodes;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Permission(name = "refractor.GamemodeAdventure", desc = "Allows gma command", defaultValue = PermissionDefault.OP)
@org.bukkit.plugin.java.annotation.command.Command(name = "gma", desc = "Changes a players gamemode to adventure", permission = "refractor.GamemodeAdventure", permissionMessage = "You do not have permission to use this command!", usage = "/<command>")
public class gma implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;



        if(cmd.getName().equalsIgnoreCase("gma")) {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(ChatColor.GOLD + "Set Gamemode to Adventure!");
        }


        return true;
    }


}