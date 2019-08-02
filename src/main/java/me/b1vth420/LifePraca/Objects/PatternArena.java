package me.b1vth420.LifePraca.Objects;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import me.b1vth420.LifePraca.Managers.PatternArenaManager;
import me.b1vth420.LifePraca.Managers.SchematicManager;
import me.b1vth420.LifePraca.Utils.SchematicUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class PatternArena {

    private String name;
    private Location center;
    private Location lowerLoc;
    private Location upperLoc;
    private int size;
    private long time;
    private HashSet<Material> blocks = new HashSet<>();
    private Schematic schema;

    public PatternArena(String name, Location center, int size){
        this.name = name;
        this.center = center;
        this.size = size;
        PatternArenaManager.addPatternArena(this);
        reCalculate();
    }

    public static PatternArena get(String name){
        for(PatternArena ba : PatternArenaManager.getPatternArenas().values()){
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

    public void fill(){
        clear();
        Location l = new Location(center.getWorld(), center.getBlockX()-this.size+1, center.getBlockY(), center.getBlockZ()-this.size+1);
        Schematic s = SchematicManager.getRandom();
        SchematicUtils.pasteSchematic(l.getWorld(), l, s, this.blocks);
        this.schema = s;
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

    public static boolean canBuild(Player p, Block b){
        if((p == null) || (b == null)) return false;

        Location l = b.getLocation();

        if(PatternArenaManager.isIn(l) && !p.hasPermission("LifePraca.build")) return false;
        return true;
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

    public HashSet<Material> getBlocks() {
        return blocks;
    }

    public void setBlocks(HashSet<Material> blocks) {
        this.blocks = blocks;
    }

    public Schematic getSchema() {
        return schema;
    }

    public void setSchema(Schematic schema) {
        this.schema = schema;
    }
}
