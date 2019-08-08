package me.b1vth420.LifePraca.Listeners.Block;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.BlocksUtil;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

import java.util.AbstractMap;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        JobUser ju = JobUser.get(e.getPlayer());

        if (!PatternArena.canBuild(e.getPlayer(), e.getBlock())) {
            e.setCancelled(true);
            return;
        }

        if (!ju.hasJob()) return;

        Job j = ju.getJob();
        int pkt = ju.getLevels().get(j).getValue();
        int level = ju.getLevels().get(j).getKey();
        if(ju.getJob().getMoneyGivingBlocks() != null && ju.getJob().getMoneyGivingBlocks().containsKey(e.getBlock().getType())) {
            if (!(e.getBlock().getState().getData() instanceof Crops)) {
                ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
                e.getPlayer().sendMessage(ChatUtil.chat(Lang.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));
                ju.getLevels().replace(j, new AbstractMap.SimpleEntry<>(level, pkt + j.getMoneyGivingBlocks().get(e.getBlock().getType())));
            } else {
                if (BlocksUtil.isFullyGrown(e.getBlock())) {
                    ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
                    e.getPlayer().sendMessage(ChatUtil.chat(Lang.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));
                    ju.getLevels().replace(j, new AbstractMap.SimpleEntry<>(level, pkt + j.getMoneyGivingBlocks().get(e.getBlock().getType())));
                }
            }
        }

        Location l = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getBlockX() + 0.5, e.getBlock().getLocation().getBlockY() + 0.5, e.getBlock().getLocation().getBlockZ() + 0.5);

        if(Main.getInst().getDrugFarms().contains(e.getBlock().getLocation(l))) {
            if(Main.getInst().getDrugs().containsKey(e.getBlock().getType())){
                if(BlocksUtil.isFullyGrown(e.getBlock())){
                    int pktx = Main.getInst().getDrugs().get(e.getBlock().getType()).getValue().getValue().getValue().getValue();
                    e.setCancelled(true);
                    ItemStack is = ((ItemStack) Main.getInst().getDrugs().get(e.getBlock().getType()).getKey()).clone();
                    is.setAmount(RandomUtil.getRandInt(1, 4));
                    ju.getLevels().replace(ju.getJob(), new AbstractMap.SimpleEntry<>(ju.getLevels().get(ju.getJob()).getKey(), ju.getLevels().get(ju.getJob()).getValue() + pktx));
                    e.getPlayer().getWorld().dropItemNaturally(l, is);
                    e.getBlock().setType(Material.AIR);
                    Main.getInst().getSQLManager().deleteDrugFram(l);
                }
            }
        }

        if (ju.getLevels().get(j).getValue() >= level * level * 100) {
            LevelUpEvent ev = new LevelUpEvent(e.getPlayer(), level);
            Bukkit.getPluginManager().callEvent(ev);
        }
    }
}
