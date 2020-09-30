package com.off.creativetabs;

import com.off.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModItemTab extends CreativeTabs {

	public ModItemTab(int index, String label) {
		super(index, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getTabIconItem()
	{
		if (ModItems.LOGO != null)
		{
			return new ItemStack(ModItems.LOGO);
		}
		else
		{
			return new ItemStack(Items.IRON_INGOT);
		}
	}

}
