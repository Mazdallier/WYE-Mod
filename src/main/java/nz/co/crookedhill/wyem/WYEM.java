package nz.co.crookedhill.wyem;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import nz.co.crookedhill.wyem.item.WYEMItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = WYEM.MODID, version = WYEM.VERSION)
public class WYEM
{
    public static final String MODID = "wyem";
    public static final String VERSION = "$VERSION$";
    
    public static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wyeMaterial", 15, new int[] {1, 3, 2, 1}, 25);
    public static WYEMCreativeTab wyemTab = new WYEMCreativeTab("WYEM");
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		WYEMItem.init();
    }
}
