package xyz.necrozma.Refractor.Moderation;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static xyz.necrozma.Refractor.Refractor.database;
import static  xyz.necrozma.Refractor.Refractor.playerUtils;

@Permission(name = "refractor.unmute", desc = "Allows unmute command", defaultValue = PermissionDefault.OP)
@org.bukkit.plugin.java.annotation.command.Command(name = "unmute", desc = "Unmutes a player", permission = "refractor.unmute", permissionMessage = "You do not have permission to use this command!", usage = "/<command> [target player]")
public class Unmute implements CommandExecutor {
    Logger logger = LoggerFactory.getLogger(Unmute.class);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unmute")) {
            if (args.length == 0) {
                commandSender.sendMessage("Usage: /unmute <Player>");
                return true;
            }

            if (args.length > 1) {
                commandSender.sendMessage("Usage: /unmute <Player>");
                return true;
            }
            String UUID = playerUtils.UUIDFromStringName(args[0], commandSender);
            Boolean success = deletePlayerMuteData(UUID);
            if (success) {
                commandSender.sendMessage(ChatColor.GREEN + "Unmuted player!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "Unable to unmute player!");
            }


        }
        return true;
    }
    public boolean deletePlayerMuteData(String playerUUID) {
        Connection connection = null;
        boolean success = false;

        try {
            connection = database.getConnection();
            String deleteQuery = "DELETE FROM player_mutes WHERE player_uuid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, playerUUID);

            int rowsAffected = preparedStatement.executeUpdate();
            success = rowsAffected > 0;

            preparedStatement.close();
        } catch (SQLException e) {
            logger.error("Failed to delete player data. Error: " + e.getMessage());
            Sentry.captureException(e);
        }

        return success;
    }
}
