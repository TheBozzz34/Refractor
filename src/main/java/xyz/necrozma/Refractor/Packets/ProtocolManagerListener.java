package xyz.necrozma.Refractor.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dev.dejvokep.boostedyaml.route.Route;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Utilities.Config;

import static xyz.necrozma.Refractor.Refractor.database;

public class ProtocolManagerListener {

    static Logger logger = LoggerFactory.getLogger(ProtocolManagerListener.class);
    static Config configManager = Config.getInstance();

    public static void initialize(Plugin plugin) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();

                String message = packet.getStrings().read(0);

                String UUID = String.valueOf(event.getPlayer().getUniqueId());
                UUID = UUID.replaceAll("-", "");

                if (isMuted(UUID)) {
                    logger.info("[MUTED] " + player.getName() + ": " + message);
                    event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
                    event.setCancelled(true);
                } else if (event.getPlayer().isOp() && configManager.getBoolean(Route.from("highlight-operators"))) {
                    event.setCancelled(true);
                    String username = event.getPlayer().getDisplayName();
                    username = ChatColor.WHITE + "<" + ChatColor.RED + username + ChatColor.WHITE + ">";
                    message = username + " " + ChatColor.WHITE + message;
                    event.getPlayer().getServer().broadcastMessage(message);
                }

            }
        });
    }

    private static boolean isMuted(String UUID) {
        return database.isPlayerMuted(UUID);
    }
}
