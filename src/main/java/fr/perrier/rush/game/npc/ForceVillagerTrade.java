package fr.perrier.rush.game.npc;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ForceVillagerTrade {

    private String invname;
    private MerchantRecipeList l = new MerchantRecipeList();

    /**
     * @param invname Inventory display name, (May contain color)
     *
     */
    public ForceVillagerTrade(String invname) {
        this.invname = invname;
    }

    /**
     * @param inOne The itemstack in the first input slot.
     * @param out The itemstack output.
     * @return ForceVillagerTrade object so you can invoke the next method like:
     * addTrade(...).addTrade(...).addTrade(...).openTrade(player);
     */
    public ForceVillagerTrade addTrade(ItemStack in, ItemStack out) {
        MerchantRecipe merchantRecipe = new MerchantRecipe(CraftItemStack.asNMSCopy(in), (net.minecraft.server.v1_8_R3.ItemStack) null,CraftItemStack.asNMSCopy(out),0,100000);

        l.add(merchantRecipe);
        return this;
    }

    /**
     * @param inOne The itemstack in the first input slot.
     * @param inTwo The itemstack on the second input slot.
     * @param out The itemstack output.
     * @return ForceVillagerTrade object so you can invoke the next method like:
     * addTrade(...).addTrade(...).addTrade(...).openTrade(player);
     */
    public ForceVillagerTrade addTrade(ItemStack inOne, ItemStack inTwo,
                                        ItemStack out) {
        l.add(new MerchantRecipe(CraftItemStack.asNMSCopy(inOne),
                CraftItemStack.asNMSCopy(inTwo), CraftItemStack.asNMSCopy(out),0,100000));
        return this;
    }

    /**
     * @param who The player who will see the Trade
     */
    public void openTrade(Player who) {
        final EntityHuman e = ((CraftPlayer) who).getHandle();
        e.openTrade(new IMerchant() {
            @Override
            public MerchantRecipeList getOffers(EntityHuman arg0) {
                return l;
            }

            @Override
            public void a_(net.minecraft.server.v1_8_R3.ItemStack arg0) {
            }

            @Override
            public void a_(EntityHuman arg0) {
            }

            @Override
            public IChatBaseComponent getScoreboardDisplayName() {
                return IChatBaseComponent.ChatSerializer.a(invname);
            }

            @Override
            public EntityHuman v_() {
                return e;
            }

            @Override
            public void a(MerchantRecipe arg0) {
            }
        });
    }
}