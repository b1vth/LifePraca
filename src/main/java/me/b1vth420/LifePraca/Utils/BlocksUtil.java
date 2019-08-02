package me.b1vth420.LifePraca.Utils;

import org.bukkit.CropState;
import org.bukkit.block.Block;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

public class BlocksUtil {

    public static boolean isFullyGrown(Block block) {
        MaterialData md = block.getState().getData();
        if(md instanceof Crops)
            return (((Crops) md).getState() == CropState.RIPE);
        return false;
    }
}
