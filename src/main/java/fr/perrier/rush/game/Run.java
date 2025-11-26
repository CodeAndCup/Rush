package fr.perrier.rush.game;

import fr.perrier.rush.*;
import fr.perrier.rush.utils.*;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Item;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.*;

import java.util.*;

public class Run extends BukkitRunnable implements Listener {

    @Getter
    private static int time = 0;

    @Override
    public void run() {
        spawn_ingot(time);
        time++;

        Bukkit.getOnlinePlayers().forEach(player -> {
            Location center = new Location(Main.getInstance().getServer().getWorld("Rush"),1024,64,1024 );
            Location player_loc = player.getLocation();
            if(player.getGameMode().equals(GameMode.SURVIVAL)) {
                if(center.distance(player_loc) <= 75) {
                    new BorderScreen().removeBorder(player);
                    player.setHealth(0.0);
                    new BukkitRunnable() {
                        public void run() {
                            ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                        }
                    }.runTaskLater(Main.getInstance(),1L);
                }
                if(center.distance(player_loc) <= 80) {
                    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§4Bordure à "+ ((int)(center.distance(player_loc)-74)) + " block(s)" + "\"}");
                    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
                    new BorderScreen().setBorder(player,100);
                }
                if(center.distance(player_loc) >= 105) {
                    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§4Bordure à "+ ((int)(115-center.distance(player_loc))) + " block(s)" + "\"}");
                    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
                    new BorderScreen().setBorder(player,100);
                }
                if(center.distance(player_loc) >= 115) {
                    new BorderScreen().removeBorder(player);
                    player.setHealth(0.0);
                    new BukkitRunnable() {
                        public void run() {
                            ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                        }
                    }.runTaskLater(Main.getInstance(),1L);
                }
                new BorderScreen().removeBorder(player);
                if(!BedLocation.isBreak(BedLocation.BLUE_BED) && !BedLocation.isBreak(BedLocation.PINK_BED)) {
                    Location nobedrush_loc = new Location(Main.getInstance().getServer().getWorld("Rush"),963,64,963 );
                    if(nobedrush_loc.distance(player_loc) <= 18) {
                        new BorderScreen().removeBorder(player);
                        player.setHealth(0.0);
                        new BukkitRunnable() {
                            public void run() {
                                ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                            }
                        }.runTaskLater(Main.getInstance(),1L);
                    }
                    if(nobedrush_loc.distance(player_loc) <= 23) {
                        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§4Bordure à "+ ((int)(nobedrush_loc.distance(player_loc)-17)) + " block(s)" + "\"}");
                        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
                        new BorderScreen().setBorder(player,100);
                    }
                }
            }else{
                if(center.distance(player_loc) >= 135) {
                    player.teleport(new Location(Main.getInstance().getServer().getWorld("Rush"),1024,64,1024 ));
                    player.sendMessage("§cNonono, ne va pas trop loin, tu dois rester sous la surveillance de tes parents.");
                }
            }
        });
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if(item.getType().equals(Material.BED)) {
            e.setCancelled(true);
        }
        if(item.getType().equals(Material.TNT)) {
            item.getItemMeta().setDisplayName("§4§lBOOM");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.STARTING)) return;
        Block block = e.getBlockPlaced();
        Player p = e.getPlayer();
        if(block.getLocation().getBlockY() >= 75 && (Teams.bed.get(Teams.ROSE) && Teams.bed.get(Teams.BLEU))) {
            IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§c§lVous ne pouvez pas construire plus haut." + "\"}");
            PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
            e.setCancelled(true);
        }else if(block.getLocation().getBlockY() >= 82){
            IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§c§lVous ne pouvez pas construire plus haut." + "\"}");
            PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
            e.setCancelled(true);
        }
        if(block.getLocation().getBlockY() <= 62) {
            IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§c§lVous ne pouvez pas construire plus bas." + "\"}");
            PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
            e.setCancelled(true);
        }
    }

    public static void spawn_ingot(int time) {

        if(Main.getInstance().getStatus().equals(GameStatus.FINISH)) return;

        ArrayList<Location> gen = new ArrayList<>();
        if(Main.getInstance().getConfig().getInt("game.max-players-per-team") == 2) {
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,65,929.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,65,929.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,991.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,993.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1055.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1057.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,65,1119.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,65,1119.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1055.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1057.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,991.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,993.5));

        }else{
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),989.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),995.5,65,929.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1053.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,65,929.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1059.5,65,929.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,989.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,991.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,993.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,995.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1053.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1055.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1057.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1119.5,65,1059.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1059.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1057.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1055.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),1053.5,65,1119.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),995.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),993.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),991.5,65,1119.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),989.5,65,1119.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1053.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1055.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1057.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,1059.5));

            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,989.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,991.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,993.5));
            gen.add(new Location(Main.getInstance().getServer().getWorld("Rush"),929.5,65,995.5));
        }




        ItemStack copper = new ItemBuilder(Material.CLAY_BRICK).setName("§6§lCOPPER").toItemStack();
        ItemStack iron = new ItemBuilder(Material.IRON_INGOT).setName("§7§lIRON").toItemStack();
        ItemStack gold = new ItemBuilder(Material.GOLD_INGOT).setName("§e§lGOLD").toItemStack();
        ItemStack diamond = new ItemBuilder(Material.DIAMOND).setName("§b§lDIAMOND").toItemStack();

        gen.forEach(location -> {
            Item copperitem = Main.getInstance().getServer().getWorld("Rush").dropItem(location,copper);
            copperitem.setVelocity(copperitem.getVelocity().zero());
            if(time==0) return;
            if(time%15==0) {
                Item ironitem = Main.getInstance().getServer().getWorld("Rush").dropItem(location,iron);
                ironitem.setVelocity(ironitem.getVelocity().zero());
            }
            if (time%60==0) {
                Item golditem = Main.getInstance().getServer().getWorld("Rush").dropItem(location,gold);
                golditem.setVelocity(golditem.getVelocity().zero());
            }
            if (time%(60*5)==0) {
                Item diamonditem = Main.getInstance().getServer().getWorld("Rush").dropItem(location,diamond);
                diamonditem.setVelocity(diamonditem.getVelocity().zero());
            }
        });


        /*
        Bronze : 1 seconde.
        Argent : 15 secondes.
        Or : 1 minute.
        Diamant : 5 minutes.
         */
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        ItemStack clickeditem = e.getCurrentItem();
        if(e.isShiftClick()){
            if(e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER) {
                Inventory vTradeInv = e.getView().getTopInventory();
                if(vTradeInv != null && vTradeInv.getType() == InventoryType.MERCHANT) {
                    e.setCancelled(true);
                    if(clickeditem != null && clickeditem.getType() != Material.AIR) {
                        int emptyslot = vTradeInv.firstEmpty();
                        if(emptyslot!= -1) {
                            vTradeInv.setItem(emptyslot,clickeditem);
                            e.setCurrentItem(new ItemStack(Material.AIR));
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }
}
