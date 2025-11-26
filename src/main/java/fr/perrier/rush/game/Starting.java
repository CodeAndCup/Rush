package fr.perrier.rush.game;

import fr.perrier.rush.*;
import fr.perrier.rush.listener.game.Chemin;
import fr.perrier.rush.listener.game.Kills;
import fr.perrier.rush.menu.*;
import fr.perrier.rush.utils.*;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;

public class Starting extends BukkitRunnable {

    @Getter
    private static Integer time = 30;

    private int rose = 0;
    private int bleu = 0;

    @Override
    public void run() {
        time--;
        if(time == 0) {
            setupGame();
            this.cancel();
        } else if (time == 1) {
            Bukkit.broadcastMessage("§8» §aDémarrage dans 1 seconde !");
        } else if (time == 2) {
            Bukkit.broadcastMessage("§8» §eDémarrage dans 2 secondes...");
        } else if (time == 3) {
            Bukkit.broadcastMessage("§8» §6Démarrage dans 3 secondes..");
        } else if (time == 5) {
            Bukkit.broadcastMessage("§8» §cDémarrage dans 5 secondes. !");
        } else if (time == 10) {
            Bukkit.broadcastMessage("§8» §4Démarrage dans 10 secondes");
        }

        if(Bukkit.getOnlinePlayers().size() < Main.getInstance().getConfig().getInt("game.max-players-per-team")*2) {
            cancel();
            Bukkit.broadcastMessage("§cDémarrage annulé.");

            Main.getInstance().setStatus(GameStatus.WAITING);

            new Waiting().runTaskTimer(Main.getInstance(),20L,20L);
        }

    }

    public void setupGame() {

        ArrayList<Player> waiter = new ArrayList<>();

        Teams.players.entrySet().forEach(playerTeamsEntry -> {
            if(playerTeamsEntry.getValue().equals(Teams.WAITING)) {
                waiter.add(playerTeamsEntry.getKey());
            }
            if(playerTeamsEntry.getValue().equals(Teams.ROSE)) {
                rose++;
            }
            if(playerTeamsEntry.getValue().equals(Teams.BLEU)) {
                bleu++;
            }
        });

        for (int i=0;rose-Main.getInstance().getConfig().getInt("game.max-players-per-team")+i<0;i++) {
            Teams.setPlayerTeam(waiter.get(0),Teams.ROSE);
            waiter.remove(0);
        }
        for (int i=0;bleu-Main.getInstance().getConfig().getInt("game.max-players-per-team")+i<0;i++) {
            Teams.setPlayerTeam(waiter.get(0),Teams.BLEU);
            waiter.remove(0);
        }

        Main.getInstance().setStatus(GameStatus.PLAYING);

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().clear();
        });
        
        Bukkit.getOnlinePlayers().forEach(player -> {
            Stuff.giveTo(player);
            Teams teams = Teams.players.get(player);
            if(teams.equals(Teams.BLEU)) {
                player.teleport(BedLocation.spawnBlue());
            } else if (teams.equals(Teams.ROSE)) {
                player.teleport(BedLocation.spawnPink());
            }
            Kills.kills.put(player,0);
            Kills.morts.put(player,0);

            Chemin.cheminNb.put(player,1);

            new BDD().setKillEffectTo(player,Preferences.killEffect.get(player));
        });

        new Run().runTaskTimer(Main.getInstance(),20L,20L);
    }
}
