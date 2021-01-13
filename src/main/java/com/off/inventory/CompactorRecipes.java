package com.off.inventory;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.off.init.ModItems;
import com.off.inventory.Common.ComparableStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompactorRecipes
{
	private static final CompactorRecipes INSTANCE = new CompactorRecipes();
	private static HashMap<ComparableStack, CompactorRecipe> recipeList = new HashMap<>();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static CompactorRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private CompactorRecipes()
	{
		addCompactorRecipe(new ItemStack(ModItems.INGOT_PLASTIC_RAW), new ItemStack(ModItems.INGOT_PLASTIC), 1.0F);
		addCompactorRecipe(new ItemStack(ModItems.INGOT_PLASTIC), new ItemStack(ModItems.PLATE_PLASTIC), 1.0F);
		addCompactorRecipe(new ItemStack(ModItems.INGOT_METAL), new ItemStack(ModItems.PLATE_METAL), 1.0F);
	}
	
	public void addCompactorRecipe(ItemStack input, ItemStack output, Float exp)
	{
		//if (getCompactorResult(input) != ItemStack.EMPTY) return;
		//this.recipeList.put(input, output, null);
		recipeList.put(new ComparableStack(input), new CompactorRecipe(output));
		this.experienceList.put(output, Float.valueOf(exp));
	}
		
	public static CompactorRecipe getOutput(ItemStack stack)
	{
		if (stack == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack);
		return CompactorRecipes.recipeList.get(comp);
	}
	
	public static class CompactorRecipe
	{
		public ItemStack output;
		
		public CompactorRecipe() {}
		
		public CompactorRecipe(Item output)
		{
			this(new ItemStack(output));
		}
		
		public CompactorRecipe(ItemStack output)
		{
			this.output = output;
		}
	}
}
