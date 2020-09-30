package com.off.init;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipes
{
	public static void init()
	{
		GameRegistry.addSmelting(ModBlocks.ORE_METAL, new ItemStack(ModItems.INGOT_METAL, 1), 1.5F);
		GameRegistry.addSmelting(ModBlocks.ORE_SMOKE_EMPTY, new ItemStack(ModBlocks.ROCK, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.INGOT_PLASTIC, new ItemStack(ModItems.INGOT_PLASTIC_RAW, 1), 1.0F);
		GameRegistry.addSmelting(ModBlocks.ROCK, new ItemStack(ModBlocks.HEAT_SHIELDING, 1), 1.0F);
		GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ModBlocks.HEAT_SHIELDING, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.MEAT_RAW, new ItemStack(ModItems.MEAT), 0.5F);
		GameRegistry.addSmelting(ModItems.MEAT_METAL, new ItemStack(ModItems.FRAGMENT_POOR_METAL, 3), 0.75F);
		GameRegistry.addSmelting(ModItems.NUGGET_METAL_IMPURE, new ItemStack(ModItems.NUGGET_METAL), 0.75F);
		GameRegistry.addSmelting(ModItems.FLESH_ELSEN, new ItemStack(ModItems.FLESH_ELSEN_BURNED), 2.5F);
	}
}
