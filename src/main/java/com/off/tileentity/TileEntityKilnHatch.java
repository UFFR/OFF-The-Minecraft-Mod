package com.off.tileentity;

import com.off.blocks.machines.BlockHatch;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityKilnHatch extends TileEntity implements ITickable
{
	private BlockPos corePos;
	
	@Override
	public void update()
	{
		if (corePos == null)
		{
			return;
		}
		
		if (!world.isRemote)
		{
			BlockHatch.updateBlockState(false, this.world, pos);
		}
	}
}
