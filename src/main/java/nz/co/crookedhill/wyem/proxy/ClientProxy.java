package nz.co.crookedhill.wyem.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.WYEM;
import nz.co.crookedhill.wyem.item.WYEMItem;
import nz.co.crookedhill.wyem.network.DamageMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
	public void init() 
	{
		FMLCommonHandler.instance().bus().register(this.new TickEvents(Minecraft.getMinecraft()));
	}


	@SideOnly(value=Side.CLIENT)
	public class TickEvents
	{
		public boolean isCollided = false;
		int movedBlocks = 0;
		int[] shouldDamage = {0,0};
		public double prevY;
		public double currY;
		Minecraft mc;
		public TickEvents(Minecraft mc)
		{
			this.mc = mc;
		}

		@SubscribeEvent
		public void onClientTick(ClientTickEvent event)
		{
			if(mc.currentScreen == null)
			{
				ItemStack boots =  mc.thePlayer.inventory.armorInventory[0];
				if(boots != null && boots.getItem() == WYEMItem.spiderTreads)
				{
					isCollided = mc.thePlayer.isCollidedHorizontally;
					if(isCollided)
					{
						shouldDamage[0] = 1;
						currY += prevY - currY;
						mc.thePlayer.motionY = 0.065555559;
					}
					else
					{
						shouldDamage[1] = 1;
						movedBlocks = (int)Math.abs(currY - prevY);
						prevY = mc.thePlayer.posY;
					}
					if(!isCollided && movedBlocks > 0 && shouldDamage[0] == 1 & shouldDamage[1] == 1)
					{
						shouldDamage[0] = 0; shouldDamage[1] = 0;
						WYEM.network.sendToServer(new DamageMessage(movedBlocks));
						//boots.damageItem(movedBlocks, mc.thePlayer);
						System.out.println(movedBlocks);
						movedBlocks = 0;
					}
					if(isCollided && mc.thePlayer.isSneaking())
					{
						mc.thePlayer.motionY = 0;
					}
				}
			}
		}
	}
}
