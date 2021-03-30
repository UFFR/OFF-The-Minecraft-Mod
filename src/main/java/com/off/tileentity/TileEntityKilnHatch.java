package com.off.tileentity;

import com.off.blocks.machines.MachineKilnHatch;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityKilnHatch extends TileEntity implements ITickable
{
	public static BlockPos corePos;
	public static BlockPos anticipatedPos;
	
	public static TileEntityKilnCore getCoreTE(World worldIn, BlockPos pos)
	{
		EnumFacing facing = worldIn.getBlockState(pos).getValue(BlockHorizontal.FACING);
		if (facing == EnumFacing.NORTH)
		{
			anticipatedPos = pos.add(0, 0, 3);
			TileEntity coreEntity = worldIn.getTileEntity(anticipatedPos);
			if (coreEntity instanceof TileEntityKilnCore)
			{
				corePos = coreEntity.getPos();
				return (TileEntityKilnCore)coreEntity;
			}
		}
		if (facing == EnumFacing.SOUTH)
		{
			anticipatedPos = pos.add(0, 0, -3);
			TileEntity coreEntity = worldIn.getTileEntity(anticipatedPos);
			if (coreEntity instanceof TileEntityKilnCore)
			{
				corePos = coreEntity.getPos();
				return (TileEntityKilnCore)coreEntity;

			}
		}
		if (facing == EnumFacing.WEST)
		{
			anticipatedPos = pos.add(3, 0, 0);
			TileEntity coreEntity = worldIn.getTileEntity(anticipatedPos);
			if (coreEntity instanceof TileEntityKilnCore)
			{
				corePos = coreEntity.getPos();
				return (TileEntityKilnCore)coreEntity;
			}
		}
		if (facing == EnumFacing.EAST)
		{
			anticipatedPos = pos.add(-3, 0, 0);
			TileEntity coreEntity = worldIn.getTileEntity(anticipatedPos);
			if (coreEntity instanceof TileEntityKilnCore)
			{
				corePos = coreEntity.getPos();
				return (TileEntityKilnCore)coreEntity;
			}
		}
		corePos = null;
		return null;
	}
	
	public static boolean isStructureValid(World worldIn)
	{
		return ((TileEntityKilnCore)worldIn.getTileEntity(corePos)).isStructureValid(worldIn);
	}
	
	@Override
	public void update()
	{
		if (getCoreTE(world, pos) == null)
		{
			return;
		}
		
		if (!world.isRemote)
		{
			MachineKilnHatch.updateBlockState(getCoreTE(world, pos).isBurning(), this.world, pos);
		}
	}
}
