package me.b1vth420.LifePraca.Objects;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import me.b1vth420.LifePraca.Managers.BuildingArenaManager;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class BuildingArea {

    private String name;
    private Location center;
    private Location lowerLoc;
    private Location upperLoc;
    private int size;
    private PatternArena pa;
    private boolean empty;

    public BuildingArea(String name, Location center, PatternArena pa){
        this.name = name;
        this.center = center;
        this.size = pa.getSize();
        this.pa = pa;
        this.empty = true;
        BuildingArenaManager.addArena(this);
        reCalculate();
    }

    public static BuildingArea get(String name){
        for(BuildingArea ba : BuildingArenaManager.getArena().values()){
            if(ba.getName().equalsIgnoreCase(name)) return ba;
        }
        return null;
    }

    private void reCalculate(){
        Vector low = new Vector(center.getBlockX() - this.size, 0, center.getBlockZ() - this.size);
        Vector up = new Vector(center.getBlockX() + this.size, 256, center.getBlockZ() + this.size);
        this.lowerLoc = low.toLocation(center.getWorld());
        this.upperLoc = up.toLocation(center.getWorld());
    }

    public void clear(){
        Selection selection = new CuboidSelection(this.getCenter().getWorld(), this.getLowerLoc(), this.getUpperLoc());
        try {
            Region region = selection.getRegionSelector().getRegion();
            region.getWorld().regenerate(region, WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1));
        } catch (IncompleteRegionException e) { e.printStackTrace(); }
    }

    public boolean isIn(Location l){
        reCalculate();
        if(this.lowerLoc == null || this.upperLoc == null || l == null) return false;
        if((l.getBlockX() > getLowerLoc().getBlockX()) && (l.getBlockX() < getUpperLoc().getBlockX()) &&
                (l.getBlockY() > getLowerLoc().getBlockY()) && (l.getBlockY() < getUpperLoc().getBlockY()) &&
                (l.getBlockZ() > getLowerLoc().getBlockZ()) && (l.getBlockZ() < getUpperLoc().getBlockZ())
        ){
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public Location getLowerLoc() {
        return lowerLoc;
    }

    public void setLowerLoc(Location lowerLoc) {
        this.lowerLoc = lowerLoc;
    }

    public Location getUpperLoc() {
        return upperLoc;
    }

    public void setUpperLoc(Location upperLoc) {
        this.upperLoc = upperLoc;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PatternArena getPa() {
        return pa;
    }

    public void setPa(PatternArena pa) {
        this.pa = pa;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
