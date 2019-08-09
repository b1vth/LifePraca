package me.b1vth420.LifePraca.Listeners.Inventory;

import com.google.common.collect.Lists;
import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.DataUtil;
import me.b1vth420.LifePraca.Utils.IDUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PolicjantInventoryClickListener implements Listener{

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        JobUser ju = JobUser.get(p);

        if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;

        if (e.getClickedInventory().getName().equals(ChatUtil.chat("&aMenu policyjne"))) {
            e.setCancelled(true);
            JobUser ju2 = JobUser.get(ju.getCheeked());

            if (e.getCurrentItem().getType().equals(Material.IRON_FENCE)) {
                if (JobUserManager.isFreezed(ju.getCheeked())) {
                    JobUserManager.removeFreeze(ju.getCheeked());
                    p.sendMessage(ChatUtil.chat(Lang.getInst().playerUnFrezedMessage.replace("{PLAYER}", ju2.getName())));
                } else {
                    JobUserManager.addFreeze(ju.getCheeked());
                    p.sendMessage(ChatUtil.chat(Lang.getInst().playerFrezedMessage.replace("{PLAYER}", ju2.getName())));
                }
            }

            if (e.getCurrentItem().getType().equals(Material.GOLD_INGOT)) {
                p.closeInventory();
                Main.getInst().getSignMenuFactory()
                        .newMenu(p, Lists.newArrayList("&a&lExample", "&4&lSign"))
                        .reopenIfFail()
                        .response((player, strings) -> {
                            if (strings[3].equals("Potwierdz") && strings[0] != null && strings[1] != null) {
                                p.sendMessage(ChatUtil.chat(Lang.getInst().finePolicemanMessage
                                                .replace("{PLAYER}", ju2.getName())
                                                .replace("{REASON1}",(strings[1].isEmpty() ? strings[1] : ""))
                                                .replace("{REASON2}", (strings[2].isEmpty() ? strings[2] : ""))));
                                ju2.sendMessage(Lang.getInst().finePlayerMessage
                                        .replace("{PLAYER}", p.getName())
                                        .replace("{REASON1}",(strings[1].isEmpty() ? strings[1] : ""))
                                        .replace("{REASON2}", (strings[2].isEmpty() ? strings[2] : ""))
                                        .replace("{FINE}", ChatUtil.formatDouble(Double.parseDouble(strings[0]))));
                                ju2.setMoney(ju2.getMoney() - Double.parseDouble(strings[0]));
                                Main.getInst().getSQLManager().updateLog(ju, "MANDAT", "Gracz " + ju2.getName() + " otrzymal mandat ("
                                        + strings[0] + "$) za "
                                        + (strings[1].isEmpty() ? strings[1] : "")
                                        + (strings[2].isEmpty() ? strings[2] : ""));
                                return true;
                            }
                            return false;
                        })
                        .open();
            }

            if(e.getCurrentItem().getType().equals(Material.POISONOUS_POTATO)){
                Main.getInst().getSignMenuFactory()
                        .newMenu(p, Lists.newArrayList("&a&lExample", "&4&lSign"))
                        .reopenIfFail()
                        .response((player, strings) -> {
                            if (strings[3].equals("Potwierdz") && strings[0] != null && strings[1] != null) {
                                p.sendMessage(ChatUtil.chat(Lang.getInst().mutePolicemanMessage
                                        .replace("{PLAYER}", p.getName())
                                        .replace("{REASON1}",(strings[1].isEmpty() ? strings[1] : ""))
                                        .replace("{REASON2}", (strings[2].isEmpty() ? strings[2] : ""))
                                        .replace("{TIME}", DataUtil.getDate(DataUtil.parseDateDiff(strings[0], true)))));
                                ju2.sendMessage(Lang.getInst().mutePlayerMessage
                                        .replace("{PLAYER}", p.getName())
                                        .replace("{REASON1}",(strings[1].isEmpty() ? strings[1] : ""))
                                        .replace("{REASON2}", (strings[2].isEmpty() ? strings[2] : ""))
                                        .replace("{TIME}", DataUtil.getDate(DataUtil.parseDateDiff(strings[0], true))));
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mute " + ju2.getName() + " " + strings[0]);
                                Main.getInst().getSQLManager().updateLog(ju, "MUTE", "Gracz " + ju2.getName() + " zostal zmutowany na " + strings[0] + " za "
                                        + (strings[1].isEmpty() ? strings[1] : "")
                                        + (strings[2].isEmpty() ? strings[2] : ""));
                                return true;
                            }
                            return false;
                        })
                        .open();
            }

            if(e.getCurrentItem().getType().equals(Material.TRAP_DOOR)){
                Main.getInst().getSignMenuFactory()
                        .newMenu(p, Lists.newArrayList("&a&lExample", "&4&lSign"))
                        .reopenIfFail()
                        .response((player, strings) -> {
                            if (strings[3].equals("Potwierdz") && strings[0] != null) {
                                p.sendMessage(ChatUtil.chat("&aWyrzuciles gracza " + ju2.getName() + " za " + strings[0] + (strings[1].isEmpty() ? strings[1] : "") + (strings[2].isEmpty() ? strings[2] : "")));
                                p.kickPlayer(ChatUtil.chat(
                                        "&4Zostales wyrzucony z serwera "
                                        + (strings[0].isEmpty() ? "\n" + strings[0] : "")
                                        + (strings[1].isEmpty() ? "\n" + strings[1] : "")
                                        + (strings[2].isEmpty() ? "\n" + strings[2] : "")));
                                Main.getInst().getSQLManager().updateLog(ju, "KICK", "Gracz " + ju2.getName() + " zostal wyrzucony za "
                                        + (strings[0].isEmpty() ? strings[0] : "")
                                        + (strings[1].isEmpty() ? strings[1] : "")
                                        + (strings[2].isEmpty() ? strings[2] : ""));
                                return true;
                            }
                            return false;
                        })
                        .open();
            }

            if(e.getCurrentItem().getType().equals(Material.NAME_TAG)){
                IDUtil.showID(p, ju2);
            }

            if(e.getCurrentItem().getType().equals(Material.BOOK)){
                IDUtil.showJob(p, ju2);
            }

            if(e.getCurrentItem().getType().equals(Material.CHEST)){
                p.openInventory(ju.getCheeked().getInventory());
            }
        }
    }
}
