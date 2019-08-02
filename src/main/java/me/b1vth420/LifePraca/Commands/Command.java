package me.b1vth420.LifePraca.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class Command
  extends org.bukkit.command.Command
{
  private String name;
  private String description;
  private String permission;
  private String usage;
  private String[] aliases;
  private boolean onlyPlayer;
  private int argsMinLength;
  
  public Command(String name, String description, String permission, String usage, boolean onlyPlayer, int argsMinLength, String... aliases)
  {
    super(name, description == null ? "" : description, ChatColor.DARK_RED + "Poprawne uzycie: " + ChatColor.RED + (usage == null ? "/" + name : usage), Arrays.asList(aliases));
    
    this.name = name;
    this.description = (description == null ? "" : description);
    this.permission = permission;
    this.usage = (ChatColor.DARK_RED + "Poprawne uzycie: " + ChatColor.RED + (usage == null ? "/" + name : usage));
    this.aliases = aliases;
    this.argsMinLength = argsMinLength;
    this.onlyPlayer = onlyPlayer;
  }
  
  public boolean execute(CommandSender sender, String label, String[] args)
  {
    if ((this.permission != null) && (!sender.hasPermission(this.permission)))
    {
      sender.sendMessage(ChatColor.RED + "Nie masz uprawnien! (" + this.permission +")");
      return false;
    }
    if ((this.onlyPlayer) && (!(sender instanceof Player)))
    {
      sender.sendMessage(ChatColor.RED + "Musisz byc graczem zeby tego uzyc!");
      return false;
    }
    if (args.length < this.argsMinLength)
    {
      sender.sendMessage(this.usage);
      return false;
    }
    execute(sender, args);
    return false;
  }
  
  public abstract void execute(CommandSender paramCommandSender, String[] paramArrayOfString);
  
  public String getName()
  {
    return this.name;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getPermission()
  {
    return this.permission;
  }
  
  public String getUsage()
  {
    return this.usage;
  }
  
  public List<String> getAliases()
  {
    return Arrays.asList(this.aliases);
  }
  
  public String[] getAliasesTable()
  {
    return this.aliases;
  }
  
  public boolean isOnlyPlayer()
  {
    return this.onlyPlayer;
  }
  
  public int getArgsMinLength()
  {
    return this.argsMinLength;
  }
}
