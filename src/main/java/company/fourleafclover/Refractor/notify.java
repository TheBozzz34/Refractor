package company.fourleafclover.Refractor;

import java.lang.Exception;

import dorkbox.notify.Notify;
import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class notify implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage(ChatColor.GREEN + "Notification sent!");

        //generate execution
        if (cmd.getName().equalsIgnoreCase("notify")) {
            Notify.create()
                    .title("Title Text")
                    .text("Hello World!");



        }

        return true;
    }

}
