package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Objects.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class SchematicUtils {

    public static void pasteSchematic(World world, Location loc, Schematic schematic, HashSet<Material> allBlocks) {
        byte[] blocks = schematic.getBlocks();
        byte[] blockData = schematic.getData();

        short length = schematic.getLenght();
        short width = schematic.getWidth();
        short height = schematic.getHeight();

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    block.setTypeIdAndData(blocks[index], blockData[index], true);
                    allBlocks.add(block.getType());
                }
            }
        }
        allBlocks.remove(Material.AIR);
    }

    public static int isSame(World world, Location loc, Schematic schematic) {
        byte[] blocks = schematic.getBlocks();
        byte[] blockData = schematic.getData();

        short length = schematic.getLenght();
        short width = schematic.getWidth();
        short height = schematic.getHeight();

        int i = 0;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                    if(block.getTypeId() != blocks[index]){
                        i++;
                    }
                }
            }
        }
        return i;
    }

    private static void prepare(Player p, BuildingArea ba, PatternArena pa, JobUser ju){
        ju.setBuilding(false);
        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        InventoryUtil.restoreInventory(p);
        ba.clear();
        ba.setEmpty(true);
        pa.clear();
    }

    public static void check(PatternArena pa, BuildingArea ba, Player p){
        JobUser ju = JobUser.get(p);
        Job j = ju.getJob();
        Location l = new Location(pa.getCenter().getWorld(), ba.getCenter().getBlockX()-ba.getSize()+1, ba.getCenter().getBlockY(), ba.getCenter().getBlockZ()-ba.getSize()+1);

        if(SchematicUtils.isSame(l.getWorld(), l, pa.getSchema()) == 0){
            double money = (j.getLevels().get(pa.getSchema().getLevel()));

            p.sendTitle(ChatUtil.chat("&2Prace"), ChatUtil.chat(Lang.getInst().buildingSuccessfulBuildMessage), 20, 60, 20);
            ju.setMoney(ju.getMoney() + money);
            ChatUtil.sendActionBar(p, ChatUtil.chat(Lang.getInst().buildingSuccessfulMoneyMessage.replace("{MONEY}", String.valueOf(money))));

            prepare(p, ba, pa, ju);
        } else if(SchematicUtils.isSame(l.getWorld(), l, pa.getSchema()) <= 10){
            double money = (j.getLevels().get(pa.getSchema().getLevel())/2);

            p.sendTitle(ChatUtil.chat("&2Prace"), ChatUtil.chat(Lang.getInst().buildingPartlySuccessfulMessage), 20, 60, 20);
            ju.setMoney(ju.getMoney() + money);
            ChatUtil.sendActionBar(p, ChatUtil.chat(Lang.getInst().buildingSuccessfulMoneyMessage.replace("{MONEY}", String.valueOf(money))));

            prepare(p, ba, pa, ju);
        } else {
            p.sendTitle(ChatUtil.chat("&4Prace"), ChatUtil.chat(Lang.getInst().buildingNotSuccessfulMessage), 20, 60, 20);
            if(p.getLevel() <= 0){
                prepare(p, ba, pa, ju);
                ChatUtil.sendActionBar(p, ChatUtil.chat("&cMlody skocz po 0.5"));
            }
        }
    }


    public static Schematic loadSchematic(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(stream);

        String ss[] = file.getName().split("_");

        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if (!schematicTag.getName().equals("Schematic")) {
            throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
        }

        Map<String, Tag> schematic = schematicTag.getValue();
        if (!schematic.containsKey("Blocks")) {
            throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
        }

        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();

        String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
        if (!materials.equals("Alpha")) {
            throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
        }

        byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
        byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
        return new Schematic(ss[0], blocks, blockData, width, length, height, Integer.parseInt(ss[1]), Integer.parseInt(ss[2].replace(".schematic", "")));
    }

    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}
