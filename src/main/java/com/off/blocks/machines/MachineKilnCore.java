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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityKilnCore tileEntity = (TileEntityKilnCore) worldIn.getTileEntity(pos);
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack1 = itemHandler.getStackInSlot(0);
		ItemStack stack2 = itemHandler.getStackInSlot(1);
		ItemStack stack3 = itemHandler.getStackInSlot(2);
		if (!stack1.isEmpty())
		{
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack1));
		}
		if (!stack2.isEmpty())
		{
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getY(), pos.getY(), pos.getZ(), stack2));
		}
		if (!stack3.isEmpty())
		{
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack3));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	/*@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else if (!playerIn.isSneaking())
		{
			playerIn.openGui(MainInit.instance, Reference.GUI_KILN, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		else
		{
			return false;
		}
	}*/
	
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
