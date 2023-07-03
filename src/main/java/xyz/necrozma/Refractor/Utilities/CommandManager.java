package xyz.necrozma.Refractor.Utilities;

import io.sentry.Sentry;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.dadjoke;
import xyz.necrozma.Refractor.dsc;
import xyz.necrozma.Refractor.Gamemodes.gma;
import xyz.necrozma.Refractor.Gamemodes.gmc;
import xyz.necrozma.Refractor.Gamemodes.gms;
import xyz.necrozma.Refractor.Gamemodes.gmsp;
import xyz.necrozma.Refractor.Moderation.Ban;
import xyz.necrozma.Refractor.Moderation.Mute;
import xyz.necrozma.Refractor.Moderation.UnBan;
import xyz.necrozma.Refractor.Moderation.Unmute;
import xyz.necrozma.Refractor.PlayerManipulation.feed;
import xyz.necrozma.Refractor.PlayerManipulation.getinfo;
import xyz.necrozma.Refractor.PlayerManipulation.give;
import xyz.necrozma.Refractor.PlayerManipulation.heal;
import xyz.necrozma.Refractor.WorldManipulation.day;
import xyz.necrozma.Refractor.WorldManipulation.night;
import xyz.necrozma.Refractor.WorldManipulation.title;
import xyz.necrozma.Refractor.info;


public class CommandManager {
    private final JavaPlugin plugin;

    Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        try {
            setCommandExecutor("dsc", new dsc());
            setCommandExecutor("feed", new feed());
            setCommandExecutor("heal", new heal());
            setCommandExecutor("day", new day());
            setCommandExecutor("night", new night());
            setCommandExecutor("gmc", new gmc());
            setCommandExecutor("gms", new gms());
            setCommandExecutor("gma", new gma());
            setCommandExecutor("gmsp", new gmsp());
            setCommandExecutor("dadjoke", new dadjoke());
            setCommandExecutor("getinfo", new getinfo());
            setCommandExecutor("refractor", new info());
            setCommandExecutor("title", new title());
            // setCommandExecutor("give", new give());
            setCommandExecutor("ban", new Ban());
            setCommandExecutor("unban", new UnBan());
            setCommandExecutor("unmute", new Unmute());
            setCommandExecutor("mute", new Mute());

            logger.info("Successfully Loaded Commands");
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
    }
    private void setCommandExecutor(String commandName, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(commandName);
        if (command != null) {
            command.setExecutor(executor);
        } else {
            logger.warn("Failed to set executor for command: " + commandName);
        }
    }

}
