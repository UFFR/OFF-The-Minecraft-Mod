package com.off.util.handlers;

import com.off.blocks.container.ContainerCompactor;
import com.off.inventory.gui.GuiCompactor;
import com.off.tileentity.TileEntityCompactor;
import com.off.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_COMPACTOR)
			return new ContainerCompactor(player.inventory, (TileEntityCompactor)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_COMPACTOR)
			return new GuiCompactor(player.inventory, (TileEntityCompactor)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

}
