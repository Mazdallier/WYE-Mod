package nz.co.crookedhill.wyem.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import nz.co.crookedhill.wyem.WYEM;

public class WYEMItemArmor extends ItemArmor
{
	private String textureName;
	
	public WYEMItemArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) 
	{
		super(material, 0, type);
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(WYEM.MODID + ":" + unlocalizedName);
		this.textureName = textureName;
		this.setCreativeTab(WYEM.wyemTab);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return WYEM.MODID + ":armor/" + this.textureName + ".png";
	}
}
