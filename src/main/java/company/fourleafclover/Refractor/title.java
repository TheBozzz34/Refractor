package company.fourleafclover.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.lang.Nullable;

public class gmc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;



        if(cmd.getName().equalsIgnoreCase("title")) {
            String subtitle = args[0];
            String title = args[1];
            void sendTitle​(@Nullable
                    String title,
                    @Nullable
                            String subtitle,
            int fadeIn,
            int stay,
            int fadeOut)

        }


        return true;
    }


}


