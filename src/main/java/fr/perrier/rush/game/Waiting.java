package fr.perrier.rush.game;

import fr.perrier.rush.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

public class Waiting extends BukkitRunnable {
    @Override
    public void run() {
        if(Bukkit.getOnlinePlayers().size() == Main.getInstance().getConfig().getInt("game.max-players-per-team")*2) {
            Main.getInstance().setStatus(GameStatus.STARTING);
            new Starting().runTaskTimer(Main.getInstance(),20L,20L);
            cancel();
        }
    }
}
