package company.fourleafclover.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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