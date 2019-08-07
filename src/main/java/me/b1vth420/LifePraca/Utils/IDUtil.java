package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
public class IDUtil {

    public static void showID(Player p, JobUser ju){
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        String ss[] = Main.getInst().getSQLManager().getInfo(ju.getPlayer()).split(" ");
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("dowod", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatUtil.chat("&cDowod"));
        Score sc4 = obj.getScore(ChatUtil.chat("&7Data: &c" + ss[4]));
        Score sc3 = obj.getScore(ChatUtil.chat("&7Plec: &c" + ss[3]));
        Score sc2 = obj.getScore(ChatUtil.chat("&7Wiek: &c" + ss[2]));
        Score sc1 = obj.getScore(ChatUtil.chat("&7ID: &c" + ss[1]));
        Score sc = obj.getScore(ChatUtil.chat("&7Imie: &c" + ss[0]));
        sc4.setScore(7);
        sc3.setScore(3);
        sc2.setScore(1);
        sc1.setScore(2);
        sc.setScore(0);
        p.setScoreboard(sb);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }, 20*10L);
    }

    public static void shopJob(Player p, JobUser ju){
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("praca", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatUtil.chat("&cPraca"));
        Score sc = obj.getScore(ChatUtil.chat("&7Praca: &c" + ju.getJob().getName()));
        sc.setScore(0);
        p.setScoreboard(sb);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }, 20*10L);
    }

}
