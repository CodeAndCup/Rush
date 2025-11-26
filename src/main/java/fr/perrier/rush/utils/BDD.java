package fr.perrier.rush.utils;

import fr.perrier.rush.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

public class BDD {

    public void addPointsTo(Player player, Long i) {
        UUID uuid = player.getUniqueId();

        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            // Insérer ou mettre à jour les points
            String sql;
            if (Main.getInstance().getDatabaseManager().getDatabaseType() ==
                fr.perrier.rush.database.DatabaseManager.DatabaseType.MYSQL) {
                sql = "INSERT INTO player_data (uuid, points) VALUES (?, ?) " +
                      "ON DUPLICATE KEY UPDATE points = points + ?";
            } else {
                sql = "INSERT INTO player_data (uuid, points) VALUES (?, ?) " +
                      "ON CONFLICT(uuid) DO UPDATE SET points = points + ?";
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid.toString());
                stmt.setLong(2, i);
                stmt.setLong(3, i);
                stmt.executeUpdate();
            }

            // Vérifier et limiter les points
            String selectSql = "SELECT points FROM player_data WHERE uuid = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setString(1, uuid.toString());
                ResultSet rs = selectStmt.getResultSet();
                if (rs.next()) {
                    long points = rs.getLong("points");
                    if (points / 1000000000 > 1000000000) {
                        return;
                    }
                }
            }

        } catch (SQLException e) {
            player.sendMessage("§4Une erreur est survenue. [BDD:SQL]");
            e.printStackTrace();
        }
    }

    public TextComponent getPointsOf(Player player, String str) {
        UUID uuid = player.getUniqueId();
        long points = 0;

        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            String sql = "SELECT points FROM player_data WHERE uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    points = rs.getLong("points");
                } else {
                    // Créer une entrée pour le joueur s'il n'existe pas
                    createPlayerData(uuid.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String affichage = "";

        if(points < 1000) {
            affichage = String.valueOf(points);
        } else if(points < 1000000) {
            points = points / 1000;
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            affichage = "§6" + formatter.format(points).replaceAll(",", "'");
            affichage += "k";
        } else if(points < 1000000000) {
            points = points / 1000000;
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            affichage = "§c" + formatter.format(points).replaceAll(",", "'");
            affichage += "M";
        } else if(points / 1000 < 1000000000) {
            points = points / 1000000000;
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            affichage = "§4" + formatter.format(points).replaceAll(",", "'");
            affichage += "Md";
        } else if(points / 1000000 < 1000000000) {
            points /= 1000000;
            points /= 1000000;
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            affichage = "§5" + formatter.format(points).replaceAll(",", "'");
            affichage += "B";
        } else if(points / 1000000000 < 1000000000) {
            points /= 1000000;
            points /= 1000000000;
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator(' ');
            affichage = "§1" + formatter.format(points).replaceAll(",", "'");
            affichage += "T";
        } else {
            affichage = "§4§lI§c§lN§6§lF§e§lI§a§lN§2§lI";
        }

        // Récupérer les points réels pour le hover
        long realPoints = getPoints(uuid.toString());
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        String hoverpoints = formatter.format(realPoints).replaceAll(",", "'");

        TextComponent textComponent = new TextComponent("§7[" + affichage + "§7] " + str);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder("§f" + hoverpoints + " §bPoints").create()));

        return textComponent;
    }

    public void setKillEffectTo(Player player, Integer integer) {
        UUID uuid = player.getUniqueId();

        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            String sql;
            if (Main.getInstance().getDatabaseManager().getDatabaseType() ==
                fr.perrier.rush.database.DatabaseManager.DatabaseType.MYSQL) {
                sql = "INSERT INTO player_data (uuid, kill_effect) VALUES (?, ?) " +
                      "ON DUPLICATE KEY UPDATE kill_effect = ?";
            } else {
                sql = "INSERT INTO player_data (uuid, kill_effect) VALUES (?, ?) " +
                      "ON CONFLICT(uuid) DO UPDATE SET kill_effect = ?";
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid.toString());
                stmt.setInt(2, integer);
                stmt.setInt(3, integer);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            player.sendMessage("§4Une erreur est survenue. [BDD:SQL]");
            e.printStackTrace();
        }
    }

    public Integer getKillEffectOf(Player player) {
        UUID uuid = player.getUniqueId();

        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            String sql = "SELECT kill_effect FROM player_data WHERE uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("kill_effect");
                } else {
                    // Créer une entrée pour le joueur s'il n'existe pas
                    createPlayerData(uuid.toString());
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    private void createPlayerData(String uuid) {
        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            String sql = "INSERT INTO player_data (uuid, points, kill_effect) VALUES (?, 0, 1)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Ignorer si l'entrée existe déjà
        }
    }

    private long getPoints(String uuid) {
        try (Connection connection = Main.getInstance().getDatabaseManager().getConnection()) {
            String sql = "SELECT points FROM player_data WHERE uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong("points");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
