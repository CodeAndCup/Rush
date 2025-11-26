package fr.perrier.rush.utils;

import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class Stuff {

    public static void giveTo(Player p) {
        Teams teams = Teams.players.get(p);
        Color dye = null;
        if(teams.equals(Teams.ROSE)) {
            dye = Color.FUCHSIA;
        } else if (teams.equals(Teams.BLEU)) {
            dye = Color.AQUA;
        }
        ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                .setInfinityDurability()
                .setLeatherArmorColor(dye)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1)
                .setName("§fÉquipe " + teams.getColor() + teams.getName())
                .toItemStack();
        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setInfinityDurability()
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2)
                .setName("§fÉquipe " + teams.getColor() + teams.getName())
                .toItemStack();
        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setInfinityDurability()
                .setLeatherArmorColor(dye)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1)
                .setName("§fÉquipe " + teams.getColor() + teams.getName())
                .toItemStack();
        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .setInfinityDurability()
                .setLeatherArmorColor(dye)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1)
                .setName("§fÉquipe " + teams.getColor() + teams.getName())
                .toItemStack();
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .setInfinityDurability()
                .addEnchant(Enchantment.DAMAGE_ALL,2)
                .setName("§bÉpée III")
                .toItemStack();
        ItemStack briquet = new ItemBuilder(Material.FLINT_AND_STEEL)
                .setDurability(44)
                .toItemStack();

        p.getInventory().setArmorContents(new ItemStack[]{boots,leggings,chestplate,helmet});
        p.getInventory().setItem(0,sword);
        p.getInventory().setItem(1,briquet);
    }

    public static void waitingTo(Player player) {
        ItemStack TeamSelector;
        if (Teams.players.get(player).equals(Teams.BLEU)) {
            TeamSelector = new ItemBuilder(Material.STAINED_CLAY)
                    .setDyeColor(DyeColor.LIGHT_BLUE)
                    .setName("&f&lÉquipe &3&lCyan")
                    .toItemStack();
        } else if (Teams.players.get(player).equals(Teams.ROSE)) {
            TeamSelector = new ItemBuilder(Material.STAINED_CLAY)
                    .setDyeColor(DyeColor.PINK)
                    .setName("&f&lÉquipe &5&lViolette")
                    .toItemStack();
        } else {
            TeamSelector = new ItemBuilder(Material.STAINED_CLAY)
                    .setName("&f&lÉquipe &7&lAléatoire")
                    .toItemStack();
        }

        ItemStack pref = new ItemBuilder(Material.COMMAND)
                .setName("§c§lPréférences")
                .toItemStack();

        ItemStack GoHub = new ItemBuilder(Material.INK_SACK)
                .setDurability(1)
                .setName("&c&lRetour au lobby")
                .toItemStack();

        player.getInventory().setItem(0,TeamSelector);
        player.getInventory().setItem(7,pref);
        player.getInventory().setItem(8,GoHub);
    }
}
