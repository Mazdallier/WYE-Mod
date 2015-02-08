package nz.co.crookedhill.wyem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.item.WYEMItemCrown;

public class EntityAINearestModified extends EntityAITarget
{
	private EntityCreature owner;
	private String helmetName;
    private final Class targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
    private static final String __OBFID = "CL_00001620";

    public EntityAINearestModified(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_, String hemletName)
    {
        this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
        this.helmetName = hemletName;
    }

    public EntityAINearestModified(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_)
    {
        this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (IEntitySelector)null);
    }

    public EntityAINearestModified(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, final IEntitySelector p_i1665_6_)
    {
        super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
        this.owner = p_i1665_1_;
        this.targetClass = p_i1665_2_;
        this.targetChance = p_i1665_3_;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i1665_1_);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector()
        {
            private static final String __OBFID = "CL_00001621";
            /**
             * Return whether the specified entity is applicable to this filter.
             */
            public boolean isEntityApplicable(Entity p_82704_1_)
            {
                return !(p_82704_1_ instanceof EntityLivingBase) ? false : (p_i1665_6_ != null && !p_i1665_6_.isEntityApplicable(p_82704_1_) ? false : EntityAINearestModified.this.isSuitableTarget((EntityLivingBase)p_82704_1_, false));
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
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);
            if (list.isEmpty())
            {
                return false;
            }
            else
            {
            	for(int i = 0; i < list.size(); i++)
            	{
            		if(list.get(i) instanceof EntityPlayer)
            		{
        				System.out.println("I am an anoying message that is alwasy there!");

            			EntityPlayer player = (EntityPlayer)list.get(i);
            			ItemStack itemstack = player.inventory.armorInventory[3];
            			if(itemstack != null && itemstack.getItem() instanceof WYEMItemCrown)
            			{
            				if(((WYEMItemCrown)itemstack.getItem()).friendlyString.equals(helmetName))
            				{
            					list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, ((EntityPlayer)list.get(i)).boundingBox.expand(d0, 4.0D, d0));
            					for(int j = 0; j < list.size(); j++)
            					{
            						if(list.get(j).equals(player))
            						{
            							list.remove(j);
            							break;
            						}
            					}
            					loopEntities(list);
                	            Collections.sort(list, this.theNearestAttackableTargetSorter);
            					break;
            				}
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
    private List loopEntities(List list)
    {
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(list.get(i) instanceof EntityAnimal)
    		{
    			list.remove(i);
    		}
    		else if(list.get(i).getClass().equals(this.owner.getClass()))
    		{
    			list.remove(i);
    		}
    		else if(list.get(i) instanceof EntityVillager)
    		{
    			list.remove(i);
    		}
    	}
    	return list;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public static class Sorter implements Comparator
        {
            private final Entity theEntity;
            private static final String __OBFID = "CL_00001622";

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
