package com.off.util;

import com.off.init.ModBlocks;
import com.off.init.ModItems;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistry
{
	public static void registry()
	{
		// Basic items
		OreDictionary.registerOre("ingotMetal", ModItems.INGOT_METAL);
		OreDictionary.registerOre("ingotPlastic", ModItems.INGOT_PLASTIC);
		OreDictionary.registerOre("ingotPlasticRaw", ModItems.INGOT_PLASTIC_RAW);
		OreDictionary.registerOre("ingotPoorMetal", ModItems.POOR_METAL);
		OreDictionary.registerOre("dustSugar", ModItems.SUGAR);
		
		// Plates
		OreDictionary.registerOre("plateMetal", ModItems.PLATE_METAL);
		OreDictionary.registerOre("platePlastic", ModItems.PLATE_PLASTIC);
		
		// Nuggets
		OreDictionary.registerOre("nuggetMetal", ModItems.NUGGET_METAL);
		OreDictionary.registerOre("nuggetPoorMetal", ModItems.FRAGMENT_POOR_METAL);
		
		// Ores
		OreDictionary.registerOre("oreMetal", ModBlocks.ORE_METAL);
		OreDictionary.registerOre("oreSmoke", ModBlocks.ORE_SMOKE);
		OreDictionary.registerOre("oreSmokeEmpty", ModBlocks.ORE_SMOKE_EMPTY);
		
		// Blocks
		OreDictionary.registerOre("blockMetal", ModBlocks.BLOCK_METAL);
		OreDictionary.registerOre("blockPlastic", ModBlocks.BLOCK_PLASTIC);
		OreDictionary.registerOre("blockSugar", ModBlocks.BLOCK_SUGAR);
		
		// Records
		// Efficiency!
		for (Item item : ModItems.RECORDS)
		{
			OreDictionary.registerOre("record", item);
		}
		
		// Misc
		OreDictionary.registerOre("cobblestone", ModBlocks.ROCK);
		OreDictionary.registerOre("stone", ModBlocks.ROCK);
		OreDictionary.registerOre("dirt", ModBlocks.DIRT);
	}
}
