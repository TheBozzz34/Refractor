package xyz.necrozma.Refractor.PlayerManipulation;

import me.clip.placeholderapi.PlaceholderAPI;
import io.sentry.Sentry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.Objects;

@Permission(name = "refractor.getinfo", desc = "Allows getinfo command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "getinfo", desc = "Gets information about a player", permission = "refractor.getinfo", permissionMessage = "You do not have permission to use this command!", usage = "/<command> [target player]"))
public class getinfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if (args.length == 0 ) {
            player.sendMessage("Usage: /getinfo <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(cmd.getName().equalsIgnoreCase("getinfo")) {
            try {
                assert target != null;
                String username = target.getDisplayName();
                Location loc = target.getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                String locale = target.getLocale();
                long time = target.getPlayerTime();
                int ping = target.getPing();
                InetSocketAddress address = target.getAddress();
                World world = target.getWorld();
                player.sendMessage("--------Player: " + target.getDisplayName() + "--------");
                player.sendMessage("Username: " + username);
                player.sendMessage("Location: " + "X: " + x + " Y: " + y + " Z: " + z );
                player.sendMessage("IP address: " + Objects.requireNonNull(target.getAddress()).getAddress());
                player.sendMessage("Playtime: " + time);
                player.sendMessage("Ping: " + ping);
                player.sendMessage("Socket Address: " + address);
                player.sendMessage("World: " + world);
                player.sendMessage("Locale: " + locale);
            } catch (Exception e) {
                Sentry.captureException(e);
            }
        }
        return true;
    }
}