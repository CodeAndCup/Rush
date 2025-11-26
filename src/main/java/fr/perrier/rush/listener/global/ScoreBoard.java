package fr.perrier.rush.listener.global;

import fr.perrier.rush.*;
import fr.perrier.rush.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreBoard implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		Main.getInstance().getScoreboardManager().onLogin(p);

		}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		Main.getInstance().getScoreboardManager().onLogout(p);

	}
}
