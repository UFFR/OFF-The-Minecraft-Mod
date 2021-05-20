package com.off.blocks;

import java.util.ArrayList;
import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.EnumItemLore;
import com.off.util.IItemLore;
import com.off.util.ItemLore;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPillar extends BlockRotatedPillar implements IItemLore
{
	private List<EnumItemLore> loreList = new ArrayList<EnumItemLore>();
	public BlockPillar(String name, Material materialIn, SoundType sound, Float hardness, Float resistance, String toolType, Integer harvestLevel)
	{
		super(materialIn);
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
		if (this == ModBlocks.VOID_TREE_STEM)
		{
			tooltip.add(ItemLore.loreBlock[4]);
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
