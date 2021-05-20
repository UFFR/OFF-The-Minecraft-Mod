package com.off.blocks;

import java.util.ArrayList;
import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.EnumItemLore;
import com.off.util.IItemLore;
import com.off.util.ItemLore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBase extends Block implements IItemLore
{
	private List<EnumItemLore> loreList = new ArrayList<EnumItemLore>();
	/** Very basic block constructor **/
	public BlockBase(String name)
	{
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		setSoundType(SoundType.STONE);
		setHardness(2.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 1);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	/** Block with custom material type **/
	public BlockBase(String name, Material material, SoundType sound)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sound);
	}
	public BlockBase setCustomHarvestLevel(String toolType, int level)
	{
		setHarvestLevel(toolType, level);
		return this;
	}
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this == ModBlocks.ORE_METAL || this == ModBlocks.BLOCK_METAL)
		{
			tooltip.add(ItemLore.loreMaterial[0]);
		}
		if (this == ModBlocks.HEAT_SHIELDING || this == ModBlocks.FURNACE_CORE || this == ModBlocks.FURNACE_CORE_COMPONENT)
		{
			tooltip.add(ItemLore.loreMachine[0]);
		}
		if (this == ModBlocks.BRICKS_METAL)
		{
			tooltip.add(ItemLore.loreBlock[1]);
			tooltip.add(ItemLore.loreBlock[2]);
		}
		if (this == ModBlocks.BRICKS)
		{
			tooltip.add(ItemLore.loreBlock[3]);
		}
		if (this == ModBlocks.DIRT)
		{
			tooltip.add(ItemLore.loreMaterial[0]);
		}
		if (this == ModBlocks.ROCK)
		{
			tooltip.add(ItemLore.loreBlock[0]);
		}
	}
	@Override
	public BlockBase addLore(EnumItemLore... loreIn)
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
	public BlockBase setRarity(EnumRarity rarityIn)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
