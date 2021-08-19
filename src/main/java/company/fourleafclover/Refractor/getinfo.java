package company.fourleafclover.Refractor;

import io.sentry.Sentry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class getinfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);



        if(cmd.getName().equalsIgnoreCase("getinfo")) {
            try {
                String username = target.getDisplayName();
                Location loc = target.getLocation();
                String locale = target.getLocale();
                long time = target.getPlayerTime();
                int ping = target.getPing();
                InetSocketAddress address = target.getAddress();
                World world = target.getWorld();
                player.sendMessage("--------Player: " + target.getDisplayName() + "--------");
                player.sendMessage("Username: " + username);
                player.sendMessage("Location: " + loc);
                player.sendMessage("IP address: %otherplayer_ip_" + target.getDisplayName() + "%");
                player.sendMessage("Playtime: " + time);
                player.sendMessage("Ping: " + ping);
                player.sendMessage("Socket Address: " + address);
                player.sendMessage("World: " + world);

            } catch (Exception e) {
                Sentry.captureException(e);
            }





        }


        return true;
    }


}