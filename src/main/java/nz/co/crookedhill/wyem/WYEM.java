package nz.co.crookedhill.wyem;

import java.io.File;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import nz.co.crookedhill.wyem.item.WYEMItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	public void preInit(FMLPreInitializationEvent event)
	{
		WYEMConfigHelper.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + WYEM.MODID + File.separator + WYEM.MODID + ".cfg"));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WYEMItem.init();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttackedEvent(LivingAttackEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && event.source.isProjectile())
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			for(ItemStack item : player.inventory.armorInventory)
			{
				if(item == null)
				{
					continue;
				}
				
				if(item.getItem() == WYEMItem.enderChestplate && rand.nextFloat() < WYEMConfigHelper.enderTeleportChance) // 10% chance to cancel and teleport.
				{
					event.setCanceled(true);
					event.source.getSourceOfDamage().setDead();
					/* this line exists because .setDead doesnt hapen streat away, so the player can still get damaged */
					event.source.getSourceOfDamage().setPosition(event.source.getSourceOfDamage().posX, event.source.getSourceOfDamage().posY+1000.0d, event.source.getSourceOfDamage().posZ);
					teleportFromDamage((EntityPlayer)event.entity);
					item.attemptDamageItem(3, rand);
					break;
				}
			}
		}	
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHurtEvent(LivingHurtEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && event.entity.handleLavaMovement())
		{
			for(ItemStack item : ((EntityPlayer) event.entity).inventory.armorInventory)
			{
				if(item == null)
				{
					continue;
				}
				if(item.getItem() == WYEMItem.enderChestplate)
				{
					teleportFromDamage((EntityPlayer)event.entity);
					item.attemptDamageItem(3, rand);
					break;
				}
			}
		}
	}

	private static void teleportFromDamage(EntityPlayer player)
	{
		double xpos = ((player.posX-5.0d) + (double)rand.nextInt(10));
		double ypos = ((player.posY-5.0d) + (double)rand.nextInt(10));
		double zpos = ((player.posZ-5.0d) + (double)rand.nextInt(10));
		while(!player.worldObj.isAirBlock((int)xpos, (int)ypos, (int)zpos))
		{
			ypos += 1.0d;
		}
		player.setPositionAndUpdate(xpos, ypos, zpos);

	}
}
