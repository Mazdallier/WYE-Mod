package nz.co.crookedhill.wyem;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class WYEMConfigHelper 
{
	static Configuration config = new Configuration();
	public static double enderTeleportChance;
	public static int enderTeleportDistance;
	
	public static void init(File file){
		config = new Configuration(file);
		try{
			config.load();
			enderTeleportChance = config.get("Ender Chestplate", "TeleportChance", 0.1D, "The percentage chance you will teleport when attacked with an arrow, 0.1=10%, 1.0=100%", 0.0D, 1.0D).getDouble();
			enderTeleportDistance = config.getInt("Max Teleport Distance", "Ender Chestplate", 5, 0, 100, "The maximum distance you will travel when teleported by the ender chestplate.");
		}
		catch(Exception e){
			System.out.println("Config shat itself.");
		}
		finally{
			config.save();
		}
	}
}
