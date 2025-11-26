package fr.perrier.rush.utils;

import fr.perrier.rush.*;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.material.*;

import java.util.*;

@Getter
public enum BedLocation {

    BLUE_BED(992,64,935),
    BLUE_BED2(992,64,936),
    PINK_BED(936,64,992),
    PINK_BED2(935,64,992),
    ADDONS_BED1_1(1056,64,936),
    ADDONS_BED1_2(1056,64,935),
    ADDONS_BED2_1(1112,64,992),
    ADDONS_BED2_2(1113,64,992),
    ADDONS_BED3_1(1112,64,1056),
    ADDONS_BED3_2(1113,64,1056),
    ADDONS_BED4_1(1056,64,1112),
    ADDONS_BED4_2(1056,64,1113),
    ADDONS_BED5_1(992,64,1112),
    ADDONS_BED5_2(992,64,1113),
    ADDONS_BED6_1(936,64,1056),
    ADDONS_BED6_2(935,64,1056),

    ;

    public static List<String> beds = new ArrayList<>();
    static {
        beds.add("BLUE_BED");
        beds.add("BLUE_BED2");
        beds.add("PINK_BED");
        beds.add("PINK_BED2");
        beds.add("ADDONS_BED1_1");
        beds.add("ADDONS_BED1_2");
        beds.add("ADDONS_BED2_1");
        beds.add("ADDONS_BED2_2");
        beds.add("ADDONS_BED3_1");
        beds.add("ADDONS_BED3_2");
        beds.add("ADDONS_BED4_1");
        beds.add("ADDONS_BED4_2");
        beds.add("ADDONS_BED5_1");
        beds.add("ADDONS_BED5_2");
        beds.add("ADDONS_BED6_1");
        beds.add("ADDONS_BED6_2");
    }

    private final int x;
    private final int y;
    private final int z;

    BedLocation(int x,int y,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    static ArrayList<Player> message = new ArrayList<>();

    public static void getDestroyedBed(Block block, Player player) {
        Teams teams = Teams.players.get(player);
        if(isBlueBed(block.getX(), block.getY(), block.getZ())) {
            if(teams.equals(Teams.BLEU) && !message.contains(player)) {
                message.add(player);
                player.sendMessage("§4Vous ne pouvez pas détruire votre lit.");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    message.remove(player);
                },20L);
            } else if (Teams.players.get(player).equals(Teams.ROSE)) {
                block.breakNaturally();
                Bukkit.broadcastMessage("§4\uD83D\uDECF §fLe lit des §3cyans §fa été détruit par §5" + player.getName());
                Teams.bed.replace(Teams.BLEU,false);
            }
        }else if(isPinkBed(block.getX(), block.getY(), block.getZ())) {
            if(teams.equals(Teams.ROSE) && !message.contains(player)) {
                message.add(player);
                player.sendMessage("§4Vous ne pouvez pas détruire votre lit.");
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    message.remove(player);
                },20L);
            } else if (Teams.players.get(player).equals(Teams.BLEU)) {
                block.breakNaturally();
                Bukkit.broadcastMessage("§4\uD83D\uDECF §fLe lit des §5violets §fa été détruit par §3" + player.getName());
                Teams.bed.replace(Teams.ROSE,false);
            }
        }else {
            beds.forEach(bed -> {
                if ((block.getX() == BedLocation.valueOf(bed).getX())
                        && (block.getY() == BedLocation.valueOf(bed).getY())
                        && (block.getZ() == BedLocation.valueOf(bed).getZ())) {
                    block.breakNaturally();
                    if(teams == null) {
                        Bukkit.broadcastMessage("§cUne erreur est survenue §4[BEDL-85-BNS]");
                    }
                    if(teams.equals(Teams.BLEU)) {
                        Teams.addBedDestroyedByBlue();
                    }else
                    if(teams.equals(Teams.ROSE)) {
                        Teams.addBedDestroyedByPink();
                    }else{
                        Bukkit.broadcastMessage("§cUne erreur est survenue §4[BEDL-91-BNS]");
                    }
                }
            });
        }
    }

    public static Location spawnBlue() {
        return new Location(Main.getInstance().getServer().getWorld("Rush"), 992.5,65,934.5);
    }
    public static Location spawnPink() {
        return new Location(Main.getInstance().getServer().getWorld("Rush"), 934.5,65,992.5);
    }

    public static boolean isBreak(BedLocation bedLocation) {
        return !Main.getInstance().getServer().getWorld("Rush").getBlockAt(bedLocation.x, bedLocation.y, bedLocation.z).getType().toString().equalsIgnoreCase("BED_BLOCK");
    }

    public static boolean isBlueBed(int x,int y,int z) {
        return ((x == BedLocation.BLUE_BED.getX()) && (y == BedLocation.BLUE_BED.getY()) && (z == BedLocation.BLUE_BED.getZ())) ||
               ((x == BedLocation.BLUE_BED2.getX()) && (y == BedLocation.BLUE_BED2.getY()) && (z == BedLocation.BLUE_BED2.getZ()));
    }

    public static boolean isPinkBed(int x,int y,int z) {
        return ((x == BedLocation.PINK_BED.getX()) && (y == BedLocation.PINK_BED.getY()) && (z == BedLocation.PINK_BED.getZ())) ||
               ((x == BedLocation.PINK_BED2.getX()) && (y == BedLocation.PINK_BED2.getY()) && (z == BedLocation.PINK_BED2.getZ()));
    }
}
