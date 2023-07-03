package xyz.necrozma.Refractor.Moderation;

import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Utilities.PlayerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static xyz.necrozma.Refractor.Main.database;
import static  xyz.necrozma.Refractor.Main.playerUtils;
public class Mute implements CommandExecutor {

    Logger logger = LoggerFactory.getLogger(Ban.class);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("mute")) {
            String mute_reason = null;
            if (strings.length == 0) {
                commandSender.sendMessage(ChatColor.YELLOW + "Usage: /mute <player> <optional reason>");
                return true;
            } else if (strings.length == 1) {
                mute_reason = "unspecified";
            } else {
                mute_reason = strings[1];
            }

            String UUID = playerUtils.UUIDFromStringName(strings[0], commandSender);
            savePlayerMuteData(UUID, mute_reason, commandSender);

        }
        return true;
    }

    public void savePlayerMuteData(String playerUUID, String reason, CommandSender sender) {
        Connection connection = null;

        try {
            connection = database.getConnection();
            // logger.info("Obtained Database Connection");
        } catch (Exception e) {
            Sentry.captureException(e);
            logger.error("There was an error getting the connection");
        }

        if (connection != null) {
            try {
                // Check if the player ban data already exists
                String checkQuery = "SELECT COUNT(*) FROM player_mutes WHERE player_uuid = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, playerUUID);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                resultSet.close();
                checkStatement.close();

                if (count > 0) {
                    sender.sendMessage(ChatColor.YELLOW + "Player already muted.");
                    return;
                }

                // Insert the new player ban data
                String insertQuery = "INSERT INTO player_mutes (player_uuid, reason) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, playerUUID);
                insertStatement.setString(2, reason);

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    sender.sendMessage(ChatColor.RED + "Muted UUID: " + playerUUID + "\nFor: " + reason);
                } else {
                    sender.sendMessage(ChatColor.RED + "Failed to mute player.");
                }

                insertStatement.close();
            } catch (SQLException e) {
                sender.sendMessage(ChatColor.RED + "Failed to save player data. Error: " + e.getMessage());
                logger.error("Failed to save player data. Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.error("Failed to establish a database connection.");
        }

    }
}
