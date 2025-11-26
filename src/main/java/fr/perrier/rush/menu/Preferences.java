package fr.perrier.rush.menu;

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

public class Preferences extends GlassMenu implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            ItemMeta it = e.getItem().getItemMeta();
            Player p = e.getPlayer();
            if (it.getDisplayName().contains("§c§lPréférences")) {
                openMenu(p);
            }
        }catch (Exception e1) {
            return;
        }
    }

    public static HashMap<Player,Integer> killEffect = new HashMap<>();

    @Override
    public int getGlassColor() {
        return 0;
    }

    @Override
    public Map<Integer, Button> getAllButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(11, new Button() {
            @Override
            public ItemStack getButtonItem(Player p0) {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§3 ");
                switch (killEffect.get(p0).toString()) {
                    case "1":
                        lore.add("§f» §cAucun");
                        lore.add("§8» §7Heart");
                        lore.add("§8» §7Villager");
                        lore.add("§8» §7Firework");
                        break;
                    case "2":
                        lore.add("§8» §7Aucun");
                        lore.add("§f» §dHeart");
                        lore.add("§8» §7Villager");
                        lore.add("§8» §7Firework");
                        break;
                    case "3":
                        lore.add("§8» §7Aucun");
                        lore.add("§8» §7Heart");
                        lore.add("§f» §aVillager");
                        lore.add("§8» §7Firework");
                        break;
                    case "4":
                        lore.add("§8» §7Aucun");
                        lore.add("§8» §7Heart ");
                        lore.add("§8» §7Villager");
                        lore.add("§f» §fFirework");
                        break;
                    default:
                        break;
                }

                ItemStack itemStack = new ItemBuilder(Material.SKULL_ITEM).setName("&cKill Effect").setLore(lore).toItemStack();
                return itemStack;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                try {
                    if(killEffect.get(player) == 4) {
                        killEffect.replace(player,1);
                        return;
                    }
                    if(killEffect.get(player) == 3) {
                        killEffect.replace(player,1);
                        return;
                    }
                    if (killEffect.get(player) == 2) {
                        killEffect.replace(player,1);
                        return;
                    }
                    killEffect.replace(player, killEffect.get(player) + 1);
                }catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
                return true;
            }

        });

        buttons.put(13,new DisplayButton(new ItemBuilder(Material.BARRIER).setName("&cSoon..").toItemStack()));
        buttons.put(15,new DisplayButton(new ItemBuilder(Material.BARRIER).setName("&cSoon..").toItemStack()));

        buttons.put(21,new DisplayButton(new ItemBuilder(Material.AIR).toItemStack()));
        return buttons;
    }

    @Override
    public String getTitle(Player paramPlayer) {
        return "&8» &cPréférences";
    }
}
