package com.off;

import org.apache.logging.log4j.Logger;

import com.off.init.SmeltingRecipes;
import com.off.init.SoundRegistry;
import com.off.proxy.CommonProxy;
import com.off.util.Reference;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSION)
public class MainInit
{
    private static Logger logger;
    
    @Instance
    public static MainInit instance;
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        System.out.println(Reference.MODID + ":preInit");
        SoundRegistry.init();
        //ModItems.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        System.out.println(Reference.MODID + ":init");
        SmeltingRecipes.init();
        //CreativeTabs modItemTab = ModItemTab(CreativeTabs.getNextID(), "tabOFFItems");
    }
    
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	System.out.println(Reference.MODID + ":postInit");
    }
}
