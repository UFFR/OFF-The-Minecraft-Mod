package com.off.inventory;

import java.util.HashMap;

import com.off.init.ModItems;
import com.off.inventory.Common.ComparableStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompactorRecipes
{
    private static HashMap<ComparableStack, CompactorRecipe> recipeList = new HashMap<>();
    public static void register()
    {
        addCompactorRecipe(new ItemStack(ModItems.INGOT_PLASTIC_RAW), new ItemStack(ModItems.INGOT_PLASTIC), 1.0F);
        addCompactorRecipe(new ItemStack(ModItems.INGOT_PLASTIC), new ItemStack(ModItems.PLATE_PLASTIC), 1.0F);
        addCompactorRecipe(new ItemStack(ModItems.INGOT_METAL), new ItemStack(ModItems.PLATE_METAL), 1.0F);
    }
    
    public static void addCompactorRecipe(ItemStack input, ItemStack output, Float exp)
    {
        //if (getCompactorResult(input) != ItemStack.EMPTY) return;
        //this.recipeList.put(input, output, null);
        recipeList.put(new ComparableStack(input), new CompactorRecipe(output));
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