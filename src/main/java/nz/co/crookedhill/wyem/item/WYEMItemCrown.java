package nz.co.crookedhill.wyem.item;

public class WYEMItemCrown extends WYEMItemArmor{
	
	public Class<?> friendlyEntity;
	public String friendlyString;

	public WYEMItemCrown(String unlocolizedName, ArmorMaterial material, String textureName, int type, Class<?> friendlyEntity) {
		super(unlocolizedName, material, textureName, type);
		this.friendlyEntity = friendlyEntity;
		this.friendlyString = unlocolizedName;
	}

}
