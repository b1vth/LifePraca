package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Gui.MedykGui;
import me.b1vth420.LifePraca.Gui.PolicjantGui;
import me.b1vth420.LifePraca.Gui.PracaGui;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.DeathUtil;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractEntityListener implements Listener{
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Villager){
            Villager v = (Villager) e.getRightClicked();
            if(v.getName().equals("Praca")){
                e.setCancelled(true);
                PracaGui.jobsTypesx(e.getPlayer());
            }
        }

        if(e.getRightClicked() instanceof Player){
            Player p = (Player) e.getPlayer();
            JobUser ju = JobUser.get(e.getPlayer());

            if(!ju.hasJob()) return;

            ItemStack palka = new ItemStack(Material.STICK);
            ItemMeta im = palka.getItemMeta();
            im.setDisplayName(ChatUtil.chat("&4Palka policyjna"));
            palka.setItemMeta(im);

            if(DeathUtil.isSleeping((Player) e.getRightClicked())){
                p.openInventory(((Player) e.getRightClicked()).getInventory());
            }
            if(ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("medyk")){
                ju.setCheeked((Player) e.getRightClicked());
                MedykGui.medykGui(p);
            }

            if (ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("policjant") && e.getPlayer().getItemInHand().equals(palka)) {
                ju.setCheeked((Player) e.getRightClicked());
                PolicjantGui.policjantGui(p);
            }
        }
    }
}
