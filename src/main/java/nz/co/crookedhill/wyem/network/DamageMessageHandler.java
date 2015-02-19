package nz.co.crookedhill.wyem.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DamageMessageHandler implements IMessageHandler<DamageMessage, IMessage>
{
	@Override
	public IMessage onMessage(DamageMessage message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.inventory.armorInventory[0].damageItem(message.damageAmount, ctx.getServerHandler().playerEntity);
		return null;
	}
}
