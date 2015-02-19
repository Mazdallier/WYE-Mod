package nz.co.crookedhill.wyem.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nz.co.crookedhill.wyem.item.WYEMItem;

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
		public int prevY;

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
						mc.thePlayer.motionY = 0.065555559;
					}
					else
					{
						
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
