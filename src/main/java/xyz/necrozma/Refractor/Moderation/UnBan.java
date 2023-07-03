package xyz.necrozma.Refractor.Moderation;

import com.google.gson.Gson;
import io.sentry.Sentry;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Events.OnJoin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static xyz.necrozma.Refractor.Main.database;

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

            HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.mojang.com/users/profiles/minecraft/" + args[0])
                    .header("accept", "application/json")
                    .header("User-Agent", "Refractor Plugin, Contact me at necrozma@catgirlsaresexy.org")
                    .asJson();

            int status = httpResponse.getStatus();
            if (status == 400 || status == 204) {
                commandSender.sendMessage(ChatColor.YELLOW + "Player UUID not found or does not exist");
            } else {
                JsonNode responseBody = httpResponse.getBody();
                String UUID = responseBody.getObject().getString("id");
                logger.info("Unbanning UUID: " + UUID);
                boolean success = deletePlayerBanData(UUID);
                if (success) {
                    commandSender.sendMessage(ChatColor.GREEN + "Player " + UUID + " unbanned successfully!");
                } else {
                    commandSender.sendMessage(ChatColor.YELLOW + "Player ban data not found or failed to delete.");
                }
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
