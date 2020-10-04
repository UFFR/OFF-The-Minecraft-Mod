package com.off.util;

import com.off.init.ModBlocks;
import com.off.init.ModItems;

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
		OreDictionary.registerOre("record", ModItems.DISC_SILENCIO);
		OreDictionary.registerOre("record", ModItems.DISC_FOURTEEN_RESIDENTS);
		OreDictionary.registerOre("record", ModItems.DISC_PEPPER_STEAK);
		
		// Misc
		OreDictionary.registerOre("cobblestone", ModBlocks.ROCK);
	}
}
