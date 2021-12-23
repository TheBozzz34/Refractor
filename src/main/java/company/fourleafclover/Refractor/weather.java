package company.fourleafclover.Refractor;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class weather implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;


        if(cmd.getName().equalsIgnoreCase("weather")) {
            String APIKEY = Refractor.getPlugin(Refractor.class).getConfig().getString("weather-api-key");
            player.sendMessage(APIKEY);

        }


        return true;
    }


}