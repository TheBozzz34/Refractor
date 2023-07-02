package xyz.necrozma.Refractor;

import io.sentry.Sentry;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class weather implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;


        if(cmd.getName().equalsIgnoreCase("weather")) {
            HttpResponse<String> response = Unirest.get("https://community-open-weather-map.p.rapidapi.com/find?q=" + args[0] + "&cnt=1&mode=null&lon=0&type=link%2C%20accurate&lat=0&units=imperial%2C%20metric")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", args[1])
                    .asString();

                    player.sendMessage(response.getBody());

        }


        return true;
    }


}
