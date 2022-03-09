package company.fourleafclover.Refractor;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Yeah, I know /give already exists, cope.
public class give implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

//todo Make this actually work

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("give")) {

            if (args.length == 0){
                player.sendMessage(ChatColor.RED + "Correct usage: /give <item> <count>");
                return false;
            }

            if (args.length == 1){
                player.sendMessage(ChatColor.RED + "Correct usage: /give <item> <count>");
                return false;
            }

            String count = args[1];
            int countInt;

            try {
                countInt = Integer.parseInt(count);

                player.sendMessage("item give test");

            } catch (NumberFormatException ex) {
                player.sendMessage(ChatColor.RED + "That is not a valid number");
            }

        }

        return true;
    }


}
