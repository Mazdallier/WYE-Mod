package nz.co.crookedhill.wyem.item;

import net.minecraft.item.ItemSword;
import nz.co.crookedhill.wyem.WYEM;

public class WYEMItemHeadCollector extends ItemSword
{

	public WYEMItemHeadCollector(String unlocolizedName) {
		super(ToolMaterial.EMERALD);
		this.setCreativeTab(WYEM.wyemTab);
		this.setUnlocalizedName(unlocolizedName);
		this.setTextureName(WYEM.MODID + ":" + unlocolizedName);
	}
}
