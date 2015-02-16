package nz.co.crookedhill.wyem.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.item.WYEMItem;
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
				for(ItemStack item : mc.thePlayer.inventory.armorInventory)
				{
					if(item != null && item.getItem() == WYEMItem.spiderTreads)
					{
						boolean isCollided = mc.thePlayer.isCollidedHorizontally;
						if(isCollided)
						{
							mc.thePlayer.motionY = 0.065555559;
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
}
