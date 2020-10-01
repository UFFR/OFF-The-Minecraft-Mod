package com.off.init;

import java.util.ArrayList;
import java.util.List;

import com.off.blocks.BlockBase;
import com.off.blocks.BlockFall;
import com.off.blocks.BlockOre;
import com.off.blocks.BlockSpecial;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	/*
	BlockBase: Extremely basic block
	BlockOre: Block that drops something other than itself, doesn't have to be an ore
	BlockSpecial: Block with special properties and may be considered rare
	*/
	
	// Defaults
	public static Material defaultMaterial = Material.ROCK;
	public static SoundType defaultSound = SoundType.STONE;
	public static Float defaultHardness = 2.0F;
	public static Float defaultResistance = 5.0F;
	public static String defaultToolType = "pickaxe";
	public static Integer defaultHarvestLevel = 1;
	
	/* General */
	// Material blocks
	public static final Block BLOCK_METAL = new BlockBase("block_metal", Material.IRON, SoundType.METAL, 5.0F, defaultResistance, "pickaxe", 2);
	public static final Block BLOCK_PLASTIC = new BlockBase("block_plastic", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel);
	public static final Block BLOCK_SUGAR = new BlockFall("block_sugar", Material.SAND, 0);
	
	// Ores
	public static final Block ORE_SMOKE = new BlockOre("ore_smoke", defaultMaterial, defaultHardness, 2.5F);
	public static final Block ORE_SMOKE_EMPTY = new BlockOre("ore_smoke_empty", Material.ROCK, 0.5F, 0.5F);
	public static final Block ORE_METAL = new BlockBase("ore_metal", defaultMaterial, defaultSound, 3.0F, 8.0F, defaultToolType, defaultHarvestLevel);

	// Generating blocks
	public static final Block DIRT = new BlockBase("dirt", Material.GROUND, SoundType.GROUND, 0.5F, 0.5F, "shovel", 0);
	public static final Block ROCK = new BlockBase("rock", defaultMaterial, defaultSound, defaultHardness, 6.0F, defaultToolType, defaultHarvestLevel);
	
	// Building blocks
	public static final Block BRICKS = new BlockBase("bricks", defaultMaterial, defaultSound, 2.5F, 15.0F, defaultToolType, defaultHarvestLevel);
	
	// Oooo spooky blocks
	public static final Block NOTHING = new BlockSpecial("nothing", Material.BARRIER, SoundType.METAL, 18000000.0F, 18000000.0F, "", 100, 255, 0, true);
	public static final Block VOID_TREE_STEM = new BlockSpecial("void_tree_stem", Material.WOOD, SoundType.WOOD, 15.0F, 30.0F, "axe", 4, 0, 1, false);
	public static final Block VOID_TREE_LEAVES = new BlockSpecial("void_tree_leaves", Material.LEAVES, SoundType.PLANT, 15.0F, 30.0F, "axe", 0, 0, 1, false);
	
	/* Industrial Furnace */
	// Exclusive
	public static final Block HEAT_SHIELDING = new BlockBase("heat_shielding", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel);

	// Non-exclusive
	public static final Block BRICKS_METAL = new BlockBase("bricks_metal", Material.IRON, SoundType.METAL, 4.0F, 18.0F, defaultToolType, 2);
}
