package fr.perrier.rush.commands;

import fr.perrier.rush.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Rush implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String txt, String[] args) {
        if(cmd.getName().equalsIgnoreCase("rush")) {
            sender.sendMessage(
                    "§8§m--------------------------------------------------\n"
                    + "§f                                             §6§lRush\n"
                    + "§d \n"
                    + "      §6Plugin Développé par §fPerrierBottle\n"
                    + "      §6Version: §f"+ Main.getInstance().getDescription().getVersion()+"-SNAPSHOT\n"
                    + "§c \n"
                    + "      §3§lDiscord §b→ §fhttps://discord.cupcode.fr\n"
                    + "§8§m--------------------------------------------------\n"
            );
        }
        return false;
    }
}
