package com.off.blocks;

import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IItemLore;
import com.off.util.ItemLore;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class BlockSpecial extends BlockBase implements IItemLore
{
	/**
	 * Special block, with numerous parameters
	 * @param name - Registry name
	 * @param material - Material type
	 * @param sound - Sound type
	 * @param hardness - Time required to mine
	 * @param resistance - Resistance to explosions
	 * @param tooltype - Tool required to mine (efficiently)
	 * @param harvestlevel - Tool level required to mine (0 = hand)
	 * @param opacity - How much light is allowed through or not
	 * @param luminence - How much light is emitted
	 * @param unbreakable - Should it be unbreakable like bedrock
	 */
	public BlockSpecial(String name, Material material, SoundType sound, Float hardness, Float resistance, String tooltype, Integer harvestlevel, Integer opacity, Float luminence, Boolean unbreakable)
	{
		super(name, material, sound);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sound);
		setCreativeTab(MainInit.tabOFFBlocks);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(tooltype, harvestlevel);
		setLightOpacity(opacity);
		setLightLevel(luminence);
		if (unbreakable == true)
		{
			setBlockUnbreakable();
		}
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		if (this == ModBlocks.VOID_TREE_LEAVES)
		{
			return BlockRenderLayer.CUTOUT;
		}
		else
		{
			return BlockRenderLayer.SOLID;
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		if (this == ModBlocks.VOID_TREE_LEAVES)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this == ModBlocks.VOID_TREE_LEAVES || this == ModBlocks.VOID_TREE_PLANKS)
		{
			tooltip.add(ItemLore.loreBlock[4]);
		}
		if (this == ModBlocks.CUBE_RED || this == ModBlocks.CUBE_YELLOW)
		{
			tooltip.add(ItemLore.loreBlock[6]);
			tooltip.add(ItemLore.loreBlock[7]);
		}
	}
	
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}
}
