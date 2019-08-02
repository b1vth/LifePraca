package me.b1vth420.LifePraca;

import me.b1vth420.LifePraca.Commands.*;
import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Data.FileManager;
import me.b1vth420.LifePraca.Listeners.*;
import me.b1vth420.LifePraca.Tasks.BuildingTimeTask;
import me.b1vth420.LifePraca.Tasks.PremiumJobTask;
import me.b1vth420.LifePraca.Utils.Loader;
import me.b1vth420.LifePraca.Utils.RegisterUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main inst;

    public Main() {
        inst = this;
    }

    @Override
    public void onEnable() {
        FileManager.check();
        Loader.load();
        Config.getInst().load();

        registerListeners();
        registerCommands();
        registerTasks();
    }

    @Override
    public void onDisable() {
    }

    public static Main getInst() {
        if(inst == null) return new Main();
        return inst;
    }

    private void registerCommands(){
        RegisterUtil.registerCommand(new CreatePatternCommand());
        RegisterUtil.registerCommand(new CreateBuildingCommand());
        RegisterUtil.registerCommand(new PracaCommand());
        RegisterUtil.registerCommand(new CheckCommand());
        RegisterUtil.registerCommand(new BudowaCommand());
        RegisterUtil.registerCommand(new SluzbaCommand());
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new LevelUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    private void registerTasks(){
        new BuildingTimeTask().runTaskTimer(this, 20L, 20L);
        new PremiumJobTask().runTaskTimer(this, 20*60L, 20*60L);
    }
}
