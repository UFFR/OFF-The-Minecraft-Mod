package com.off.inventory;

import java.util.HashMap;

import com.off.init.ModBlocks;
import com.off.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KilnRecipes
{
	private static HashMap<Item, ItemStack> recipeList = new HashMap<Item, ItemStack>();
	private static HashMap<Item, Float> heatList = new HashMap<Item, Float>();
	public static void register()
	{
		addKilnRecipe(ModItems.FLESH_ELSEN, new ItemStack(ModItems.SUGAR, 2), 1200.0F);
		addKilnRecipe(ModItems.FLESH_ELSEN_BURNED, new ItemStack(ModItems.SUGAR, 2), 1000.0F);
		addKilnRecipe(Item.getItemFromBlock(ModBlocks.ORE_METAL), new ItemStack(ModItems.INGOT_METAL, 4), 525.0F);
		addKilnRecipe(ModItems.NUGGET_METAL_IMPURE, new ItemStack(ModItems.NUGGET_METAL, 3), 375.5F);
		addKilnRecipe(ModItems.MEAT_METAL, new ItemStack(ModItems.FRAGMENT_POOR_METAL, 6), 350.0F);
	}
	
	public static void addKilnRecipe(Item input, ItemStack output, Float heat)
	{
		recipeList.put(input, output);
		heatList.put(input, heat);
	}
	
	public static KilnRecipe getOutput(Item input)
	{
		if (input == null || input.getRegistryName() == null || !recipeList.containsKey(input))
		{
			System.out.println("[KILNRECIPES] No recipe");
			return null;
		}
		System.out.println("[KILNRECIPES] Recipe output: " + recipeList.get(input).getItem().getRegistryName().toString());
		System.out.println("[KILNRECIPES] Recipe heat: " + heatList.get(input));
		return new KilnRecipe(recipeList.get(input), heatList.get(input));
	}
	
	public static class KilnRecipe
	{
		public ItemStack output;
		public float heat;
		
		public KilnRecipe() {}
		
		public KilnRecipe(Item output, float heat)
		{
			this(new ItemStack(output), heat);
		}
		
		public KilnRecipe(ItemStack output, float heat)
		{
			this.output = output;
			this.heat = heat;
		}
	}
}
