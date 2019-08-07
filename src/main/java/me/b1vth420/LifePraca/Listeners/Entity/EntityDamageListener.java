package me.b1vth420.LifePraca.Listeners.Entity;

import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageListener implements Listener{
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if(p.getFallDistance() > 6){
                JobUser.get(p).setBrokenLeg(true);
                p.sendMessage(ChatUtil.chat("&cZlamales sobie noge!"));
                PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, 9999999, 1);
                p.addPotionEffect(pe);
            }
        }
    }

}
