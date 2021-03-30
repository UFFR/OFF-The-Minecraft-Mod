package com.off.util.handlers;

import com.off.inventory.container.ContainerCompactor;
import com.off.inventory.container.ContainerKiln;
import com.off.inventory.gui.GuiCompactor;
import com.off.inventory.gui.GuiKiln;
import com.off.tileentity.TileEntityCompactor;
import com.off.tileentity.TileEntityKilnCore;
import com.off.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID)
		{
		case Reference.GUI_COMPACTOR:
			return new ContainerCompactor(player.inventory, (TileEntityCompactor)tileEntity);
		case Reference.GUI_KILN:
			return new ContainerKiln(player.inventory, (TileEntityKilnCore)tileEntity);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID)
		{
		case Reference.GUI_COMPACTOR:
			return new GuiCompactor(player.inventory, (TileEntityCompactor)tileEntity);
		case Reference.GUI_KILN:
			return new GuiKiln(player.inventory, (TileEntityKilnCore)tileEntity);
		default:
			return null;
		}
	}

}
