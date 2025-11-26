package fr.perrier.rush.game;

import fr.perrier.rush.*;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.block.*;
import org.bukkit.craftbukkit.v1_8_R3.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

import java.lang.reflect.*;
import java.util.*;

public class BedEvents implements Listener {

    private HashMap<String,Player> playerTnt = new HashMap<>();


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getBlockPlaced().getType().equals(Material.TNT)) {
            playerTnt.put(""+e.getBlockPlaced().getLocation().getBlockX()+e.getBlockPlaced().getLocation().getBlockZ(),e.getPlayer());
        }
    }

    @EventHandler
    public void onBlockDestoy(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.TNT)) {
            if(playerTnt.containsKey(e.getBlock().getLocation())) {
                playerTnt.remove(e.getBlock().getLocation());
            }
        }
    }

    @EventHandler
    public void onTnT(ExplosionPrimeEvent e) {
        ArrayList<Block> blocks = getBlocks(e.getEntity().getLocation(),e.getRadius());
        blocks.forEach(block -> {
            if (block.getType().equals(Material.BED_BLOCK)) {
                BedLocation.getDestroyedBed(block, playerTnt.get("" + e.getEntity().getLocation().getBlockX() + e.getEntity().getLocation().getBlockZ()));
            } else if (block.getType().equals(Material.SANDSTONE)) {
                block.breakNaturally();
            }
            if (block.getType().equals(Material.SEA_LANTERN) || block.getType().equals(Material.STAINED_GLASS) || block.getType().equals(Material.BANNER) || block.getType().equals(Material.TRAPPED_CHEST) || block.getType().equals(Material.WOOL)) {
            }

        });

    }

    public ArrayList<Block> getBlocks(Location start, float radius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        try {
            if(e.getClickedBlock().toString().toLowerCase().contains("bed")) {
                e.setCancelled(true);
            }
            if(e.getItem().getType().equals(Material.FLINT_AND_STEEL)) {
                if(!e.getClickedBlock().toString().toLowerCase().contains("tnt")) {
                    e.setCancelled(true);
                }
            }
        }catch (Exception e1) {
            return;
        }
    }
}
