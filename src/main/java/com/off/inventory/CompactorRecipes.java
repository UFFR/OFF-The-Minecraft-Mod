package com.off.inventory;

import java.util.HashMap;

import com.off.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompactorRecipes
{
    //private static HashMap<ComparableStack, CompactorRecipe> recipeList = new HashMap<>();
	private static HashMap<Item, Item> recipeList = new HashMap<Item, Item>();
    public static void register()
    {
        addCompactorRecipe(ModItems.INGOT_PLASTIC_RAW, ModItems.INGOT_PLASTIC);
        addCompactorRecipe(ModItems.INGOT_PLASTIC, ModItems.PLATE_PLASTIC);
        addCompactorRecipe(ModItems.INGOT_METAL, ModItems.PLATE_METAL);
    }
    
    public static void addCompactorRecipe(Item input, Item output)
    {
        //if (getCompactorResult(input) != ItemStack.EMPTY) return;
        //this.recipeList.put(input, output, null);
    	
        //recipeList.put(new ComparableStack(input), new CompactorRecipe(output));
    	recipeList.put(input, output);
    }
        
    public static CompactorRecipe getOutput(Item input)
    {
    	//System.out.println("[CompactorRecipes]: Input input identified as: " + input.getRegistryName().toString());
        if (input == null || input.getRegistryName() == null)
        {
        	//System.out.println("[CompactorRecipes]: Input input empty/null");
            return null;
        }
        
        if (!recipeList.containsKey(input))
        {
        	//System.out.println("[CompactorRecipes]: No recipe output");
        	return null;
        }
        // TODO remove or comment out debugging stuff once errors fixed
        //ComparableStack comp = new ComparableStack(input);
        //System.out.println("[CompactorRecipes]: Output input: " + recipeList.get(input).getRegistryName().toString());
        //return CompactorRecipes.recipeList.get(comp);
        return new CompactorRecipe(new ItemStack(recipeList.get(input)));
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