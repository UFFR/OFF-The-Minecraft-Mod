package com.off.blocks.machines;

import java.util.List;
import java.util.Random;

import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.tileentity.TileEntityKilnCore;
import com.off.util.ItemLore;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class MachineKilnCore extends BlockContainer
{

	public MachineKilnCore(String name, Material materialIn)
	{
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(ModBlocks.defaultHardness);
		setResistance(ModBlocks.defaultResistance);
		setHarvestLevel(ModBlocks.defaultToolType, ModBlocks.defaultHarvestLevel);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityKilnCore();
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.FURNACE_CORE);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(ItemLore.loreMachine[0]);
	}
}
