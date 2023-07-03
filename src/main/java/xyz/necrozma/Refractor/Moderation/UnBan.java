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

@Permission(name = "refractor.unban", desc = "Allows unban command", defaultValue = PermissionDefault.OP)
@org.bukkit.plugin.java.annotation.command.Command(name = "unban", desc = "Unbans a player", permission = "refractor.unban", permissionMessage = "You do not have permission to use this command!", usage = "/<command> [target player]")
public class UnBan implements CommandExecutor {
    Logger logger = LoggerFactory.getLogger(UnBan.class);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unban")) {
            if (args.length == 0) {
                commandSender.sendMessage("Usage: /unban <Player>");
                return true;
            }

            if (args.length > 1) {
                commandSender.sendMessage("Usage: /unban <Player>");
                return true;
            }


            String UUID = playerUtils.UUIDFromStringName(args[0], commandSender);
            logger.info("Unbanning UUID: " + UUID);
            boolean success = deletePlayerBanData(UUID);
            if (success) {
                commandSender.sendMessage(ChatColor.GREEN + "Player " + UUID + " unbanned successfully!");
            } else {
                commandSender.sendMessage(ChatColor.YELLOW + "Player ban data not found or failed to delete.");
            }


        }
        return true;
    }
    public boolean deletePlayerBanData(String playerUUID) {
        Connection connection = null;
        boolean success = false;

        try {
            connection = database.getConnection();
            String deleteQuery = "DELETE FROM player_bans WHERE player_uuid = ?";
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
