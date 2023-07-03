package xyz.necrozma.Refractor.Moderation;


import io.sentry.Sentry;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import static xyz.necrozma.Refractor.Main.database;

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
            HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.mojang.com/users/profiles/minecraft/" + args[0])
                    .header("accept", "application/json")
                    .header("User-Agent", "Refractor Plugin, Contact me at necrozma@catgirlsaresexy.org")
                    .asJson();

            int status = httpResponse.getStatus();
            String target_uuid_string = null;
            if (status == 400 || status == 204) {
                sender.sendMessage(ChatColor.YELLOW + "Player not found or does not exist");
            } else {
                JsonNode responseBody = httpResponse.getBody();
                String UUID = responseBody.getObject().getString("id");
                if (Objects.equals(UUID, "0232e740c30340a2b170d689eb4c62b3")) {
                    sender.sendMessage(ChatColor.DARK_RED + "(╯°□°)╯︵ ┻━┻, You didn't say the magic word!");
                    return true;
                }
                target_uuid_string = UUID;
                logger.info("Banning UUID: " + UUID);

            };
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
                    player.sendMessage(ChatColor.RED + "Banned UUID: " + playerUUID + " For: " + reason);
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

