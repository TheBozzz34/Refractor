package company.fourleafclover.Refractor;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class day implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage(ChatColor.YELLOW + "Time set to day");

        if (cmd.getName().equalsIgnoreCase("day")) {
            player.getWorld().setTime(0);
        }
        return true;
    }

}


