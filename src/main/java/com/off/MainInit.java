package com.off;

import org.apache.logging.log4j.Logger;

import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.init.OreDictionaryRegistry;
import com.off.init.SmeltingRecipes;
import com.off.init.SoundRegistry;
import com.off.inventory.CompactorRecipes;
import com.off.inventory.KilnRecipes;
import com.off.proxy.CommonProxy;
import com.off.util.Reference;
import com.off.util.TextureLoader;
import com.off.util.handlers.GuiHandler;
import com.off.util.handlers.TileEntityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSION)
public class MainInit
{
    private static Logger logger;
    
    @Instance
    public static MainInit instance;
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    // Creative tabs
    public final static CreativeTabs tabOFFItems = (new CreativeTabs("tabOFFItems")
    {
    	@Override
    	public ItemStack getTabIconItem()
    	{
    		return new ItemStack(ModItems.LOGO);
    	}
    });
    
    public final static CreativeTabs tabOFFBlocks = (new CreativeTabs("tabOFFBlocks")
    {
    	@Override
    	public ItemStack getTabIconItem()
    	{
    		return new ItemStack(Item.getItemFromBlock(ModBlocks.ORE_METAL));
    	}
    });
    
    public final static CreativeTabs tabOFFMusic = (new CreativeTabs("tabOFFMusic")
    {
    	@Override
    	public ItemStack getTabIconItem()
    	{
    		return new ItemStack(ModItems.DISC_PEPPER_STEAK);
    	}
    	
    	@Override
    	public boolean hasSearchBar()
    	{
    		return true;
    	}
    }.setBackgroundImageName("item_search.png"));
    
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        System.out.println(Reference.MODID + ":preInit");
        SoundRegistry.init();// Register sounds
        ModItems.preInit();// Register items
        ModBlocks.preInit();// Register blocks
        TextureLoader.registerItems();// Register textures and models
        TileEntityHandler.registerTileEntities();// Self explanatory
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        System.out.println(Reference.MODID + ":init");
        SmeltingRecipes.init();// Register smelting recipes
        OreDictionaryRegistry.registry();// Register ore dictionary
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        TextureLoader.registerBlocks(mesher);// Register item block textures
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());// Register GUIs
    	CompactorRecipes.register();// Register compactor recipes
    	KilnRecipes.register();// Register kiln recipes
    }
    
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	System.out.println(Reference.MODID + ":postInit");
    }
}
