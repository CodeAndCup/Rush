package fr.perrier.rush.listener.global;

import fr.perrier.rush.*;
import fr.perrier.rush.game.*;
import fr.perrier.rush.menu.*;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.*;

public class JoinAndLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Bukkit.getOnlinePlayers().size() >= Main.getInstance().getConfig().getInt("game.max-players-per-team")*2+1) {
            e.getPlayer().kickPlayer("Game Pleine.");
            e.setJoinMessage("");
            return;
        }
        e.getPlayer().getInventory().clear();
        if(Main.getInstance().getStatus().equals(GameStatus.PLAYING) || Main.getInstance().getStatus().equals(GameStatus.STARTING) || Main.getInstance().getStatus().equals(GameStatus.FINISH)) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.setJoinMessage("");
            e.getPlayer().teleport(new Location(Main.getInstance().getServer().getWorld("Rush"),1024,70,1024 ));
            return;
        }
        Teams.setPlayerTeam(e.getPlayer(),Teams.WAITING);
        e.setJoinMessage("§8[§a+§8] §f" + e.getPlayer().getName());
        e.getPlayer().teleport(new Location(Main.getInstance().getServer().getWorld("world"),21.5 ,50,-2.5));
        e.getPlayer().setMaxHealth(20);
        Stuff.waitingTo(e.getPlayer());

        Preferences.killEffect.put(e.getPlayer(), new BDD().getKillEffectOf(e.getPlayer()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Teams.players.remove(e.getPlayer());
        e.setQuitMessage("§8[§c-§8] §f" + e.getPlayer().getName());

        if(Main.getInstance().getStatus().equals(GameStatus.PLAYING)) {
            new Win().attemp();
        }

        if(Bukkit.getOnlinePlayers().size()-1 <= 0) {
            if(Main.getInstance().getStatus().equals(GameStatus.PLAYING)) {
                Main.getInstance().setStatus(GameStatus.FINISH);
                Bukkit.getScheduler().runTaskLater((Plugin) this, () -> {
                    Bukkit.broadcastMessage("§8» §cShutdown ..");
                    Bukkit.shutdown();
                }, 5 * 20L);
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            ItemMeta it = e.getItem().getItemMeta();
            Player p = e.getPlayer();
            if (it.getDisplayName().equalsIgnoreCase("&c&lRetour au lobby")) {
                p.performCommand("hub");
            }
        }catch (Exception e1) {
            return;
        }
    }
}
