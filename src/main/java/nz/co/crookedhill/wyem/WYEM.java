package nz.co.crookedhill.wyem;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import nz.co.crookedhill.wyem.item.WYEMItem;
import nz.co.crookedhill.wyem.proxy.ClientProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = WYEM.MODID, version = WYEM.VERSION)
public class WYEM
{
	public static final String MODID = "wyem";
	public static final String VERSION = "0.0.3.2";
	
	@SidedProxy(clientSide="nz.co.crookedhill.wyem.proxy.ClientProxy", serverSide="nz.co.crookedhill.wyem.proxy.CommonProxy")
	public static ClientProxy proxy;
	
	@Instance("wyem")
	public static WYEM instance;

	public static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wyeMaterial", 15, new int[] {1, 3, 2, 1}, 25);
	//Use a custom item as an icon (assuming it is instantiated in a class called ModItems)
	public static CreativeTabs tabCustom = new CreativeTabs("tabWYEM") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return WYEMItem.headCollector;
	    }
	};
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		WYEMConfigHelper.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + WYEM.MODID + File.separator + WYEM.MODID + ".cfg"));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WYEMItem.init();
		MinecraftForge.EVENT_BUS.register(new WYEMEventHandler());
		proxy.init();
	}
}
