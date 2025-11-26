package fr.perrier.rush.menu;

import fr.perrier.rush.*;
import fr.perrier.rush.api.menu.Button;
import fr.perrier.rush.api.menu.GlassMenu;
import fr.perrier.rush.api.menu.buttons.DisplayButton;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class TeamSelector extends GlassMenu implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            ItemMeta it = e.getItem().getItemMeta();
            Player p = e.getPlayer();
            if (it.getDisplayName().contains("§f§lÉquipe")) {
                openMenu(p);
            }
        }catch (Exception e1) {
            return;
        }
    }

    @Override
    public int getGlassColor() {
        return 0;
    }

    @Override
    public Map<Integer, Button> getAllButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();


        buttons.put(12, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack it;

                ArrayList<String> lore = new ArrayList<>();
                lore.add(" ");
                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if (playerTeamsEntry.getValue().equals(Teams.BLEU)) {
                        lore.add("&f- &3" + playerTeamsEntry.getKey().getName());
                    }
                });

                it = new ItemBuilder(Material.WOOL).setDurability(9).setName("&3&lCyan").setLore(lore).toItemStack();
                return it;
            }
            int i = 0;

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {

                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if(playerTeamsEntry.getValue().equals(Teams.BLEU)) {
                        i++;
                    }
                });
                if(i==Main.getInstance().getConfig().getInt("game.max-players-per-team")) {
                    player.sendMessage("§cÉquipe complete");
                    return;
                }
                Teams.setPlayerTeam(player,Teams.BLEU);
                Stuff.waitingTo(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return true;
            }
        });
        buttons.put(13, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack it;

                ArrayList<String> lore = new ArrayList<>();
                lore.add(" ");
                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if(playerTeamsEntry.getValue().equals(Teams.WAITING)) {
                        lore.add("&f- &7" + playerTeamsEntry.getKey().getName());
                    }
                });

                it = new ItemBuilder(Material.BARRIER).setName("&cAléatoire").setLore(lore).toItemStack();
                return it;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                Teams.setPlayerTeam(player,Teams.WAITING);
                Stuff.waitingTo(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return true;
            }

        });
        buttons.put(14, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ItemStack it;

                ArrayList<String> lore = new ArrayList<>();
                lore.add(" ");
                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if(playerTeamsEntry.getValue().equals(Teams.ROSE)) {
                        lore.add("&f- &5" + playerTeamsEntry.getKey().getName());
                    }
                });

                it = new ItemBuilder(Material.WOOL).setDurability(10).setName("&5&lViolet").setLore(lore).toItemStack();
                return it;
            }

            int i = 0;

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                Teams.players.entrySet().forEach(playerTeamsEntry -> {
                    if(playerTeamsEntry.getValue().equals(Teams.ROSE)) {
                        i++;
                    }
                });
                if(i==Main.getInstance().getConfig().getInt("game.max-players-per-team")) {
                    player.sendMessage("§cÉquipe complete");
                    return;
                }
                Teams.setPlayerTeam(player,Teams.ROSE);
                Stuff.waitingTo(player);
            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return true;
            }
        });

        buttons.put(21,new DisplayButton(new ItemBuilder(Material.AIR).toItemStack()));

        return buttons;
    }

    @Override
    public String getTitle(Player paramPlayer) {
        return "&8» &6&lChoisir une équipe";
    }
}
