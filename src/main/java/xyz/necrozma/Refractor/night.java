package xyz.necrozma.Refractor;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class night implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("night")) {
            player.getWorld().setTime(18000);
            player.sendMessage(ChatColor.BLUE+"Set time to night");
        }
        return true;
    }


}