package fr.perrier.rush.utils;

import com.nametagedit.plugin.*;
import fr.perrier.rush.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public enum Teams {

    ROSE("&5","Violet"),
    BLEU("&3","Cyan"),
    ROSE_MORT("&5&o","Mort"),
    BLEU_MORT("&3&o","Mort"),
    WAITING("&7",""),;

    private String color;
    private String name;
    public static HashMap<Player,Teams> players = new HashMap<>();
    public static HashMap<Teams,Boolean> bed = new HashMap<>();

    private static int bedDestroyedByBlue = 0;
    private static int bedDestroyedByPink = 0;

    Teams(String color,String name) {
        this.color = color;
        this.name = name;
    }

    public static void setupBed() {
        Teams.bed.put(Teams.BLEU,true);
        Teams.bed.put(Teams.ROSE,true);
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public static void setPlayerTeam(Player player, Teams teams) {
        players.put(player,teams);
        NametagEdit.getApi().setPrefix(player, teams.getColor() + teams.getName() + " ");
    }

    public static int getBedDestroyedByBlue() {
        return bedDestroyedByBlue;
    }

    public static void addBedDestroyedByBlue() {
        bedDestroyedByBlue += 1;
        Bukkit.broadcastMessage("§4§lBOOM ! §fLit intermédiaire détruit par les §3Cyans");
        if(bedDestroyedByBlue < 3) {
            Bukkit.broadcastMessage("§f+2§c♥ §fpour l'équipe §3Cyan");
            players.entrySet().forEach(playerTeamsEntry -> {
                if(playerTeamsEntry.getValue().equals(Teams.BLEU)) {
                    playerTeamsEntry.getKey().setMaxHealth(playerTeamsEntry.getKey().getMaxHealth()+4);
                }
            });
        }else{
            Bukkit.broadcastMessage("§fObjets supplémentaires au spawn de l'équipe §3Cyan");
            if(bedDestroyedByBlue == 3) {
                dropItemAtLocation(989.5, 65, 929.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(991.5, 65, 929.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(993.5, 65, 929.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(995.5, 65, 929.5, Material.DIAMOND, "§b§lDIAMOND");
            }else{
                dropItemAtLocation(989.5, 65, 929.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(991.5, 65, 929.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(993.5, 65, 929.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(995.5, 65, 929.5, Material.TNT, "§4§lBOOM");
            }
        }
    }

    public static int getBedDestroyedByPink() {
        return bedDestroyedByPink;
    }

    public static void addBedDestroyedByPink() {
        bedDestroyedByPink += 1;
        Bukkit.broadcastMessage("§4§lBOOM ! §fLit intermédiaire détruit par les §5Violets");
        if(bedDestroyedByPink < 3) {
            Bukkit.broadcastMessage("§f+2§c♥ §fpour l'équipe §5Violette");
            players.entrySet().forEach(playerTeamsEntry -> {
                if(playerTeamsEntry.getValue().equals(Teams.ROSE)) {
                    playerTeamsEntry.getKey().setMaxHealth(playerTeamsEntry.getKey().getMaxHealth()+4);
                }
            });
        }else{
            Bukkit.broadcastMessage("§fObjets supplémentaires au spawn de l'équipe §5Violette");
            if(bedDestroyedByPink == 3) {
                dropItemAtLocation(929.5, 65, 989.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(929.5, 65, 991.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(929.5, 65, 993.5, Material.DIAMOND, "§b§lDIAMOND");
                dropItemAtLocation(929.5, 65, 995.5, Material.DIAMOND, "§b§lDIAMOND");
            }else{
                dropItemAtLocation(929.5, 65, 989.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(929.5, 65, 991.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(929.5, 65, 993.5, Material.TNT, "§4§lBOOM");
                dropItemAtLocation(929.5, 65, 995.5, Material.TNT, "§4§lBOOM");
            }
        }
    }

    private static void dropItemAtLocation(double x, double y, double z, Material material, String name) {
        World world = Main.getInstance().getServer().getWorld("Rush");
        Location location = new Location(world, x, y, z);
        Item item = world.dropItem(location, new ItemBuilder(material).setName(name).toItemStack());
        item.setVelocity(item.getVelocity().zero());
    }
}
