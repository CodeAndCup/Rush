package fr.perrier.rush.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.perrier.rush.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private HikariDataSource dataSource;
    private DatabaseType databaseType;
    private final Main plugin;

    public enum DatabaseType {
        MYSQL, SQLITE
    }

    public DatabaseManager(Main plugin) {
        this.plugin = plugin;
        setupDatabase();
        createTables();
    }

    private void setupDatabase() {
        FileConfiguration config = plugin.getConfig();
        String type = config.getString("database.type", "SQLITE").toUpperCase();

        try {
            this.databaseType = DatabaseType.valueOf(type);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Type de base de données invalide: " + type + ". Utilisation de SQLITE par défaut.");
            this.databaseType = DatabaseType.SQLITE;
        }

        HikariConfig hikariConfig = new HikariConfig();

        if (databaseType == DatabaseType.MYSQL) {
            String host = config.getString("database.mysql.host", "localhost");
            int port = config.getInt("database.mysql.port", 3306);
            String database = config.getString("database.mysql.database", "rush");
            String username = config.getString("database.mysql.username", "root");
            String password = config.getString("database.mysql.password", "");

            hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC");
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        } else {
            String filename = config.getString("database.sqlite.filename", "rush_data.db");
            hikariConfig.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/" + filename);
            hikariConfig.setDriverClassName("org.sqlite.JDBC");
        }

        hikariConfig.setMaximumPoolSize(config.getInt("database.pool.max-pool-size", 10));
        hikariConfig.setMinimumIdle(config.getInt("database.pool.min-idle", 2));
        hikariConfig.setMaxLifetime(config.getLong("database.pool.max-lifetime", 1800000));
        hikariConfig.setConnectionTimeout(config.getLong("database.pool.connection-timeout", 5000));

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(hikariConfig);
        plugin.getLogger().info("Connexion à la base de données " + databaseType + " établie.");
    }

    private void createTables() {
        String createTableSQL;

        if (databaseType == DatabaseType.MYSQL) {
            createTableSQL = "CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "points BIGINT NOT NULL DEFAULT 0," +
                    "kill_effect INT NOT NULL DEFAULT 1" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        } else {
            createTableSQL = "CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid TEXT PRIMARY KEY," +
                    "points INTEGER NOT NULL DEFAULT 0," +
                    "kill_effect INTEGER NOT NULL DEFAULT 1" +
                    ");";
        }

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            plugin.getLogger().info("Tables de la base de données créées/vérifiées.");
        } catch (SQLException e) {
            plugin.getLogger().severe("Erreur lors de la création des tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            setupDatabase();
        }
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Connexion à la base de données fermée.");
        }
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}

