package company.fourleafclover.Refractor;

import java.lang.Exception;
import io.sentry.Sentry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class community implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        //generate execution
        if(cmd.getName().equalsIgnoreCase("community")) {
            sender.sendMessage("Join our community at: dsc.gg/t0ast!");
        }


        return true;
    }


}