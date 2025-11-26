package fr.perrier.rush.game.npc.type;

import fr.perrier.rush.*;
import fr.perrier.rush.game.npc.*;
import fr.perrier.rush.utils.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.enchantments.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class Armors implements Listener {

    @EventHandler
    public void onPNJ(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            if(villager.getCustomName().equalsIgnoreCase("L'Armurier")) {
                e.setCancelled(true);
                Menu(p);
            }
        }
    }

    public void Menu(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ForceVillagerTrade forceVillagerTrade = new ForceVillagerTrade("Armurier");
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.GOLD_INGOT).setName("&e&lGOLD").toItemStack(),
                        new ItemBuilder(Material.POTION).setDurability(16453).setAmount(2).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.CLAY_BRICK).setName("&6&lCOPPER").setAmount(2).toItemStack(),
                        new ItemBuilder(Material.LEATHER_CHESTPLATE).setAmount(1).setName("&bArmure I").toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.CLAY_BRICK).setName("&6&lCOPPER").setAmount(15).toItemStack(),
                        new ItemBuilder(Material.LEATHER_CHESTPLATE).setAmount(1).setName("&bArmure II").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.GOLD_INGOT).setName("&e&lGOLD").setAmount(1).toItemStack(),
                        new ItemBuilder(Material.LEATHER_CHESTPLATE).setAmount(1).setName("&bArmure III").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.DIAMOND).setName("&b&lDIAMOND").setAmount(2).toItemStack(),
                        new ItemBuilder(Material.LEATHER_CHESTPLATE).setAmount(1).setName("&bArmure III").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).toItemStack()
                );
                forceVillagerTrade.addTrade(
                        new ItemBuilder(Material.GOLD_INGOT).setName("&e&lGOLD").toItemStack(),
                        new ItemBuilder(Material.COMPASS).setName("&aTraqueur").toItemStack()
                );
                forceVillagerTrade.openTrade(player);
            }
        }.runTaskLater(Main.getInstance(),1L);
    }
    public void spawn() {
        ArrayList<Location> loc = new ArrayList<>();
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),999.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1063.5,65,929.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,999.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1063.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1049.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),985.5,65,1119.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1049.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,985.5,-90,0));

        loc.forEach(location -> {
            Entity entity = Main.getInstance().getServer().getWorld("Rush").spawnEntity(location, EntityType.VILLAGER);
            entity.setCustomName("L'Armurier");
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

}
