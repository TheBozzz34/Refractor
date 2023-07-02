package xyz.necrozma.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gms implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;



        if(cmd.getName().equalsIgnoreCase("gms")) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.GOLD + "Set Gamemode to Survival!");
        }


        return true;
    }


}