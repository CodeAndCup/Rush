package fr.perrier.rush.scoreboard;



import java.util.UUID;

import fr.perrier.rush.*;
import fr.perrier.rush.game.*;
import fr.perrier.rush.listener.game.Chemin;
import fr.perrier.rush.listener.game.Kills;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {


    private Player player;


    private final UUID uuid;
    private final ObjectiveSign objectiveSign;



    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "DevPlugin");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData(){}

    int players_rose;
    int players_bleu;

    public void setLines(String ip){

        try {

            players_rose = 0;
            players_bleu = 0;


            Teams.players.entrySet().forEach(playerTeamsEntry -> {
                if (playerTeamsEntry.getValue().equals(Teams.ROSE)) {
                    if(Teams.bed.get(Teams.ROSE)) {
                        players_rose++;
                    }else {
                        if (!playerTeamsEntry.getKey().getGameMode().equals(GameMode.SPECTATOR)) {
                            players_rose++;
                        }
                    }
                } else if (playerTeamsEntry.getValue().equals(Teams.BLEU)) {
                    if(Teams.bed.get(Teams.BLEU)) {
                        players_bleu++;
                    }else {
                        if (!playerTeamsEntry.getKey().getGameMode().equals(GameMode.SPECTATOR)) {
                            players_bleu++;
                        }
                    }
                }
            });


            objectiveSign.setDisplayName("§6§lRush");
            objectiveSign.setLine(1, "§8§m------------------");

            if(Main.getInstance().getStatus().equals(GameStatus.WAITING)) {
                objectiveSign.setLine(2, "§f");
                objectiveSign.setLine(3, "§fJoueurs: " + Bukkit.getOnlinePlayers().size() + "/" + Main.getInstance().getConfig().getInt("game.max-players-per-team")*2);
                objectiveSign.setLine(4, "§d");
                objectiveSign.setLine(5, "§cEn attente de joueurs..");
                objectiveSign.setLine(6, "§2");
            } else if (Main.getInstance().getStatus().equals(GameStatus.STARTING)) {
                objectiveSign.setLine(2, "§f");
                objectiveSign.setLine(3, "§fJoueurs: " + Bukkit.getOnlinePlayers().size() + "/" + Main.getInstance().getConfig().getInt("game.max-players-per-team")*2);
                objectiveSign.setLine(4, "§d");
                objectiveSign.setLine(5, "§fDémarrage dans §e" + Starting.getTime() + "§fs");
                objectiveSign.setLine(6, "§2");

            } else if (Main.getInstance().getStatus().equals(GameStatus.PLAYING)) {
                objectiveSign.setLine(2, "§f");
                if(Teams.bed.get(Teams.ROSE) && Teams.bed.get(Teams.BLEU)) {
                    try {
                        objectiveSign.setLine(3, "§fChemin: " + new Chemin().getProgressBar(player));
                    }catch (Exception e1) {
                        objectiveSign.setLine(3, "§fChemin: §cChargement..");
                    }
                }else{
                    objectiveSign.setLine(3, "§fChemin: §6Libre");
                }
                objectiveSign.setLine(4, "§1");
                objectiveSign.setLine(5, "§5Violet§f: " + players_rose + " " + (Teams.bed.get(Teams.ROSE) ? "§a✔" : "§c✘"));
                objectiveSign.setLine(6, "§3Cyan§f: " + players_bleu + " " + (Teams.bed.get(Teams.BLEU) ? "§a✔" : "§c✘"));
                objectiveSign.setLine(7, "§2");
                objectiveSign.setLine(8,"§fKills/Morts: §6" + Kills.kills.get(player) + "§f/§6" + Kills.morts.get(player));
                objectiveSign.setLine(9, "§fChrono: §6" + Run.getTime()/60 + "§fm§6" + Run.getTime()%60 +"§fs");
            }else{
                objectiveSign.setLine(2, "§f");
                objectiveSign.setLine(3, "§fChemin: §6Libre");
                objectiveSign.setLine(4, "§1");
                objectiveSign.setLine(5, "§5Violet§f: " + players_rose + " " + (Teams.bed.get(Teams.ROSE) ? "§a✔" : "§c✘"));
                objectiveSign.setLine(6, "§3Cyan§f: " + players_bleu + " " + (Teams.bed.get(Teams.BLEU) ? "§a✔" : "§c✘"));
                objectiveSign.setLine(7, "§2");
                objectiveSign.setLine(8,"§fKills/Morts: §6" + Kills.kills.get(player) + "§f/§6" + Kills.morts.get(player));
                objectiveSign.setLine(9,"§3");
            }


            objectiveSign.setLine(10, "§f§8§m------------------");
            objectiveSign.setLine(11, ip);

            objectiveSign.updateLines();
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}