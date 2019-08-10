package me.b1vth420.LifePraca.Listeners.Job;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;

public class LevelUpListener implements Listener {

    @EventHandler
    public void LevelUp(LevelUpEvent e) {
        JobUser ju = JobUser.get(e.getPlayer());
        e.getPlayer().sendMessage(ChatUtil.chat(Lang.getInst().jobLevelUpMessage.replace("{LEVEL}", String.valueOf(e.getLevel() + 1))));
        ju.getLevels().put(ju.getJob(), new AbstractMap.SimpleEntry<>(ju.getLevels().get(ju.getJob()).getKey() + 1, ju.getLevels().get(ju.getJob()).getValue()));
        for (ItemStack is : ju.getJob().getPrizes().get(ju.getLevels().get(ju.getJob()).getKey()-1)) {
            if(is != null)
                e.getPlayer().getInventory().addItem(is);
        }
    }
}