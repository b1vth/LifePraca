package me.b1vth420.LifePraca;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.b1vth420.LifePraca.Commands.*;
import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Data.FileManager;
import me.b1vth420.LifePraca.Data.MySQL.SQLManager;
import me.b1vth420.LifePraca.Listeners.Block.BlockBreakListener;
import me.b1vth420.LifePraca.Listeners.Block.BlockPlaceListener;
import me.b1vth420.LifePraca.Listeners.Entity.EntityDamageListener;
import me.b1vth420.LifePraca.Listeners.Entity.EntityDeathListener;
import me.b1vth420.LifePraca.Listeners.Inventory.BudowaInventoryClickListener;
import me.b1vth420.LifePraca.Listeners.Inventory.InventoryClickListener;
import me.b1vth420.LifePraca.Listeners.Inventory.MedykInventoryClickListener;
import me.b1vth420.LifePraca.Listeners.Inventory.PolicjantInventoryClickListener;
import me.b1vth420.LifePraca.Listeners.Job.JobJoinListener;
import me.b1vth420.LifePraca.Listeners.Job.LevelUpListener;
import me.b1vth420.LifePraca.Listeners.Player.*;
import me.b1vth420.LifePraca.Tasks.BuildingTimeTask;
import me.b1vth420.LifePraca.Tasks.PremiumJobTask;
import me.b1vth420.LifePraca.Tasks.SleepTask;
import me.b1vth420.LifePraca.Tasks.UnemployedBenefitTask;
import me.b1vth420.LifePraca.Utils.Loader;
import me.b1vth420.LifePraca.Utils.RegisterUtil;
import me.b1vth420.LifePraca.Utils.SignMenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    private static Main inst;
    private ProtocolManager manager;
    private SignMenuFactory signMenuFactory;
    private SQLManager sql;
    private HashMap<ItemStack, Map.Entry<List<String>, List<PotionEffect>>> drugs;

    public Main() {
        inst = this;
    }

    @Override
    public void onEnable() {
        init();
        FileManager.check();
        Loader.load();
        Config.getInst().load();

        registerListeners();
        registerCommands();
        registerTasks();
        registerDatabase();

        sql.loadPlayers();
    }

    @Override
    public void onDisable() {
        sql.savePlayers();
        sql.onDisable();
    }

    public static Main getInst() {
        if(inst == null) return new Main();
        return inst;
    }

    public ProtocolManager getManager() {
        return manager;
    }

    private void registerCommands(){
        RegisterUtil.registerCommand(new CreatePatternCommand());
        RegisterUtil.registerCommand(new CreateBuildingCommand());
        RegisterUtil.registerCommand(new PracaCommand());
        RegisterUtil.registerCommand(new CheckCommand());
        RegisterUtil.registerCommand(new BudowaCommand());
        RegisterUtil.registerCommand(new SluzbaCommand());
        RegisterUtil.registerCommand(new SpawnNpcCommand());
        RegisterUtil.registerCommand(new AwansCommand());
        RegisterUtil.registerCommand(new DrugsCommand());
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new LevelUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new JobJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PolicjantInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new MedykInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new BudowaInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void registerTasks(){
        new BuildingTimeTask().runTaskTimer(this, 20L, 20L);
        new PremiumJobTask().runTaskTimer(this, 20*60L, 20*60L);
        new UnemployedBenefitTask().runTaskTimer(this, 20*Config.getInst().benefitTime, 20*Config.getInst().benefitTime);
        new SleepTask().runTaskTimer(this, 20*10, 20*10);
    }

    private void registerDatabase(){
        sql = new SQLManager(this);
    }

    private void init(){
        manager = ProtocolLibrary.getProtocolManager();
        this.signMenuFactory = new SignMenuFactory(this);
        this.drugs = new HashMap<>();
    }

    public SQLManager getSQLManager() { return sql; }
    public SignMenuFactory getSignMenuFactory() { return this.signMenuFactory; }
    public HashMap<ItemStack, Map.Entry<List<String>, List<PotionEffect>>> getDrugs() { return drugs; }
}
