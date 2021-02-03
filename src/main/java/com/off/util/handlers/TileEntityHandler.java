package com.off.util.handlers;

import com.off.tileentity.TileEntityCompactor;
import com.off.tileentity.TileEntityKilnCore;
import com.off.tileentity.TileEntityKilnHatch;
import com.off.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityCompactor.class, new ResourceLocation(Reference.MODID, "compactor"));
		GameRegistry.registerTileEntity(TileEntityKilnCore.class, new ResourceLocation(Reference.MODID, "furnace_core"));
		GameRegistry.registerTileEntity(TileEntityKilnHatch.class, new ResourceLocation(Reference.MODID, "furnace_access"));
	}
}
