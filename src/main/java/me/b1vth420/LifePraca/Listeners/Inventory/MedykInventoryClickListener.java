package me.b1vth420.LifePraca.Listeners.Inventory;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.DeathUtil;
import me.b1vth420.LifePraca.Utils.IDUtil;
import me.confuser.barapi.BarAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffectType;

public class MedykInventoryClickListener implements Listener{
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aMenu medyka"))){
            JobUser ju = JobUser.get((Player)e.getWhoClicked());
            JobUser ju2 = JobUser.get(ju.getCheeked());
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.CAKE)) {
                if(ju2.isBrokenLeg()){
                    ju2.setBrokenLeg(false);
                    ju.sendMessage(Lang.getInst().brokenLegHelpMessageTo.replace("{PLAYER}", ju.getName()));
                    ju2.sendMessage(Lang.getInst().brokenLegHelpMessageWho.replace("{PLAYER}", ju2.getName()));
                }
                ju2.getPlayer().setHealth(20);
                ju2.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                ju2.sendMessage(Lang.getInst().playerHelpedMessage.replace("{PLAYER}", ju.getName()));
                if(DeathUtil.isDead(ju2.getPlayer())) {
                    if(BarAPI.hasBar(ju2.getPlayer())) BarAPI.removeBar(ju2.getPlayer());
                    DeathUtil.removeDeath(ju2.getPlayer());
                }
            }

            if(e.getCurrentItem().getType().equals(Material.NAME_TAG)){
                ((Player)e.getWhoClicked()).closeInventory();
                IDUtil.showID((Player)e.getWhoClicked(), ju2);
            }

            if(e.getCurrentItem().getType().equals(Material.BOOK)){
                IDUtil.showJob((Player) e.getWhoClicked(), ju2);
            }

            if(e.getCurrentItem().getType().equals(Material.CHEST)){
                ((Player)e.getWhoClicked()).openInventory(ju.getCheeked().getInventory());
            }

        }
    }

}
