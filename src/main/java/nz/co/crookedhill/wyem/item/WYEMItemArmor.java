package nz.co.crookedhill.wyem.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.WYEM;

public class WYEMItemArmor extends ItemArmor
{
	private String textureName;

	public WYEMItemArmor(String unlocolizedName, ArmorMaterial material, String textureName, int type) 
	{
		super(material, 0, type);
		this.setUnlocalizedName(unlocolizedName);
		this.setTextureName(WYEM.MODID + ":" + unlocolizedName);
		this.textureName = textureName;
		this.setCreativeTab(WYEM.tabWyem);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return WYEM.MODID + ":armor/" + this.textureName + ".png";
	}
}