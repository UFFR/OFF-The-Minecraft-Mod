package com.off.inventory;

import java.util.HashMap;

import com.off.init.ModBlocks;
import com.off.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KilnRecipes
{
	private static HashMap<Item, KilnRecipe> recipeList = new HashMap<Item, KilnRecipe>();
	public static void register()
	{
		addKilnRecipe(ModItems.FLESH_ELSEN, new ItemStack(ModItems.SUGAR, 2), 1200.0F);
		addKilnRecipe(ModItems.FLESH_ELSEN_BURNED, new ItemStack(ModItems.SUGAR, 2), 1000.0F);
		addKilnRecipe(Item.getItemFromBlock(ModBlocks.ORE_METAL), new ItemStack(ModItems.INGOT_METAL, 4), 525.0F);
		addKilnRecipe(ModItems.NUGGET_METAL_IMPURE, new ItemStack(ModItems.NUGGET_METAL, 3), 375.5F);
		addKilnRecipe(ModItems.MEAT_METAL, new ItemStack(ModItems.FRAGMENT_POOR_METAL, 6), 350.0F);
	}
	/**
	 * Recipe adding
	 * @param input - The input item 
	 * @param output - ItemStack output (can be more than 1)
	 * @param heat - Heat required for the recipe to work
	 */
	private static void addKilnRecipe(Item input, ItemStack output, Float heat)
	{
		recipeList.put(input, new KilnRecipe(output, heat));
	}
	
	public static KilnRecipe getRecipe(Item input)
	{
		if (input == null || input.getRegistryName() == null || !recipeList.containsKey(input))
			return null;
		else
			return recipeList.get(input);
	}
	
	public static class KilnRecipe
	{
		public ItemStack output;
		public float heat;
		
		public KilnRecipe(ItemStack out, float heat)
		{
			this.output = out;
			this.heat = heat;
		}
		public ItemStack getOutput()
		{
			return output.copy();
		}
	}
}
