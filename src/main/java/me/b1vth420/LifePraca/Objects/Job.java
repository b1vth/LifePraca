package me.b1vth420.LifePraca.Objects;

import me.b1vth420.LifePraca.Managers.JobManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job {

    private String name;
    private Map<Integer, Double> levels;
    private boolean drop;
    private boolean exp;
    private Enum jobType;
    private ItemStack jobItem;
    private List<String> jobDescription = new ArrayList<>();
    private List<List<ItemStack>> prizes;
    private HashMap<Material, Integer> moneyGivingBlocks;
    private HashMap<Material, Integer> moneyGivingDrops;
    private HashMap<EntityType, Integer> moneyGivingKills;

    public Job(String name, Map<Integer, Double> levels, boolean drop, boolean exp, Enum jobType, HashMap<Material, Integer> moneyGivingBlocks, HashMap<Material, Integer> moneyGivingDrops, HashMap<EntityType, Integer> moneyGivingKills, ItemStack jobItem, List<String> jobDescription, List<List<ItemStack>> prizes){
        this.name = name;
        this.levels = levels;
        this.drop = drop;
        this.exp = exp;
        this.jobType = jobType;
        this.jobItem = jobItem;
        this.jobDescription = jobDescription;
        this.prizes = prizes;
        this.moneyGivingBlocks = moneyGivingBlocks;
        this.moneyGivingDrops = moneyGivingDrops;
        this.moneyGivingKills = moneyGivingKills;
        JobManager.addJob(this);
    }

    public static Job get(Job j){
        for(Job job : JobManager.getJobs().values()){
            if(job.equals(j)) return job;
        }
        return null;
    }

    public static Job get(String name){
        for(Job j : JobManager.getJobs().values()){
            if(j.getName().equalsIgnoreCase(name)){
                return j;
            }
        }
        return null;
    }

    public static Job get(ItemStack is, me.b1vth420.LifePraca.Enums.jobType type){
        for(Job j : JobManager.getJobs().values()){
            if(j.getJobItem().equals(is) && j.getJobType().equals(type)){
                return j;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Double> getLevels() {
        return levels;
    }

    public void setLevels(Map<Integer, Double> levels) {
        this.levels = levels;
    }

    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

    public Enum getJobType() {
        return jobType;
    }

    public void setJobType(Enum jobType) {
        this.jobType = jobType;
    }

    public HashMap<Material, Integer> getMoneyGivingBlocks() {
        return moneyGivingBlocks;
    }

    public void setMoneyGivingBlocks(HashMap<Material, Integer> moneyGivingBlocks) {
        this.moneyGivingBlocks = moneyGivingBlocks;
    }

    public HashMap<Material, Integer> getMoneyGivingDrops() {
        return moneyGivingDrops;
    }

    public void setMoneyGivingDrops(HashMap<Material, Integer> moneyGivingDrops) {
        this.moneyGivingDrops = moneyGivingDrops;
    }

    public HashMap<EntityType, Integer> getMoneyGivingKills() {
        return moneyGivingKills;
    }

    public void setMoneyGivingKills(HashMap<EntityType, Integer> moneyGivingKills) {
        this.moneyGivingKills = moneyGivingKills;
    }

    public ItemStack getJobItem() {
        return jobItem;
    }

    public void setJobItem(ItemStack jobItem) {
        this.jobItem = jobItem;
    }

    public List<String> getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(List<String> jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<List<ItemStack>> getPrizes() {
        return prizes;
    }
}
