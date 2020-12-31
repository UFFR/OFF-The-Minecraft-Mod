package com.off.tileentity;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.off.init.ModItems;

import net.minecraft.item.ItemStack;

public class CompactorRecipes
{
	private static final CompactorRecipes INSTANCE = new CompactorRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> recipeList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
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
		if (getCompactorResult(input) != ItemStack.EMPTY) return;
		this.recipeList.put(input, output, null);
		this.experienceList.put(output, Float.valueOf(exp));
	}
	
	public ItemStack getCompactorResult(ItemStack input)
	{
		for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.recipeList.columnMap().entrySet())
		{
			if (compareItemStacks(input, (ItemStack)entry.getKey()))
			{
				for (Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
				{
					if (compareItemStacks(input, (ItemStack)ent.getKey()))
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}
