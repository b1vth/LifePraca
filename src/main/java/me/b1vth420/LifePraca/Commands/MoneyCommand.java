package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand extends Command{
    public MoneyCommand() {
        super("money", null, "LifePraca.money", "/money", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatUtil.chat(Lang.getInst().accountBalanceMessage.replace("{MONEY}", ChatUtil.formatDouble(JobUser.get((Player) sender).getMoney()))));
    }
}
