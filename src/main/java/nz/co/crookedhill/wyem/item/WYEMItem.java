package nz.co.crookedhill.wyem.item;

import net.minecraft.item.Item;
import nz.co.crookedhill.wyem.WYEM;
import cpw.mods.fml.common.registry.GameRegistry;

public class WYEMItem 
{
	public static Item skeletonCrown;
	public static Item creeperCrown;
	public static Item zombieCrown;
	public static Item enderChestplate;
	public static Item spiderTreads;
	
	public static void init()
	{		
		GameRegistry.registerItem(skeletonCrown = new WYEMItemArmor("skeleton_crown", WYEM.MATERIAL, "tutorial_1", 0), "skeleton_helmet"); //0 for helmet
		GameRegistry.registerItem(creeperCrown = new WYEMItemArmor("creeper_crown", WYEM.MATERIAL, "tutorial_1", 0), "creeper_helmet"); //0 for helmet
		GameRegistry.registerItem(zombieCrown = new WYEMItemArmor("zombie_crown", WYEM.MATERIAL, "tutorial_1", 0), "zombie_helmet"); //0 for helmet

		GameRegistry.registerItem(enderChestplate = new WYEMItemArmor("ender_chestplate", WYEM.MATERIAL, "enderChestplate", 1), "ender_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(spiderTreads = new WYEMItemArmor("spider_treads", WYEM.MATERIAL, "spiderBoots", 3), "spider_boots"); // 3 for boots
	}
}
