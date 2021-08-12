package company.fourleafclover.Refractor;

import io.sentry.Sentry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command error, String createrror, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                throw new Exception("This is a test.");
            } catch (Exception e) {
                Sentry.captureException(e);
            }
            // Here we need to give items to our player
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }


}
