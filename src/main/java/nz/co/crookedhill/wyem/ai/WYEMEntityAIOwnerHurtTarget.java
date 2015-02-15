package nz.co.crookedhill.wyem.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WYEMEntityAIOwnerHurtTarget extends EntityAITarget
{

	EntityLivingBase taskOwner;
	EntityLivingBase king;
	Item friendlyHelmet;
	EntityLivingBase theTarget;
	private int field_142050_e;
	private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;

	public WYEMEntityAIOwnerHurtTarget(EntityCreature owner, Item crown)
	{
		super(owner, false);
		this.taskOwner = owner;
		this.friendlyHelmet = crown;
		this.setMutexBits(1);
		this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(owner);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		List<EntityPlayer> list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.boundingBox.expand(16, 4.0D, 16));
		EntityPlayer[] players = new EntityPlayer[list.size()];
		players = list.toArray(players);

		for(EntityPlayer player : players)
		{
			if(player.inventory.armorInventory[3] == null || player.inventory.armorInventory[3].getItem() != this.friendlyHelmet)
			{
				list.remove(player);
			}
		}
		if(list.size() > 0)
		{
			Collections.sort(list, this.theNearestAttackableTargetSorter);
			players = list.toArray(players);
			for(EntityPlayer player : players)
			{
				if(player.getLastAttacker() == null)
				{
					continue;
				}
				this.theTarget = player.getLastAttacker();
				int i = player.getLastAttacker().getLastAttackerTime();
				return i != this.field_142050_e && this.isSuitableTarget(this.theTarget, false);
			}
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		((EntityCreature)this.taskOwner).setAttackTarget(this.theTarget);
		System.out.println("attacking: " + this.theTarget.toString());
		EntityLivingBase entitylivingbase = this.king;

		if (entitylivingbase != null)
		{
			this.field_142050_e = entitylivingbase.getLastAttackerTime();
		}

		super.startExecuting();
	}

	public static class Sorter implements Comparator
	{
		private final Entity theEntity;

		public Sorter(Entity p_i1662_1_)
		{
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity p_compare_1_, Entity p_compare_2_)
		{
			double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
			double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		public int compare(Object p_compare_1_, Object p_compare_2_)
		{
			return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
		}
	}


}
