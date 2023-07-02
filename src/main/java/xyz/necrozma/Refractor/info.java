package xyz.necrozma.Refractor;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class info implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("refractor")) {
            HttpResponse<JsonNode> version_json  = Unirest.get("https://api.spiget.org/v2/resources/96459/versions/latest")
                    .header("accept", "application/json")
                    .header("x-custom-header", "Java Plugin by Sada/n#0001")
                    .asJson();

            JSONObject raw = version_json.getBody().getObject();
            String version = raw.getString("name");
            String LOCALVERSION = Main.getPlugin(Main.class).getConfig().getString("version");

            player.sendMessage(ChatColor.GREEN + "Refractor plugin by Sada\\n#9264, Remote version: " + version + " Using version: " + LOCALVERSION);
        }
        return true;
    }


}
