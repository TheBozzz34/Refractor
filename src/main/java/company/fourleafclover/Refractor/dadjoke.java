package company.fourleafclover.Refractor;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            HttpResponse<String> httpResponse = Unirest.get("https://icanhazdadjoke.com/")
                    .header("accept", "text/plain")
                    .header("User-Agent", "Keurahs Java Plugin, rep@contact-us.fourleafclover.company")
                    .asString();
                    player.sendMessage(httpResponse.getBody());





        }


        return true;
    }


}


