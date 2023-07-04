package xyz.necrozma.Refractor;

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
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;


@Permission(name = "refractor.joke", desc = "Allows dad joke command", defaultValue = PermissionDefault.TRUE)
@org.bukkit.plugin.java.annotation.command.Command(name = "joke", desc = "Sends a random dad joke",aliases = {"dadjoke"}, permission = "refractor.joke", permissionMessage = "You do not have permission to use this command!", usage = "/<command>")
public class dadjoke implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;


        if(cmd.getName().equalsIgnoreCase("joke")) {
            HttpResponse<String> httpResponse = Unirest.get("https://icanhazdadjoke.com/")
                    .header("accept", "text/plain")
                    .header("User-Agent", "Refractor by Necrozma, necrozma@catgirlsaresexy.org")
                    .asString();
                    player.sendMessage(httpResponse.getBody());

        }


        return true;
    }


}


