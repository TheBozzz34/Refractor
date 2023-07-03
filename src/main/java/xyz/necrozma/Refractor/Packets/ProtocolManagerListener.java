package xyz.necrozma.Refractor.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Main;

import java.util.Objects;

import static xyz.necrozma.Refractor.Main.database;

public class ProtocolManagerListener {

    static Logger logger = LoggerFactory.getLogger(ProtocolManagerListener.class);

    public static void initialize(Plugin plugin) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();

                String UUID = String.valueOf(event.getPlayer().getUniqueId());
                UUID = UUID.replaceAll("-", "");

                if (isMuted(UUID)) {
                    logger.info("[MUTED] " + player.getName() + ": " + packet.getStrings().read(0));
                    event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
                    event.setCancelled(true);
                }
            }
        });
    }

    private static boolean isMuted(String UUID) {
        return database.isPlayerMuted(UUID);
    }
}
