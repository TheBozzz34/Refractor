package company.fourleafclover.Refractor;


import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class title implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        Player target = player.getServer().getPlayer(args[0]);

        if(cmd.getName().equalsIgnoreCase("title")) {
            try {
                String MESSAGE = args[1];
                String SUBTITLE = args[2];
                target.sendTitle(MESSAGE, SUBTITLE, 1, 20, 1);
                player.sendMessage("Successfully sent title to " + target.getDisplayName());
            } catch (Exception e) {
                //e.printStackTrace();
                player.sendMessage(ChatColor.RED + "You need to specify a Header and Subtitle!");
                Sentry.captureException(e);
            }

        }
        return true;
    }

}
