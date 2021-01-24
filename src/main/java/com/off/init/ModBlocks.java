package com.off.init;

import java.util.ArrayList;
import java.util.List;

import com.off.blocks.BlockBase;
import com.off.blocks.BlockCrate;
import com.off.blocks.BlockFall;
import com.off.blocks.BlockOre;
import com.off.blocks.BlockPillar;
import com.off.blocks.BlockSpecial;
import com.off.blocks.BlockStair;
import com.off.blocks.machines.BlockCompactor;
import com.off.blocks.machines.BlockHatch;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	/*
	BlockBase: Extremely basic block
	BlockOre: Block that drops something other than itself, doesn't have to be an ore
	BlockFall: Self-explanatory
	BlockSpecial: Block with special properties and may be considered "rare"
	BlockCrate: Block that when right clicked with a crowbar, drops all its items, very specific
	BlockPillar: Pillar-like rotations such as logs or, well, pillars
	BlockStair: Self-explanatory
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
	public static final Block BLOCK_METAL = new BlockBase("block_metal", Material.IRON, SoundType.METAL, 5.0F, 30.0F, "pickaxe", 2); // Storage for metal, may also be used as a fairly durably construction material
	public static final Block BLOCK_PLASTIC = new BlockBase("block_plastic", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel); // Storage for plastic, may also be used for a cheap construction material
	public static final Block BLOCK_SUGAR = new BlockFall("block_sugar", Material.SAND, 0); // Storage for sugar
	
	/* Generating blocks */
	// Boring
	public static final Block DIRT = new BlockBase("dirt", Material.GROUND, SoundType.GROUND, 0.5F, 0.5F, "shovel", 0); // Self-explanatory
	public static final Block ROCK = new BlockBase("rock", defaultMaterial, defaultSound, defaultHardness, 6.0F, defaultToolType, defaultHarvestLevel); // Basically stone, considering on adding a cobblestone equivalent

	// Ores
	public static final Block ORE_SMOKE = new BlockOre("ore_smoke", defaultMaterial, defaultHardness, 2.5F); // Brittle, found underground
	public static final Block ORE_SMOKE_EMPTY = new BlockOre("ore_smoke_empty", Material.ROCK, 0.5F, 0.5F); // Slightly less brittle, can be smelted back into rock
	public static final Block ORE_METAL = new BlockBase("ore_metal", defaultMaterial, defaultSound, 3.0F, 8.0F, defaultToolType, defaultHarvestLevel); // Slightly tougher than regular rock, slightly less common than smoke
	
	/* Non generating */
	// Trading crates. Self-explanatory, can either be bought for material or made and sold for credits
	public static final Block CRATE_SMOKE = new BlockCrate("crate_smoke", Material.WOOD);
	public static final Block CRATE_METAL = new BlockCrate("crate_metal", Material.WOOD);
	public static final Block CRATE_PLASTIC = new BlockCrate("crate_plastic", Material.WOOD);
	public static final Block CRATE_MEAT = new BlockCrate("crate_meat", Material.WOOD);
	
	// Oooo spooky blocks (they're actually just special blocks for one reason or another)
	public static final Block NOTHING = new BlockSpecial("nothing", Material.BARRIER, SoundRegistry.nullBlock, 18000000.0F, 18000000.0F, "", 100, 255, 0F, true); // Standard void block, unbreakable
	public static final Block VOID_TREE_STEM = new BlockPillar("void_tree_stem", Material.WOOD, SoundType.WOOD, 15.0F, 30.0F, "axe", 4); // Log of void trees, glows
	public static final Block VOID_TREE_LEAVES = new BlockSpecial("void_tree_leaves", Material.LEAVES, SoundType.PLANT, 15.0F, 30.0F, "axe", 0, 0, 0.75F, false); // Leaves of void trees, glows
	public static final Block VOID_TREE_PLANKS = new BlockSpecial("void_tree_planks", Material.WOOD, SoundType.WOOD, 15.0F, 30.0F, "axe", 0, 0, 0.75F, false); // Chopped wood of void trees, glows
	public static final Block CUBE_RED = new BlockSpecial("cube_red", Material.BARRIER, SoundType.METAL, 30.0F, 1000.0F, "pickaxe", 0, 255, 1F, false); // Cube that heals and allows for trans-dimensional transportation
	public static final Block CUBE_YELLOW = new BlockSpecial("cube_yellow", Material.BARRIER, SoundType.METAL, 30.0F, 1000.0F, "pickaxe", 0, 255, 1F, false); // Cube that only heals
	
	/* Industrial Furnace */
	// Exclusive
	public static final Block HEAT_SHIELDING = new BlockBase("heat_shielding", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel); // Basically "smooth stone", you can use it for decoration of some sort I suppose
	public static final Block FURNACE_CORE = new BlockBase("furnace_core", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel); // Main block of the furnace
	public static final Block FURNACE_CORE_COMPONENT = new BlockBase("furnace_core_component", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel); // Sort of an "extension" of the core
	public static final Block FURNACE_ACCESS = new BlockHatch("furnace_access"); // Access port to core

	// Non-exclusive
	public static final Block BRICKS_METAL = new BlockBase("bricks_metal", Material.IRON, SoundType.METAL, 4.0F, 20.0F, defaultToolType, 2); // Outer casing, can also be used as a decent building material
	
	/* Miscellaneous */
	// Machines
	public static final Block COMPACTOR = new BlockCompactor("compactor"); // Compacts "liquid" plastic ingots into fully solid ones, can also make plates
	// Building blocks
	public static final Block BRICKS = new BlockBase("bricks", defaultMaterial, defaultSound, 2.5F, 15.0F, defaultToolType, defaultHarvestLevel); // Cheap building material, most generated buildings use it.
	public static final Block STAIRS_BRICK_METAL = new BlockStair(BRICKS_METAL.getDefaultState(), "stairs_brick_metal", Material.IRON, SoundType.METAL, 4.0F, 20.0F, defaultToolType, 2); // Stairs made from metallic bricks
	public static final Block STAIRS_METAL = new BlockStair(BLOCK_METAL.getDefaultState(), "stairs_metal", Material.IRON, SoundType.METAL, 5.0F, 30.0F, defaultToolType, 2); // Stairs made from metal
	public static final Block STAIRS_PLASTIC = new BlockStair(BLOCK_PLASTIC.getDefaultState(), "stairs_plastic", defaultMaterial, defaultSound, defaultHardness, defaultResistance, defaultToolType, defaultHarvestLevel); // Stairs made from plastic
	public static final Block STAIRS_BRICK = new BlockStair(BRICKS.getDefaultState(), "stairs_brick", defaultMaterial, defaultSound, 2.5F, 15.0F, defaultToolType, defaultHarvestLevel); // Stairs made from bricks

	public static void preInit()
	{
		for (Block block : BLOCKS)
		{
			ForgeRegistries.BLOCKS.register(block);
		}
	}
}
