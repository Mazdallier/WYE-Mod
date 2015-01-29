package nz.co.crookedhill.wyem;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import nz.co.crookedhill.wyem.item.WYEMItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = WYEM.MODID, version = WYEM.VERSION)
public class WYEM
{
    public static final String MODID = "wyem";
    public static final String VERSION = "$VERSION$";
    
    public static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wyeMaterial", 15, new int[] {1, 3, 2, 1}, 25);
    public static WYEMCreativeTab wyemTab = new WYEMCreativeTab("WYEM");
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		WYEMItem.init();
		MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onAttackedEvent(LivingAttackEvent event)
    {
    	/* if the attackee is a player and wearing the ender chestplate */
    	if(event.entityLiving instanceof EntityPlayer && Arrays.asList(((EntityPlayer)event.entityLiving).inventory.armorInventory).contains(WYEMItem.enderChestplate) && event.source.isProjectile())
    	{
    		Random rand = new Random();
    		//if(rand.nextFloat() <= 0.1f)
    		//{
    			event.setCanceled(true);
    			event.entityLiving.posX = (event.entityLiving.posX-5d) + rand.nextDouble()*10d;
    			event.entityLiving.posY = (event.entityLiving.posY-5d) + rand.nextDouble()*10d;
    			event.entityLiving.posZ = (event.entityLiving.posZ-5d) + rand.nextDouble()*10d;
    		//}
    	}
    }
}
