package com.off.blocks;

import java.util.ArrayList;
import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.EnumItemLore;
import com.off.util.IItemLore;
import com.off.util.ItemLore;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockFall extends BlockFalling implements IItemLore
{
	private List<EnumItemLore> loreList = new ArrayList<EnumItemLore>();
	/**
	 * Falling block type, like sand or gravel
	 * @param name - Name of the block
	 * @param material - Material type, usually ground or sand
	 * @param soundType - 0: Sand; 1: Ground
	 */
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

	@Override
	public IItemLore addLore(EnumItemLore... loreIn)
	{
		for (EnumItemLore lore : loreIn)
			loreList.add(lore);
		return this;
	}

	@Override
	public IItemLore setHasEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IItemLore setRarity(EnumRarity rarityIn) {
		// TODO Auto-generated method stub
		return null;
	}
}
