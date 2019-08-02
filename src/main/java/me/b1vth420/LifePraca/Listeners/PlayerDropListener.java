package me.b1vth420.LifePraca.Listeners;

import me.b1vth420.LifeDrop.Listeners.Event.PlayerDropEvent;
import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.AbstractMap;

public class PlayerDropListener implements Listener {
    @EventHandler
    public void onDrop(PlayerDropEvent e) {
        JobUser ju = JobUser.get(e.getPlayer());
        if(!ju.hasJob()) return;
        if (!ju.getJob().getMoneyGivingDrops().containsKey(e.getDrop().getItem().getType())) return;

        Job j = Job.get(ju.getJob());
        ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
        e.getPlayer().sendMessage(ChatUtil.chat(Config.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));

        int pkt = ju.getLevels().get(j).getValue();
        int level = ju.getLevels().get(j).getKey();
        ju.getLevels().replace(j, new AbstractMap.SimpleEntry<>(level, pkt+j.getMoneyGivingDrops().get(e.getDrop().getItem().getType())));

        if(ju.getLevels().get(j).getValue() >= level * level * 100) {
            LevelUpEvent ev = new LevelUpEvent(e.getPlayer(), level);
            Bukkit.getPluginManager().callEvent(ev);
        }
    }
}
