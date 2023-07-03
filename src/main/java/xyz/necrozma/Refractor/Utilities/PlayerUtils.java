package xyz.necrozma.Refractor.Utilities;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerUtils {
    Logger logger = LoggerFactory.getLogger(PlayerUtils.class);
    public String UUIDFromStringName(String username, CommandSender sender) {
        String UUID = null;
        HttpResponse<JsonNode> httpResponse = Unirest.get("https://api.mojang.com/users/profiles/minecraft/" + username)
                .header("accept", "application/json")
                .header("User-Agent", "Refractor Plugin, Contact me at necrozma@catgirlsaresexy.org")
                .asJson();

        int status = httpResponse.getStatus();
        if (status == 400 || status == 204) {
            sender.sendMessage(ChatColor.YELLOW + "Player not found or does not exist");
        } else {
            JsonNode responseBody = httpResponse.getBody();
            UUID = responseBody.getObject().getString("id");
            logger.info("Got UUID: " + UUID);
        };
        return UUID;
    };
}
