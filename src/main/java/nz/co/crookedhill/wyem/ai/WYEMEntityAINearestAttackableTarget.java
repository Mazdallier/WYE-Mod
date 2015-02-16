package nz.co.crookedhill.wyem.ai;

/*
 * this class is an edited version of EntityAINearestAttackableTarget.
 * it functions as per normal, but removed any player from the possible
 * attackees that is wearing the right MobCrown.
 */

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
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.item.WYEMItemCrown;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class WYEMEntityAINearestAttackableTarget extends EntityAITarget
{
	private EntityCreature owner;
	private Item crown;
    private final Class targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private final Predicate targetEntitySelector;
    private EntityLivingBase targetEntity;

    public WYEMEntityAINearestAttackableTarget(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_, Item crown)
    {
        this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
        this.crown = crown;
    }

    public WYEMEntityAINearestAttackableTarget(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_)
    {
        this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (Predicate)null);
    }

    public WYEMEntityAINearestAttackableTarget(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, final Predicate p_i45880_6_)
    {
        super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
        this.owner = p_i1665_1_;
        this.targetClass = p_i1665_2_;
        this.targetChance = p_i1665_3_;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i1665_1_);
        this.setMutexBits(1);
        this.targetEntitySelector = new Predicate()
        {
            private static final String __OBFID = "CL_00001621";
            public boolean func_179878_a(EntityLivingBase p_179878_1_)
            {
                if (p_i45880_6_ != null && !p_i45880_6_.apply(p_179878_1_))
                {
                    return false;
                }
                else
                {
                    if (p_179878_1_ instanceof EntityPlayer)
                    {
                        double d0 = WYEMEntityAINearestAttackableTarget.this.getTargetDistance();

                        if (p_179878_1_.isSneaking())
                        {
                            d0 *= 0.800000011920929D;
                        }

                        if (p_179878_1_.isInvisible())
                        {
                            float f = ((EntityPlayer)p_179878_1_).getArmorVisibility();

                            if (f < 0.1F)
                            {
                                f = 0.1F;
                            }

                            d0 *= (double)(0.7F * f);
                        }

                        if ((double)p_179878_1_.getDistanceToEntity(WYEMEntityAINearestAttackableTarget.this.taskOwner) > d0)
                        {
                            return false;
                        }
                    }

                    return WYEMEntityAINearestAttackableTarget.this.isSuitableTarget(p_179878_1_, false);
                }
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.func_179878_a((EntityLivingBase)p_apply_1_);
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            double d0 = this.getTargetDistance();
            // func_175647_a = selectEntitiesWithinAABB
            List list = this.taskOwner.worldObj.func_175647_a(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, IEntitySelector.NOT_SPECTATING));
            Collections.sort(list, this.theNearestAttackableTargetSorter);
            if (list.isEmpty())
            {
                return false;
            }
            else
            {
            	for(Object entities : list)
            	{
            		if(entities instanceof EntityPlayer)
            		{
            			EntityPlayer player = (EntityPlayer)entities;
            			ItemStack itemstack = player.inventory.armorInventory[3];
            			if(itemstack != null && itemstack.getItem() == this.crown)
            			{
            				//list.remove(player);
            				List toAttack = getCrownsEnemies(list);
            				if(toAttack.isEmpty())
            				{
            					return false;
            				}
            				this.targetEntity = (EntityLivingBase)toAttack.get(0);
            				return true;
            			}
            		}
            	}
            	if(list.size() > 0)
            	{
            		this.targetEntity = (EntityLivingBase)list.get(0);
            		return true;
            	}
            	return false;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	if(this.targetEntity != null && this.targetEntity instanceof EntityPlayer &&((EntityPlayer)this.targetEntity).inventory.armorInventory[3] != null && ((EntityPlayer)this.targetEntity).inventory.armorInventory[3].getItem() instanceof WYEMItemCrown && ((EntityPlayer)this.targetEntity).inventory.armorInventory[3].getItem() == this.crown)
    	{
    		this.taskOwner.setAttackTarget(null);
    	}
        this.taskOwner.setAttackTarget(this.targetEntity);
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
    private List getCrownsEnemies(List list)
    {
        double dis = this.getTargetDistance();
    	list = this.taskOwner.worldObj.getEntitiesWithinAABBExcludingEntity(this.taskOwner, this.taskOwner.getEntityBoundingBox().expand(dis, 4.0D, dis));
    	Iterator itr = list.iterator();
    	while(itr.hasNext())
    	{
    		Object entity = itr.next();
    		if(entity instanceof EntityPlayer)
    		{
    			if(((EntityPlayer)entity).inventory.armorInventory[3] != null && ((EntityPlayer)entity).inventory.armorInventory[3].getItem() == this.crown)
    			{
    				itr.remove();
    			}
    		}
    		else if(entity instanceof EntityAnimal)
    		{
    			itr.remove();
    		}
    		else if(entity instanceof EntityVillager)
    		{
    			itr.remove();
    		}
    		else if(!(entity instanceof EntityLivingBase))
    		{
    			itr.remove();
    		}
    		else if(entity.getClass() == this.taskOwner.getClass())
    		{
    			if(entity instanceof EntitySkeleton)
    			{
    				/* if its a different type of skeleton, attack it. otherwise remove it from the list */
    				if(((EntitySkeleton)entity).getSkeletonType() != ((EntitySkeleton)this.taskOwner).getSkeletonType())
    				{
    					continue;
    				}
    			}
    			itr.remove();
    		}
    	}
    	Collections.sort(list, this.theNearestAttackableTargetSorter);
    	return list;
    }
}
