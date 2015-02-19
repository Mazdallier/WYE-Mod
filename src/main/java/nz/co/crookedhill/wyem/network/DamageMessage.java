package nz.co.crookedhill.wyem.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class DamageMessage implements IMessage
{
	int damageAmount;
	public DamageMessage() {	}
	
	public DamageMessage(int amount)
	{
		this.damageAmount = amount;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.damageAmount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.damageAmount);
		
	}

}
