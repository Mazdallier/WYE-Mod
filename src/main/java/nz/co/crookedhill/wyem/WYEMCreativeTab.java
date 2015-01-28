package nz.co.crookedhill.wyem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class WYEMCreativeTab extends CreativeTabs
{

	public WYEMCreativeTab(String label) 
	{
		super(label);
	}

	@Override
	public Item getTabIconItem() 
	{
		return Items.apple;
	}

}
