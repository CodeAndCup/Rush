package fr.perrier.rush.listener.game;

import fr.perrier.rush.*;
import fr.perrier.rush.game.*;
import fr.perrier.rush.listener.game.Chemin;
import fr.perrier.rush.menu.*;
import fr.perrier.rush.utils.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class Kills implements Listener {

    public static HashMap<Player,Integer> kills = new HashMap<>();
    public static HashMap<Player,Integer> morts = new HashMap<>();
    
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        Teams teams = Teams.players.get(p);
        e.getDrops().clear();
        Chemin.cheminNb.replace(p,1);

        if(e.getEntity().getKiller() != null) {
            String teamP = Teams.players.get(e.getEntity().getPlayer()).getColor() + Teams.players.get(e.getEntity().getPlayer()).getName();
            String teamK = Teams.players.get(e.getEntity().getKiller()).getColor() + Teams.players.get(e.getEntity().getKiller()).getName();
            e.setDeathMessage((teamP + " " + e.getEntity().getPlayer().getName() + " &7a été tué par " + teamK + " " + e.getEntity().getKiller().getName()).replace("&","§"));
            if(kills.get(e.getEntity().getKiller()) != null) {
                kills.replace(e.getEntity().getKiller(), kills.get(e.getEntity().getKiller())+1);
            }else{
                kills.put(e.getEntity().getKiller(),1);
            }
            if(morts.get(e.getEntity().getPlayer()) != null) {
                morts.replace(e.getEntity().getPlayer(), morts.get(e.getEntity().getPlayer())+1);
            }else{
                morts.put(e.getEntity().getPlayer(),1);
            }

            if(Preferences.killEffect.get(e.getEntity().getKiller()) !=1 ) {
                Location loc = e.getEntity().getLocation();
                if(Preferences.killEffect.get(e.getEntity().getKiller()) == 2 ) {
                    Bukkit.getOnlinePlayers().forEach(online -> {
                        ((CraftPlayer) online).getHandle().playerConnection
                                .sendPacket(new PacketPlayOutWorldParticles(
                                        EnumParticle.HEART, true, (float) loc.getX(),(float) loc.getY(), (float) loc.getZ(), 1, 1, 1,(float) 0, 10));
                    });
                }else if(Preferences.killEffect.get(e.getEntity().getKiller()) == 3 ) {
                    Bukkit.getOnlinePlayers().forEach(online -> {
                        ((CraftPlayer) online).getHandle().playerConnection
                                .sendPacket(new PacketPlayOutWorldParticles(
                                        EnumParticle.VILLAGER_HAPPY, true, (float) loc.getX(),(float) loc.getY(), (float) loc.getZ(), 1, 1, 1,(float) 0, 10));
                    });
                }else{
                    Firework fw = (Firework) p.getWorld().spawn(p.getEyeLocation(), Firework.class);

                    FireworkMeta fireworkMeta = (FireworkMeta) fw.getFireworkMeta();
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED, Color.BLACK).trail(true).flicker(true).build());
                    fireworkMeta.setPower(0);
                    fw.setFireworkMeta(fireworkMeta);
                }
            }

        }else{
            String team_p = Teams.players.get(e.getEntity().getPlayer()).getColor() + Teams.players.get(e.getEntity().getPlayer()).getName();
            e.setDeathMessage((team_p + " " + e.getEntity().getPlayer().getName() + "&7 est mort.").replace("&","§"));
        }

        if(teams.equals(Teams.BLEU)) {
            new BukkitRunnable() {
                public void run() {
                    ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                    p.teleport(BedLocation.spawnBlue());
                    Stuff.giveTo(p);
                }
            }.runTaskLater((Plugin) Main.getInstance(), 1L);
            if(BedLocation.isBreak(BedLocation.BLUE_BED)) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage("§cVous êtes mort et vous ne pouvez plus réapparaitre.");
                Teams.setPlayerTeam(p,Teams.BLEU_MORT);
            }else{
                p.setGameMode(GameMode.SPECTATOR);
                for(int i=0; i<5;i++) {
                    int finalI = i;
                    new BukkitRunnable() {
                        public void run() {
                            p.sendTitle("§cRespawn dans",5- finalI + " seconde(s)");
                        }
                    }.runTaskLater(Main.getInstance(), 20L*i);
                    new BukkitRunnable() {
                        public void run() {
                            p.teleport(BedLocation.spawnBlue());
                            p.setGameMode(GameMode.SURVIVAL);
                            p.sendTitle("","");
                        }
                    }.runTaskLater(Main.getInstance(), 20L*5);
                }
            }
        } else if (teams.equals(Teams.ROSE)) {
            new BukkitRunnable() {
                public void run() {
                    ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                    p.teleport(BedLocation.spawnPink());
                    Stuff.giveTo(p);
                }
            }.runTaskLater(Main.getInstance(), 1L);
            if(BedLocation.isBreak(BedLocation.PINK_BED)) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage("§cVous êtes mort et vous ne pouvez plus réapparaitre.");
                Teams.setPlayerTeam(p,Teams.ROSE_MORT);
            }else{
                p.setGameMode(GameMode.SPECTATOR);
                for(int i=0; i<5;i++) {
                    int finalI = i;
                    new BukkitRunnable() {
                        public void run() {
                            p.sendTitle("§cRespawn dans",5- finalI + " seconde(s)");
                        }
                    }.runTaskLater(Main.getInstance(), 20L*i);
                    new BukkitRunnable() {
                        public void run() {
                            p.teleport(BedLocation.spawnPink());
                            p.setGameMode(GameMode.SURVIVAL);
                        }
                    }.runTaskLater(Main.getInstance(), 20L*5);
                }
            }
        }
        if(fallinvoid.contains(e.getEntity().getPlayer())) {
            fallinvoid.remove(e.getEntity().getPlayer());
        }
        new Win().attemp();
    }

    ArrayList<Player> fallinvoid = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(Main.getInstance().getStatus().equals(GameStatus.PLAYING)) {
            if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) || e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
            if (e.getPlayer().getLocation().getY() <= 50) {
                if(fallinvoid.contains(e.getPlayer())) return;
                fallinvoid.add(e.getPlayer());
                e.getPlayer().setHealth(0.0D);
            }
        }
        if(Main.getInstance().getStatus().equals(GameStatus.WAITING) || Main.getInstance().getStatus().equals(GameStatus.STARTING)) {
            if(e.getPlayer().getLocation().getY() <= 0) {
                e.getPlayer().teleport(new Location(Main.getInstance().getServer().getWorld("world"),21.5,50,-2.5));
                e.getPlayer().sendMessage("§7§oPourquoi te jeter dans le vide comme ça ?");
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof TNTPrimed) {
            e.setDamage(1.75);
        }
    }
}
