package nz.co.crookedhill.wyem.item;

public class WYEMItemCrown extends WYEMItemArmor{
	
	public String friendlyString;

	public WYEMItemCrown(String unlocolizedName, ArmorMaterial material, String textureName, int type) {
		super(unlocolizedName, material, textureName, type);
		this.friendlyString = unlocolizedName;
	}

}
