package nz.co.crookedhill.wyem.item;

import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import nz.co.crookedhill.wyem.WYEM;

public class WYEMItemHeadCollector extends ItemSword
{

	public WYEMItemHeadCollector(String unlocolizedName) {
		super(EnumHelper.addToolMaterial("wyemtool", 500, 500, 500, 1, 25));
		this.setCreativeTab(WYEM.tabWyem);
		this.setUnlocalizedName(unlocolizedName);
		this.setTextureName(WYEM.MODID + ":" + unlocolizedName);
	}
}
