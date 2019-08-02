package me.b1vth420.LifePraca.Objects;

import me.b1vth420.LifePraca.Managers.JobManager;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobUser {

    private String name;
    private UUID uuid;
    private Job job;
    private double money;
    private boolean building;
    private boolean onDuty;
    private HashMap<Job, Map.Entry<Integer, Integer>> levels;
    private BuildingArea ba;
    private Inventory inv;

    private JobUser(String name, UUID uuid, Job job, double money, HashMap<Job, Map.Entry<Integer, Integer>> levels, Inventory inv){
        this.name = name;
        this.uuid = uuid;
        this.job = job;
        this.money = money;
        this.building = false;
        this.levels = levels;
        this.ba = null;
        this.building = false;
        this.inv = inv;
        this.onDuty = false;

        for(Job j : JobManager.getJobs().values()){
            this.levels.put(j, new AbstractMap.SimpleEntry<>(1, 0));
        }
        JobUserManager.addJobUser(this);
    }

    public static JobUser get(Player p){
        for(JobUser ju : JobUserManager.getJobUsers().values()){
            if(ju.getUuid().equals(p.getUniqueId())) return ju;        }
        return new JobUser(p.getName(), p.getUniqueId(), null, 0.00, new HashMap<>(), p.getInventory());
    }

    public boolean hasJob(){
        if(this.job == null) return false;
        return true;
    }

    public Player getPlayer(){
        Player p = Bukkit.getPlayer(this.uuid);
        if(p.isOnline()) return p;
        return null;
    }

    public void sendMessage(String s){
        if(getPlayer() != null) getPlayer().sendMessage(ChatUtil.chat(s));
    }

    public void sendActionBar(String s) { if(getPlayer() != null) ChatUtil.sendActionBar(getPlayer(), ChatUtil.chat(s));}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public HashMap<Job, Map.Entry<Integer, Integer>> getLevels() {
        return levels;
    }

    public void setLevels(HashMap<Job, Map.Entry<Integer, Integer>> levels) {
        this.levels = levels;
    }

    public BuildingArea getBuildingArea() {
        return ba;
    }

    public void setBuildingArea(BuildingArea ba) {
        this.ba = ba;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }
}
