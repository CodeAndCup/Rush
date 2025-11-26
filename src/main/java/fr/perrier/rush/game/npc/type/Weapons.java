package fr.perrier.rush.game.npc.type;

import fr.perrier.rush.Main;
import fr.perrier.rush.game.npc.ForceVillagerTrade;
import fr.perrier.rush.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.*;

import java.util.ArrayList;

public class Weapons implements Listener {

    @EventHandler
    public void onPNJ(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            if(villager.getCustomName().equalsIgnoreCase("Le Terroriste")) {
                e.setCancelled(true);
                Menu(p);
            }
        }
    }

    public void spawn() {
        ArrayList<Location> loc = new ArrayList<>();
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),987.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1051.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,987.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1051.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1061.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),997.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1061.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,997.5,-90,0));

        loc.forEach(location -> {
            Entity entity = Main.getInstance().getServer().getWorld("Rush").spawnEntity(location, EntityType.VILLAGER);
            entity.setCustomName("Le Terroriste");
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
                ForceVillagerTrade forceVillagerTrade = new ForceVillagerTrade("Terroriste");
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").toItemStack(),
                        new ItemBuilder(Material.IRON_SWORD).setName("&bÉpée I").addEnchant(Enchantment.DAMAGE_ALL,1).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").setAmount(3).toItemStack(),
                        new ItemBuilder(Material.IRON_SWORD).setName("&bÉpée II").addEnchant(Enchantment.DAMAGE_ALL,2).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").setAmount(5).toItemStack(),
                        new ItemBuilder(Material.IRON_SWORD).setName("&bÉpée III").addEnchant(Enchantment.DAMAGE_ALL,2).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").setAmount(5).toItemStack(),
                        new ItemBuilder(Material.DIAMOND).setName("&b&lDIAMOND").toItemStack(),
                        new ItemBuilder(Material.DIAMOND_SWORD).setName("&bÉpée IV").addEnchant(Enchantment.DAMAGE_ALL,2).setDurability(1311).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.IRON_INGOT).setName("&7&lIRON").setAmount(4).toItemStack(),
                        new ItemBuilder(Material.TNT).setName("&4&lBOOM").toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.DIAMOND).setName("&b&lDIAMOND").toItemStack(),
                        new ItemBuilder(Material.TNT).setName("&4&lBOOM").toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.GOLD_INGOT).setName("&e&lGOLD").toItemStack(),
                        new ItemBuilder(Material.FLINT_AND_STEEL).setDurability(44).toItemStack()
                );
                forceVillagerTrade.openTrade(player);
            }
        }.runTaskLater(Main.getInstance(),1L);
    }
}
