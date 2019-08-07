package me.b1vth420.LifePraca.Listeners.Block;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.BlocksUtil;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
        if (!ju.getJob().getMoneyGivingBlocks().containsKey(e.getBlock().getType())) return;

        Job j = ju.getJob();
        int pkt = ju.getLevels().get(j).getValue();
        int level = ju.getLevels().get(j).getKey();

        if (!(e.getBlock().getState().getData() instanceof Crops)) {
            ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
            e.getPlayer().sendMessage(ChatUtil.chat(Config.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));
            ju.getLevels().replace(j, new AbstractMap.SimpleEntry<>(level, pkt + j.getMoneyGivingBlocks().get(e.getBlock().getType())));
        } else {
            if (BlocksUtil.isFullyGrown(e.getBlock())) {
                ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
                e.getPlayer().sendMessage(ChatUtil.chat(Config.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));
                ju.getLevels().replace(j, new AbstractMap.SimpleEntry<>(level, pkt + j.getMoneyGivingBlocks().get(e.getBlock().getType())));
            }
        }

        if (ju.getLevels().get(j).getValue() >= level * level * 100) {
            LevelUpEvent ev = new LevelUpEvent(e.getPlayer(), level);
            Bukkit.getPluginManager().callEvent(ev);
        }
    }
}
