package nz.co.crookedhill.wyem.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class DamageMessageHandler implements IMessageHandler<DamageMessage, IMessage>
{
	@Override
	public IMessage onMessage(DamageMessage message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.inventory.armorInventory[0].damageItem(message.damageAmount, ctx.getServerHandler().playerEntity);
		return null;
	}
}
