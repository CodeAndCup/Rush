package fr.perrier.rush.api.menu.buttons;


import fr.perrier.rush.api.menu.Button;
import fr.perrier.rush.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class Glass extends Button {

    private int data;

    public Glass() {
        data = 7;
    }

    public Glass(int data) {
        this.data = data;
    }

    @Override
    public ItemStack getButtonItem(final Player player) {
        final ItemBuilder item = new ItemBuilder(Material.STAINED_GLASS).setDurability(data).setName(" ");
        return item.toItemStack();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
    }
}
