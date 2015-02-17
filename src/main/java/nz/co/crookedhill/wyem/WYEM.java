package nz.co.crookedhill.wyem;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nz.co.crookedhill.wyem.item.WYEMItem;
import nz.co.crookedhill.wyem.proxy.ClientProxy;

@Mod(modid = WYEM.MODID, version = WYEM.VERSION)
public class WYEM
{
	public static final String MODID = "wyem";
	public static final String VERSION = "1.0.0.1";
	
	@SidedProxy(clientSide="nz.co.crookedhill.wyem.proxy.ClientProxy", serverSide="nz.co.crookedhill.wyem.proxy.CommonProxy")
	public static ClientProxy proxy;
	
	@Instance("wyem")
	public static WYEM instance;

	public static ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wyeMaterial", WYEM.MODID + "zombieCrown", 15, new int[] {1, 3, 2, 1}, 25);
	//Use a custom item as an icon (assuming it is instantiated in a class called ModItems)
	public static CreativeTabs tabWyem = new CreativeTabs("tabWYEM") {
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
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.headCollector, 0, new ModelResourceLocation("wyem:head_collector", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.creeperCrown, 0, new ModelResourceLocation("wyem:creeper_helmet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.enderChestplate, 0, new ModelResourceLocation("wyem:ender_chestplate", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.skeletonCrown, 0, new ModelResourceLocation("wyem:skeleton_helmet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.spiderTreads, 0, new ModelResourceLocation("wyem:spider_boots", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.witherCrown, 0, new ModelResourceLocation("wyem:wither_helmet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WYEMItem.zombieCrown, 0, new ModelResourceLocation("wyem:zombie_helmet", "inventory"));
		
	}
}
