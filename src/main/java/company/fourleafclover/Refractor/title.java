package company.fourleafclover.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.lang.Nullable;

public class title implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        Player targetPlayer = player.getServer().getPlayer(args[0]);




        if(cmd.getName().equalsIgnoreCase("title")) {

            String title = args[1];
            String subtitle = args[2];
            targetPlayer.sendTitle(title, subtitle, 1, 20, 1);




        }


        return true;
    }


}


