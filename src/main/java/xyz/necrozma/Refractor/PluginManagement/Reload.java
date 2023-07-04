package xyz.necrozma.Refractor.PluginManagement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Utilities.Config;

@Permission(name = "refractor.reload", desc = "Allows reload command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "reload", desc = "Reloads the config file", permission = "refractor.reload", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class Reload implements CommandExecutor {
    Config configManager = Config.getInstance();
    Logger logger = LoggerFactory.getLogger(Reload.class);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("reload")) {
            logger.info("Reloading config");
            commandSender.sendMessage(ChatColor.YELLOW + "Reloading config!");
            configManager.Reload();
        }
        return true;
    }
}
