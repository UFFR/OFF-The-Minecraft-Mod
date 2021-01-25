package com.off.util.handlers;

import com.off.tileentity.TileEntityCompactor;
import com.off.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityCompactor.class, new ResourceLocation(Reference.MODID + ":compactor"));
	}
}
