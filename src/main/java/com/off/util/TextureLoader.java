package com.off.util;

import com.off.init.ModBlocks;
import com.off.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TextureLoader
{
	public static void registerItems()
	{
		for (Item item : ModItems.ITEMS)
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlocks(ItemModelMesher mesher)
	{
		for (Block block : ModBlocks.BLOCKS)
		{
			Item itemBlock = Item.getItemFromBlock(block);
			ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
			ModelLoader.registerItemVariants(itemBlock, model);
			mesher.register(itemBlock, 0, model);
		}
	}
}
