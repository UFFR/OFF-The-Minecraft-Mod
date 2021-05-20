package com.off.blocks;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

public class BlockStair extends BlockStairs
{
	public BlockStair(IBlockState modelState, String name, SoundType sound)
	{
		super(modelState);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		setSoundType(sound);
		setHardness(2.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 1);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
}
