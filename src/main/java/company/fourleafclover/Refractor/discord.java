package company.fourleafclover.Refractor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.entity.channel.TextChannel;

public class discord {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();



        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }

}
