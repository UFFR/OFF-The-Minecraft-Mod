package com.off.blocks;

import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.ItemLore;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockFall extends BlockFalling
{

	public BlockFall(String name, Material material, Integer soundType)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		switch(soundType)
		{
		case 0:
			setSoundType(SoundType.SAND);
			break;
		case 1:
			setSoundType(SoundType.GROUND);
			break;
		}
		setHarvestLevel("shovel", 0);
		setHardness(0.5F);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this == ModBlocks.BLOCK_SUGAR)
		{
			tooltip.add(ItemLore.elementLore[4]);
		}
	}
}
