package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SpawnNpcCommand extends Command{
    public SpawnNpcCommand() {
        super("spawnnpc", null, "LifePraca.NPC", "/spawnnpc", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        v.setProfession(Villager.Profession.NITWIT);
        v.setInvulnerable(true);
        v.setAgeLock(true);
        v.setCanPickupItems(false);
        v.setCustomName("Praca");
        v.setAge(40);

        p.sendMessage(ChatUtil.chat("&aStworzyles villagera"));
        p.teleport(p.getWorld().getSpawnLocation());
    }
}
