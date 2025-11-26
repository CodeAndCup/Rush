package fr.perrier.rush.listener.game;

import fr.perrier.rush.Main;
import fr.perrier.rush.game.GameStatus;
import fr.perrier.rush.utils.Teams;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class Chemin implements Listener {

    public static HashMap<Player,Integer> cheminNb = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(!Main.getInstance().getStatus().equals(GameStatus.PLAYING)) return;
        isInRegion(e.getPlayer());
    }

    private void isInRegion(Player player) {
        Location loc_base_rose_1 = new Location(Main.getInstance().getServer().getWorld("Rush"),934,64,992);
        Location loc_base_rose_2 = new Location(Main.getInstance().getServer().getWorld("Rush"),934,64,1056);
        Location loc_base_rose_3 = new Location(Main.getInstance().getServer().getWorld("Rush"),992,64,1114);
        Location loc_base_rose_4 = new Location(Main.getInstance().getServer().getWorld("Rush"),1056,64,1114);

        Location loc_base_bleu_4 = new Location(Main.getInstance().getServer().getWorld("Rush"),1114,64,1056);
        Location loc_base_bleu_3 = new Location(Main.getInstance().getServer().getWorld("Rush"),1114,64,992);
        Location loc_base_bleu_2 = new Location(Main.getInstance().getServer().getWorld("Rush"),1056,64,934);
        Location loc_base_bleu_1 = new Location(Main.getInstance().getServer().getWorld("Rush"),992,64,934);

        if(loc_base_rose_1.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,8);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,1);
            }
        }
        if(loc_base_rose_2.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,7);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,2);
            }
        }
        if(loc_base_rose_3.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,6);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,3);
            }
        }
        if(loc_base_rose_4.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,5);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,4);
            }
        }

        if(loc_base_bleu_4.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,4);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,5);
            }
        }
        if(loc_base_bleu_3.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,3);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,6);
            }
        }
        if(loc_base_bleu_2.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,2);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,7);
            }
        }
        if(loc_base_bleu_1.distance(player.getLocation()) < 20) {
            if(Teams.players.get(player) == Teams.BLEU) {
                cheminNb.replace(player,1);
            } else if (Teams.players.get(player) == Teams.ROSE) {
                cheminNb.replace(player,8);
            }
        }
    }

    public String getProgressBar(Player player) {
        int i = cheminNb.get(player);
        switch (i) {
            case 1:
                return "§a①§7②③④⑤⑥⑦⑧";
            case 2:
                return "§a①②§7③④⑤⑥⑦⑧";
            case 3:
                return "§a①②③§7④⑤⑥⑦⑧";
            case 4:
                return "§a①②③④§7⑤⑥⑦⑧";
            case 5:
                return "§a①②③④⑤§7⑥⑦⑧";
            case 6:
                return "§a①②③④⑤⑥§7⑦⑧";
            case 7:
                return "§a①②③④⑤⑥⑦§7⑧";
            case 8:
                return "§a①②③④⑤⑥⑦⑧";
            default:
                break;
        }
        return "§cError";
    }
}
