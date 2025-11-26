package fr.perrier.rush.listener.global;

import fr.perrier.rush.Main;
import fr.perrier.rush.game.GameStatus;
import fr.perrier.rush.utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class Tchat implements Listener {

    @EventHandler
    public void onTchat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        e.setCancelled(true);
        if(Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.STARTING)) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.spigot().sendMessage(new BDD().getPointsOf(p, p.getName() + " §f: " + message));
            });
        } else if (Main.getInstance().getStatus().equals(GameStatus.PLAYING)) {
            if(message.startsWith("@") || message.startsWith("!")) {
                Bukkit.broadcastMessage("§6[§eGlobal§6] " + Teams.players.get(p).getColor().replace("&","§") + Teams.players.get(p).getName() + " " + p.getName() + " §f: §b@§f" + message.replace("@",""));
            }else{
                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if(playerTeamsEntry.getValue() == Teams.players.get(p)) {
                        playerTeamsEntry.getKey().sendMessage("§6[§eÉquipe§6] " + Teams.players.get(p).getColor().replace("&","§") + Teams.players.get(p).getName() + " " + p.getName() + " §f: §7" + message);

                    }
                });
            }
        }else{
            Bukkit.broadcastMessage(p.getName() + " §f: " + message);
        }
    }
}
