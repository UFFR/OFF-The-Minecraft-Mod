package com.off.creativetabs;

import com.off.init.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModBlockTab extends CreativeTabs {

	public ModBlockTab(int index, String label) {
		super(index, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getTabIconItem()
	{
		if (ModBlocks.ORE_METAL != null)
		{
			return new ItemStack(Item.getItemFromBlock(ModBlocks.ORE_METAL));
		}
		else
		{
			return new ItemStack(Item.getItemFromBlock(Blocks.IRON_ORE));
		}
	}

}
