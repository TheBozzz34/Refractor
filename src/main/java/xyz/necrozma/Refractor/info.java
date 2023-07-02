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
            String local_version = Main.getPlugin(Main.class).pdf.getVersion();

            player.sendMessage(ChatColor.GREEN + "Plugin written by Necrozma, Using version: " + local_version);
        }
        return true;
    }


}
