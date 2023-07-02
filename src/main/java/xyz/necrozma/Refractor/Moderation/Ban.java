package xyz.necrozma.Refractor.Moderation;


import io.sentry.Sentry;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.Refractor.Events.OnQuit;
import xyz.necrozma.Refractor.Main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static xyz.necrozma.Refractor.Main.database;

public class Ban implements CommandExecutor {
    Logger logger = LoggerFactory.getLogger(Ban.class);
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("Ban")) {
            String targetPlayer = args[0];
            player.sendMessage(ChatColor.GREEN + "console");

            Connection connection = database.getConnection();

            if (connection != null) {
                try {
                    DatabaseMetaData metaData = connection.getMetaData();
                    ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

                    logger.info("Tables in the database:");
                    while (resultSet.next()) {
                        String tableName = resultSet.getString("TABLE_NAME");
                        logger.info(tableName);
                    }

                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Failed to retrieve table information. Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                logger.error("Failed to establish a database connection.");
            }

        }


        return true;
    }


}

