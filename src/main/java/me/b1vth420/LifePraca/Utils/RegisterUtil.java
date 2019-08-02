package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;

public class RegisterUtil
{
  private static final String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
  @SuppressWarnings("rawtypes")
  private static Class craftServerClass;
  private static SimpleCommandMap commandMap;
  
  static
  {
    try
    {
      craftServerClass = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + ".CraftServer");
      Field f = craftServerClass.getDeclaredField("commandMap");
      f.setAccessible(true);
      commandMap = (SimpleCommandMap)f.get(craftServerClass.cast(Main.getInst().getServer()));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void registerCommand(Command cmd)
  {
    commandMap.register(cmd.getName(), cmd);
  }
  
  public SimpleCommandMap getCommandMap()
  {
    return commandMap;
  }
}

