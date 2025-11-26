package fr.perrier.rush.game.npc.type;

import fr.perrier.rush.Main;
import fr.perrier.rush.api.menu.Button;
import fr.perrier.rush.api.menu.GlassMenu;
import fr.perrier.rush.api.menu.buttons.DisplayButton;
import fr.perrier.rush.utils.ItemBuilder;
import fr.perrier.rush.utils.Teams;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Speed extends GlassMenu implements Listener {

    @EventHandler
    public void onPNJ(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            if (villager.getCustomName().equalsIgnoreCase("Speed PNJ")) {
                e.setCancelled(true);
                openMenu(p);
            }
        }
    }

    @Override
    public int getGlassColor() {
        return 0;
    }

    @Override
    public Map<Integer, Button> getAllButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(10, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack itemStack = new ItemBuilder(Material.GOLDEN_APPLE).setName("&6Le Tavernier").toItemStack();
                return itemStack;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new Food().Menu(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return false;
            }
        });
        buttons.put(12, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack itemStack = new ItemBuilder(Material.DIAMOND_SWORD).setName("&dLe Terroriste").toItemStack();
                return itemStack;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new Weapons().Menu(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return false;
            }
        });
        buttons.put(14, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack itemStack = new ItemBuilder(Material.SANDSTONE).setName("&bLe Batisseur").toItemStack();
                return itemStack;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new Block().Menu(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return false;
            }
        });
        buttons.put(16, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                Teams teams = Teams.players.get(p0);
                ItemStack itemStack = null;
                if(teams.equals(Teams.ROSE)) {
                    itemStack = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("&eL'Armurier").setLeatherArmorColor(Color.FUCHSIA).toItemStack();
                } else if (teams.equals(Teams.BLEU)) {
                    itemStack = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("&eL'Armurier").setLeatherArmorColor(Color.AQUA).toItemStack();
                }
                return itemStack;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                player.closeInventory();
                new Armors().Menu(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return false;
            }
        });

        buttons.put(21,new DisplayButton(new ItemBuilder(Material.AIR).toItemStack()));

        return buttons;
    }

    @Override
    public String getTitle(Player paramPlayer) {
        return "&c                 &fSpeed PNJ";
    }

    public void spawn() {
        ArrayList<Location> loc = new ArrayList<>();
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),989.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),995.5,64.5,928.5));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1053.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,64.5,928.5));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1059.5,64.5,928.5));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,989.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,991.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,993.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,995.5,90,0));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,1053.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,1055.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,1057.5,90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1120.5,64.5,1059.5,90,0));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1059.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1053.5,64.5,1120.5,180,0));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),995.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,64.5,1120.5,180,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),989.5,64.5,1120.5,180,0));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,1059.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,1057.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,1055.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,1053.5,-90,0));

        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,995.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,993.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,991.5,-90,0));
        loc.add(new Location(Main.getInstance().getServer().getWorld("Rush"),928.5,64.5,989.5,-90,0));


        loc.forEach(location -> {
            Entity entity = Main.getInstance().getServer().getWorld("Rush").spawnEntity(location, EntityType.VILLAGER);
            entity.setCustomName("Speed PNJ");
            entity.setCustomNameVisible(false);
            ((Villager)entity).setProfession(Villager.Profession.LIBRARIAN);
            ((Villager)entity).setBaby();
            ((Villager) entity).setAgeLock(true);
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
