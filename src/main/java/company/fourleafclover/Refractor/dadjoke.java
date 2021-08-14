package company.fourleafclover.Refractor;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class dadjoke implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;





        if(cmd.getName().equalsIgnoreCase("dadjoke")) {
            HttpResponse<JsonNode> httpResponse = Unirest.get("https://icanhazdadjoke.com/")
                    .header("User-Agent", "Keurahs Java Plugin, rep@contact-us.fourleafclover.company")
                    .asJson();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(httpResponse.getBody().toString());
                    String prettyJsonString = gson.toJson(je);
            player.sendMessage(prettyJsonString);





        }


        return true;
    }


}


