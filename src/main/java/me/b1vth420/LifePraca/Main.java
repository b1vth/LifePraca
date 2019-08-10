package me.b1vth420.LifePraca;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.b1vth420.LifePraca.Commands.*;
import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Data.FileManager;
import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Data.MySQL.SQLManager;
import me.b1vth420.LifePraca.Listeners.Block.BlockBreakListener;
import me.b1vth420.LifePraca.Listeners.Block.BlockPlaceListener;
import me.b1vth420.LifePraca.Listeners.Block.DilerBlockPlaceListener;
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
import me.b1vth420.LifePraca.Tasks.DeathPlayerTask;
import me.b1vth420.LifePraca.Tasks.PremiumJobTask;
import me.b1vth420.LifePraca.Tasks.UnemployedBenefitTask;
import me.b1vth420.LifePraca.Utils.Loader;
import me.b1vth420.LifePraca.Utils.RegisterUtil;
import me.b1vth420.LifePraca.Utils.SignMenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    private static Main inst;
    private ProtocolManager manager;
    private SignMenuFactory signMenuFactory;
    private SQLManager sql;
    private HashMap<Material, Map.Entry<ItemStack, Map.Entry<List<String>, Map.Entry<List<PotionEffect>, Map.Entry<Integer, Integer>>>>> drugs;
    private HashSet<Location> drugFarms;
    private HashMap<String, ItemStack> savedItems;
    private HashMap<String, Player> dead = new HashMap<>();

    public Main() {
        inst = this;
    }

    @Override
    public void onEnable() {
        init();
        registerListeners();
        registerCommands();
        registerTasks();
        registerDatabase();
        load();
    }

    @Override
    public void onDisable() {
        save();
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
        RegisterUtil.registerCommand(new MoneyCommand());
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
        Bukkit.getPluginManager().registerEvents(new DilerBlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerExpChangeListener(), this);
    }

    private void registerTasks(){
        new BuildingTimeTask().runTaskTimer(this, 20L, 20L);
        new PremiumJobTask().runTaskTimer(this, 20*60L, 20*60L);
        new UnemployedBenefitTask().runTaskTimer(this, 20*Config.getInst().benefitTime, 20*Config.getInst().benefitTime);
        new DeathPlayerTask().runTaskTimer(this, 20*2, 20*2);

    }

    private void registerDatabase(){
        sql = new SQLManager(this);
    }

    private void init(){
        manager = ProtocolLibrary.getProtocolManager();
        this.signMenuFactory = new SignMenuFactory(this);
        this.drugs = new HashMap<>();
        this.drugFarms = new HashSet<>();
        this.savedItems = new HashMap<>();
        FileManager.check();
        Loader.load();
        Config.getInst().load();
        Lang.getInst().load();
    }

    private void save(){
        sql.savePlayers();
        sql.saveBuildArenas();
        sql.savePatternArenas();
    }

    private void load(){
        sql.loadPatternArenas();
        sql.loadBuildArenas();
        sql.loadDrugFarms();
        sql.loadPlayers();
    }

    public SQLManager getSQLManager() { return sql; }
    public SignMenuFactory getSignMenuFactory() { return this.signMenuFactory; }

    public HashMap<Material, Map.Entry<ItemStack, Map.Entry<List<String>, Map.Entry<List<PotionEffect>, Map.Entry<Integer, Integer>>>>> getDrugs() { return drugs; }
    public HashSet<Location> getDrugFarms() { return drugFarms; }
    public HashMap<String, ItemStack> getSavedItems() { return savedItems; }
    public HashMap<String, Player> getDead() { return dead; }
}
