package xyz.necrozma.Refractor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;

@Permission(name = "refractor.info", desc = "Allows info command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "info", desc = "Information about the plugin", permission = "refractor.info", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class info implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("refractor")) {
            String local_version = Refractor.getPlugin(Refractor.class).pdf.getVersion();

            player.sendMessage(ChatColor.GREEN + "Plugin written by Necrozma, Using version: " + local_version);
        }
        return true;
    }
}
