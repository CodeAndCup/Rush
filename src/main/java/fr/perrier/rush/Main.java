package fr.perrier.rush;

import fr.perrier.rush.api.menu.MenuAPI;
import fr.perrier.rush.commands.*;
import fr.perrier.rush.database.DatabaseManager;
import fr.perrier.rush.game.*;
import fr.perrier.rush.game.npc.type.*;
import fr.perrier.rush.game.npc.type.Block;
import fr.perrier.rush.listener.game.Chemin;
import fr.perrier.rush.listener.game.Compass;
import fr.perrier.rush.listener.game.Kills;
import fr.perrier.rush.listener.global.Cancel;
import fr.perrier.rush.listener.global.JoinAndLeave;
import fr.perrier.rush.listener.global.ScoreBoard;
import fr.perrier.rush.listener.global.Tchat;
import fr.perrier.rush.menu.*;
import fr.perrier.rush.scoreboard.*;
import fr.perrier.rush.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;
import java.util.concurrent.*;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private DatabaseManager databaseManager;
    @Setter
    private GameStatus status;

    private MenuAPI menuAPI;

    public static final Integer JOUEUR_MAX = 10;

    @Override
    public void onLoad() {
        Field field = null;
        try {
            field = net.minecraft.server.v1_8_R3.Block.class.getDeclaredField("durability");
            field.setAccessible(true);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("sea_lantern"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("wool"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("trapped_chest"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("wall_banner"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("standing_banner"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("stained_glass"),300f);
            field.set(net.minecraft.server.v1_8_R3.Block.getByName("bed"),300f);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);

        menuAPI = new MenuAPI(this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        this.status = GameStatus.WAITING;

        new Waiting().runTaskTimer(instance,20L,20L);

        registerListeners();
        registerCommands();

        Teams.setupBed();

        World world = new WorldCreator("Rush").environment(World.Environment.THE_END).createWorld();
        world.getEntities().forEach(Entity::remove);
        world.getWorldBorder().setCenter(new Location(getServer().getWorld("Rush"),1024,64,1024));
        world.getWorldBorder().setSize(150*2);

        new Speed().spawn();
        new Block().spawn();
        new Food().spawn();
        new Weapons().spawn();
        new Armors().spawn();
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();

        // Fermer la connexion à la base de données
        if (databaseManager != null) {
            databaseManager.close();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ScoreBoard(),this);
        getServer().getPluginManager().registerEvents(new TeamSelector(),this);
        getServer().getPluginManager().registerEvents(new Tchat(),this);
        getServer().getPluginManager().registerEvents(new Run(),this);
        getServer().getPluginManager().registerEvents(new Kills(),this);
        getServer().getPluginManager().registerEvents(new BedEvents(),this);
        getServer().getPluginManager().registerEvents(new Cancel(),this);
        getServer().getPluginManager().registerEvents(new JoinAndLeave(),this);
        getServer().getPluginManager().registerEvents(new Compass(),this);
        getServer().getPluginManager().registerEvents(new Preferences(),this);
        getServer().getPluginManager().registerEvents(new Chemin(),this);

        getServer().getPluginManager().registerEvents(new Speed(),this);
        getServer().getPluginManager().registerEvents(new Food(),this);
        getServer().getPluginManager().registerEvents(new Block(),this);
        getServer().getPluginManager().registerEvents(new Armors(),this);
        getServer().getPluginManager().registerEvents(new Weapons(),this);
    }

    private void registerCommands() {
        getCommand("admin").setExecutor(new Admin());
        getCommand("rush").setExecutor(new Rush());
    }
}
