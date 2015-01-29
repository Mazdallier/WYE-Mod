package nz.co.crookedhill.wyem.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		/* Register Items */
		GameRegistry.registerItem(skeletonCrown = new WYEMItemArmor("skeleton_crown", WYEM.MATERIAL, "tutorial_1", 0), "skeleton_helmet"); //0 for helmet
		GameRegistry.registerItem(creeperCrown = new WYEMItemArmor("creeper_crown", WYEM.MATERIAL, "tutorial_1", 0), "creeper_helmet"); //0 for helmet
		GameRegistry.registerItem(zombieCrown = new WYEMItemArmor("zombie_crown", WYEM.MATERIAL, "tutorial_1", 0), "zombie_helmet"); //0 for helmet

		GameRegistry.registerItem(enderChestplate = new WYEMItemArmor("ender_chestplate", WYEM.MATERIAL, "enderChestplate", 1), "ender_chestplate"); // 1 for chestplate
		GameRegistry.registerItem(spiderTreads = new WYEMItemArmor("spider_treads", WYEM.MATERIAL, "spiderBoots", 3), "spider_boots"); // 3 for boots
		
		/* Register Recipes */
		GameRegistry.addRecipe(new ItemStack(WYEMItem.spiderTreads), "i i","ses",'i', Items.iron_ingot, 's', Items.string, 'e', Items.spider_eye);
		GameRegistry.addRecipe(new ItemStack(WYEMItem.enderChestplate), "i i", "gpg", "igi", 'i', Items.iron_ingot, 'p', Items.ender_pearl, 'g', Items.gold_ingot);
		
		GameRegistry.addRecipe(new ItemStack(WYEMItem.creeperCrown), "g g", "ihi", 'g', Items.gold_ingot, 'i', Items.iron_ingot, 'h', new ItemStack(Items.skull, 1, 4));
		GameRegistry.addRecipe(new ItemStack(WYEMItem.zombieCrown), "g g", "ihi", 'g', Items.gold_ingot, 'i', Items.iron_ingot, 'h', new ItemStack(Items.skull, 1, 2));
		GameRegistry.addRecipe(new ItemStack(WYEMItem.skeletonCrown), "g g", "ihi", 'g', Items.gold_ingot, 'i', Items.iron_ingot, 'h', new ItemStack(Items.skull, 1, 0));
	}
}
