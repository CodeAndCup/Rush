package fr.perrier.rush.listener.game;

import fr.perrier.rush.utils.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Item;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class Compass implements Listener {

    Player result = null;
    Integer lastDist = 1000;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInHand() == null) return;
        try {
            if (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Traqueur")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player == p) continue;
                    if (Teams.players.get(p) == Teams.players.get(player)) continue;
                    Integer dist = (int) player.getLocation().distance(p.getLocation());
                    if (dist < lastDist) {
                        lastDist = dist;
                        result = player;
                    }
                }
                if (result == null) return;
                String cible = LocationUtils.getArrow(p.getLocation(), result.getLocation());
                IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + result.getName() + " - " + cible + " [" + ((int)lastDist) + " Blocks]" + "\"}");
                PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
                p.setCompassTarget(result.getLocation());
                result = null;
                lastDist = 1000;
            }
        }catch (Exception e1) {
            return;
        }
    }
}
