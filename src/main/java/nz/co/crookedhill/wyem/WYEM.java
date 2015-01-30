package nz.co.crookedhill.wyem;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import nz.co.crookedhill.wyem.item.WYEMItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = WYEM.MODID, version = WYEM.VERSION)
public class WYEM
{
	public static final String MODID = "wyem";
	public static final String VERSION = "$VERSION$";

	public static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wyeMaterial", 15, new int[] {1, 3, 2, 1}, 25);
	public static WYEMCreativeTab wyemTab = new WYEMCreativeTab("WYEM");
	
	/* static so its not created over and over again */
	static Random rand = new Random(System.currentTimeMillis());

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WYEMItem.init();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttackedEvent(LivingAttackEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && event.source.damageType == "arrow")
		{
			teleportFromArrow(event);
		}	
	}
	
	private void teleportFromArrow(LivingAttackEvent event)
	{
		if(rand.nextFloat() <= 0.1f)
		{
			event.setCanceled(true);
			event.source.getSourceOfDamage().setDead();
			/* this line exists because .setDead doesnt hapen streat away, so the player can still get damaged */
			event.source.getSourceOfDamage().setPosition(event.source.getSourceOfDamage().posX, event.source.getSourceOfDamage().posY+1000.0d, event.source.getSourceOfDamage().posZ);
			
			
			double xpos = ((event.entityLiving.posX-5.0d) + (double)rand.nextInt(10));
			double ypos = ((event.entityLiving.posY-5.0d) + (double)rand.nextInt(10));
			double zpos = ((event.entityLiving.posZ-5.0d) + (double)rand.nextInt(10));
			while(!event.entity.worldObj.isAirBlock((int)xpos, (int)ypos, (int)zpos))
			{
				ypos += 1.0d;
			}
			((EntityPlayer)event.entity).setPositionAndUpdate(xpos, ypos, zpos);
			int player = 5;
			Object object = new Object();
		}
	}
}
