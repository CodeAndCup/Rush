package fr.perrier.rush.listener.global;

import fr.perrier.rush.*;
import fr.perrier.rush.game.*;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class Cancel implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
        if (Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.STARTING) || Main.getInstance().getStatus().equals(GameStatus.FINISH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(Teams.players.get(e.getEntity()) == Teams.players.get(e.getDamager())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;
        try {
            if(Main.getInstance().getStatus().equals(GameStatus.WAITING) | Main.getInstance().getStatus().equals(GameStatus.STARTING)) {
                e.setCancelled(true);
            }
        }catch (Exception e1) {
            return;
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onModSpawn(EntitySpawnEvent e) {
        if(e.getEntity() instanceof Villager || e.getEntity() instanceof Item || e.getEntity() instanceof ItemStack) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.FINISH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.FINISH)) {
            event.setCancelled(true);
        }else{
            if(event.getBlock().getType().equals(Material.SANDSTONE) || event.getBlock().getType().equals(Material.TNT) || event.getBlock().getType().equals(Material.ENDER_STONE)) {
                return;
            }else{
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent e) {
        e.getRecipe().getResult().setType(Material.AIR);
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.STARTING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        ArrayList<String> blockedcmd = new ArrayList<String>();
        blockedcmd.add("plugins");
        blockedcmd.add("pl");
        blockedcmd.add("icanhasbukkit");
        blockedcmd.add("?");
        blockedcmd.add("version");
        blockedcmd.add("ver");
        blockedcmd.add("bukkit");
        blockedcmd.add("bukkit:plugins");
        blockedcmd.add("bukkit:pl");
        blockedcmd.add("bukkit:?");
        blockedcmd.add("about");
        blockedcmd.add("bukkit:about");
        blockedcmd.add("bukkit:version");
        blockedcmd.add("bukkit:ver");
        blockedcmd.add("bukkit:help");
        blockedcmd.add("bukkit:rl");
        blockedcmd.add("bukkit:reload");
        blockedcmd.add("reload");
        blockedcmd.add("rl");
        blockedcmd.add("help");
        blockedcmd.add("nte");
        blockedcmd.add("nametagedit");
        blockedcmd.add("skin");
        blockedcmd.add("skins");
        blockedcmd.add("skinsrestorer");
        blockedcmd.add("me");
        blockedcmd.add("minecraft:me");
        blockedcmd.add("we");
        blockedcmd.add("mv");

        Player player = event.getPlayer();
        String command = event.getMessage();

        if(!player.hasPermission("rush.bypasspl")){
            for(int i = 0; i < blockedcmd.size(); i++){
                String playercommand = (String) blockedcmd.get(i);
                if(command.toUpperCase().contains("/" + playercommand.toUpperCase())){
                    event.setCancelled(true);
                    player.performCommand("rush");
                }
            }
        }
    }
}
