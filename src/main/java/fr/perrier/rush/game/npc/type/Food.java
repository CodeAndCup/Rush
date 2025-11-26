package fr.perrier.rush.game.npc.type;

import fr.perrier.rush.Main;
import fr.perrier.rush.game.npc.ForceVillagerTrade;
import fr.perrier.rush.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.*;

import java.util.ArrayList;

public class Food implements Listener {

    @EventHandler
    public void onPNJ(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            if(villager.getCustomName().equalsIgnoreCase("Le Tavernier")) {
                e.setCancelled(true);
                Menu(p);
            }
        }
    }

    public void spawn() {
        ArrayList<Location> loc = new ArrayList<>();
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),985.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1049.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,985.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1049.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1063.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),999.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1063.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,999.5,-90,0));


        loc.forEach(location -> {
            Entity entity = Main.getInstance().getServer().getWorld("Rush").spawnEntity(location, EntityType.VILLAGER);
            entity.setCustomName("Le Tavernier");
            entity.setCustomNameVisible(true);
            ((CraftVillager)entity).getHandle().setProfession(5);
            disableAI(entity);
        });
    }

    private void disableAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEnt =((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEnt.getNBTTag();

        if(tag == null) {
            tag = new NBTTagCompound();
        }

        nmsEnt.c(tag);
        tag.setInt("NoAI", 1);
        nmsEnt.f(tag);
    }

    public void Menu(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ForceVillagerTrade forceVillagerTrade = new ForceVillagerTrade("Tavernier");
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").toItemStack(),
                        new ItemBuilder(Material.GOLDEN_APPLE).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.DIAMOND).setName("&b&lDIAMOND").setAmount(3).toItemStack(),
                        new ItemBuilder(Material.POTION).setName("&f&lVODKA").toItemStack()
                );
                forceVillagerTrade.openTrade(player);
            }
        }.runTaskLater(Main.getInstance(),1L);
    }
}
