package xyz.necrozma.Refractor.Moderation;


import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static xyz.necrozma.Refractor.Refractor.database;
import static  xyz.necrozma.Refractor.Refractor.playerUtils;
@Permission(name = "refractor.ban", desc = "Allows ban command", defaultValue = PermissionDefault.OP)
@org.bukkit.plugin.java.annotation.command.Command(name = "ban", desc = "Bans a player", permission = "refractor.ban", permissionMessage = "You do not have permission to use this command!", usage = "/<command> [target player] (reason)")
public class Ban implements CommandExecutor {
    Logger logger = LoggerFactory.getLogger(Ban.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("Ban")) {

            if (args.length == 0) { // Command issued with no arguments
                player.sendMessage("Usage: /ban <player> [optional reason]");
                return true;
            }
            String target_uuid_string = playerUtils.UUIDFromStringName(args[0], sender);
            String ban_reason;

            if (args.length == 2 ) {
                ban_reason = args[1];
            } else {
                ban_reason = "Unspecified";
            }

            savePlayerBanData(target_uuid_string, ban_reason, player);

            Player target = sender.getServer().getPlayer(args[0]);
            if(target != null) {
                target.kickPlayer(ChatColor.RED + "You have been banned for " + ban_reason);
            }
            // player.sendMessage(ChatColor.RED + "Banning UUID: " + target_uuid_string + " For: " + ban_reason);


        }

        return true;
    }

    public void savePlayerBanData(String playerUUID, String reason, Player player) {
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
                String checkQuery = "SELECT COUNT(*) FROM player_bans WHERE player_uuid = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, playerUUID);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                resultSet.close();
                checkStatement.close();

                if (count > 0) {
                    player.sendMessage(ChatColor.YELLOW + "Player already banned.");
                    return;
                }

                // Insert the new player ban data
                String insertQuery = "INSERT INTO player_bans (player_uuid, ban_reason) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, playerUUID);
                insertStatement.setString(2, reason);

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    player.sendMessage(ChatColor.RED + "Banned UUID: " + playerUUID + "\nFor: " + reason);
                } else {
                    player.sendMessage(ChatColor.RED + "Failed to ban player.");
                }

                insertStatement.close();
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "Failed to save player data. Error: " + e.getMessage());
                logger.error("Failed to save player data. Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.error("Failed to establish a database connection.");
        }

    }

}

