package com.off.util;

import net.minecraft.item.EnumRarity;

public interface IItemLore
{
	public IItemLore addLore(EnumItemLore... loreIn);
	public IItemLore setHasEffect();
	public IItemLore setRarity(EnumRarity rarityIn);
}
