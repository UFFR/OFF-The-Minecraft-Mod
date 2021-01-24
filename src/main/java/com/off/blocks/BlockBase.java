package com.off.blocks;

import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.ItemLore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBase extends Block
{
	public BlockBase(String name, Material material, SoundType sound, Float hardness, Float resistance, String toolType, Integer harvestLevel)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		setSoundType(sound);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(toolType, harvestLevel);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this == ModBlocks.ORE_METAL || this == ModBlocks.BLOCK_METAL)
		{
			tooltip.add(ItemLore.loreAll[5]);
		}
		if (this == ModBlocks.HEAT_SHIELDING || this == ModBlocks.FURNACE_CORE || this == ModBlocks.FURNACE_CORE_COMPONENT)
		{
			tooltip.add(ItemLore.loreAll[14]);
		}
		if (this == ModBlocks.BRICKS_METAL)
		{
			tooltip.add(ItemLore.loreAll[11]);
			tooltip.add(ItemLore.loreAll[12]);
		}
		if (this == ModBlocks.BRICKS)
		{
			tooltip.add(ItemLore.loreAll[13]);
		}
		if (this == ModBlocks.DIRT)
		{
			tooltip.add(ItemLore.loreAll[6]);
		}
		if (this == ModBlocks.ROCK)
		{
			tooltip.add(ItemLore.loreAll[28]);
		}
	}
}
