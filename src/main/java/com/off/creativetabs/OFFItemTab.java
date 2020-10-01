package com.off.creativetabs;

import com.off.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class OFFItemTab extends CreativeTabs {

	public OFFItemTab(int index, String label) {
		super(index, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getTabIconItem() {
		// TODO Auto-generated method stub
		return new ItemStack(ModItems.LOGO);
	}

}
