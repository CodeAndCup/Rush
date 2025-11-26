package fr.perrier.rush.game;

import fr.perrier.rush.*;
import fr.perrier.rush.listener.game.Kills;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Win {

    int players_rose;
    int players_bleu;

    public void attemp() {

        players_rose = 0;
        players_bleu = 0;

        if(Main.getInstance().getStatus().equals(GameStatus.FINISH)) return;

        if(BedLocation.isBreak(BedLocation.PINK_BED)) {
            Teams.players.forEach((key, value) -> {
                if (value.equals(Teams.ROSE)) {
                    players_rose++;
                }
            });

            if(players_rose==0) {
                Main.getInstance().setStatus(GameStatus.FINISH);
                Bukkit.broadcastMessage("§fL'équipe §5Violette§f a été complètement détruite !");
                Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                Bukkit.broadcastMessage("§2                              §fVictoire des §3Cyans");
                Bukkit.broadcastMessage(" ");
                Teams.players.forEach((key, value) -> {
                    int point = 0;
                    if (value.equals(Teams.BLEU) || value.equals(Teams.BLEU_MORT)) {
                        point = (Run.getTime() + Kills.kills.get(key) - Kills.morts.get(key)) / 2 + 150;
                    } else if (value.equals(Teams.ROSE) || value.equals(Teams.ROSE_MORT)) {
                        point = (Run.getTime() + Kills.kills.get(key) - Kills.morts.get(key)) / 2;
                    }
                    key.sendMessage("§d                       §fTu as gagné §b" + point + " §bPoints §f!");
                    key.sendMessage("§5 ");
                    key.sendMessage("§f                            Kills §6" + Kills.kills.get(key) + " §f/ Morts §6" + Kills.morts.get(key));
                    new BDD().addPointsTo(key, (long) point);
                });
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("                        §fTemps de jeu §6" + Run.getTime()/60 + "§fm§6" + Run.getTime()%60 +"§fs");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

                    for (Player allp : Bukkit.getOnlinePlayers()) {
                        allp.chat("/hub");
                    }
                }, 9 * 20L);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), Bukkit::shutdown, 10 * 20L);
            }
        }
        if(BedLocation.isBreak(BedLocation.BLUE_BED)) {
            Teams.players.forEach((key, value) -> {
                if (value.equals(Teams.BLEU)) {
                    players_bleu++;
                }
            });
            if (players_bleu == 0) {
                Main.getInstance().setStatus(GameStatus.FINISH);
                Bukkit.broadcastMessage("§fL'équipe §3Cyan§f a été complètement détruite !");
                Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                Bukkit.broadcastMessage("§2                          §fVictoire des §5Violets");
                Bukkit.broadcastMessage(" ");
                Teams.players.forEach((key, value) -> {
                    int point = 0;
                    if (value.equals(Teams.ROSE) || value.equals(Teams.ROSE_MORT)) {
                        point = (Run.getTime() + Kills.kills.get(key) - Kills.morts.get(key)) / 2 + 150;
                    } else if (value.equals(Teams.BLEU) || value.equals(Teams.BLEU_MORT)) {
                        point = (Run.getTime() + Kills.kills.get(key) - Kills.morts.get(key)) / 2;
                    }
                    key.sendMessage("§d                       §fTu as gagné §b" + point + " §bPoints §f!");
                    key.sendMessage("§5 ");
                    key.sendMessage("§f                            Kills §6" + Kills.kills.get(key) + " §f/ Morts §6" + Kills.morts.get(key));
                    new BDD().addPointsTo(key, (long) point);
                });
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("                        §fTemps de jeu §6" + Run.getTime()/60 + "§fm§6" + Run.getTime()%60 +"§fs");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

                    for (Player allp : Bukkit.getOnlinePlayers()) {
                        allp.chat("/hub");
                    }
                }, 9 * 20L);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), Bukkit::shutdown, 10 * 20L);
            }


        }
    }
}
