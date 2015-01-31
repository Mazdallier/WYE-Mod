package nz.co.crookedhill.wyem;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import nz.co.crookedhill.wyem.item.WYEMItem;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WYEMEventHandler 
{
	/* static so its not created over and over again */
	static Random rand = new Random(System.currentTimeMillis());

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttackedEvent(LivingAttackEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && event.source.isProjectile())
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			for(ItemStack item : player.inventory.armorInventory)
			{
				if(item != null && item.getItem() == WYEMItem.enderChestplate && rand.nextFloat() < WYEMConfigHelper.enderTeleportChance) // 10% chance to cancel and teleport.
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
				if(item != null && item.getItem() == WYEMItem.enderChestplate)
				{
					teleportFromDamage((EntityPlayer)event.entity);
					item.attemptDamageItem(3, rand);
					break;
				}
			}
		}
		else if(event.entity instanceof EntityPlayer && event.source.getDamageType() == "fall")
		{
			for(ItemStack item : ((EntityPlayer)event.entity).inventory.armorInventory)
			{
				if(item.getItem() == WYEMItem.spiderTreads)
				{
					event.ammount -= (event.ammount * WYEMConfigHelper.spiderDamageReduction);
					item.attemptDamageItem(3, rand);
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public void onDeathEvent(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer && ((EntityPlayer)event.source.getEntity()).getHeldItem().getItem() == WYEMItem.headCollector)
		{
			if(rand.nextDouble() <= WYEMConfigHelper.headCollectorChance)
			{
				if(event.entity instanceof EntitySkeleton)
				{
					EntitySkeleton skele = (EntitySkeleton)event.entity;
					/* is normal skeleton */
					if(((EntitySkeleton)event.entity).getSkeletonType() == 0)
					{
						EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 0));
						event.entity.worldObj.spawnEntityInWorld(item);
					}
					/* is wither skeleton */
					if(((EntitySkeleton)event.entity).getSkeletonType() == 1)
					{
						EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 1));
						event.entity.worldObj.spawnEntityInWorld(item);
					}
				}
				else if(event.entity instanceof EntityZombie)
				{
					EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 2));
					event.entity.worldObj.spawnEntityInWorld(item);
				}
				else if(event.entity instanceof EntityCreeper)
				{
					EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 4));
					event.entity.worldObj.spawnEntityInWorld(item);
				}
				else if(event.entity instanceof EntityPlayer)
				{
					EntityItem item = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(Items.skull, 1, 3));
					if(!item.getEntityItem().hasTagCompound())
					{
						item.getEntityItem().stackTagCompound = new NBTTagCompound();
					}
					item.getEntityItem().getTagCompound().setString("SkullOwner", ((EntityPlayer)event.source.getEntity()).getDisplayName());
					event.entity.worldObj.spawnEntityInWorld(item);
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
