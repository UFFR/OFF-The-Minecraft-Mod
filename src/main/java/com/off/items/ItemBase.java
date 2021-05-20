package com.off.items;

import java.util.ArrayList;
import java.util.List;

import com.off.MainInit;
import com.off.init.ModItems;
import com.off.util.EnumItemLore;
import com.off.util.IItemLore;
import com.off.util.ItemLore;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemLore
{
	EnumRarity rarity = EnumRarity.COMMON;
	boolean hasEffect = false;
	private List<EnumItemLore> loreList = new ArrayList<EnumItemLore>();
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
        setCreativeTab(MainInit.tabOFFItems);
		ModItems.ITEMS.add(this);
	}
	
	public ItemBase setRarity(EnumRarity customRarity)
	{
		rarity = customRarity;
		return this;
	}
	
	public ItemBase setHasEffect()
	{
		hasEffect = true;
		return this;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return rarity;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return hasEffect;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		if (this == ModItems.LOGO)
		{
			tooltip.add(ItemLore.loreMisc[0]);
		}
		if (this == ModItems.SUGAR)
		{
			tooltip.add(ItemLore.elementLore[4]);
		}
		if (this == ModItems.INGOT_METAL || this == ModItems.NUGGET_METAL || this == ModItems.NUGGET_METAL_IMPURE || this == ModItems.FRAGMENT_POOR_METAL || this == ModItems.POOR_METAL)
		{
			tooltip.add(ItemLore.elementLore[1]);
		}
		if (this == ModItems.INGOT_METAL || this == ModItems.NUGGET_METAL)
		{
			tooltip.add(ItemLore.loreMaterial[0]);
		}
		if (this == ModItems.INGOT_PLASTIC || this == ModItems.NUGGET_PLASTIC_RAW || this == ModItems.INGOT_PLASTIC_RAW)
		{
			tooltip.add(ItemLore.elementLore[2]);
		}
		if (this == ModItems.NUGGET_PLASTIC_RAW || this == ModItems.INGOT_PLASTIC_RAW)
		{
			tooltip.add(ItemLore.loreMaterial[2]);
		}
		if (this == ModItems.MEAT || this == ModItems.MEAT_RAW)
		{
			tooltip.add(ItemLore.elementLore[3]);
		}
		if (this == ModItems.MEAT_RAW)
		{
			tooltip.add(ItemLore.loreMaterial[3]);
		}
		if (this == ModItems.MEAT)
		{
			tooltip.add(ItemLore.loreMaterial[4]);
		}
		if (this == ModItems.MEAT_METAL)
		{
			tooltip.add(ItemLore.loreMaterial[5]);
		}
		if (this == ModItems.NUGGET_METAL_IMPURE)
		{
			tooltip.add(ItemLore.loreMaterial[6]);
		}
		if (this == ModItems.BOTTLE_LARGE)
		{
			tooltip.add(ItemLore.loreMisc[1]);
		}
		if (this == ModItems.BOTTLE_MEAT)
		{
			tooltip.add(ItemLore.loreMisc[3]);
			tooltip.add(ItemLore.loreMisc[2]);
		}
		if (this == ModItems.BOTTLE_PLASTIC)
		{
			tooltip.add(ItemLore.loreMisc[5]);
			tooltip.add(ItemLore.loreMisc[2]);
		}
		if (this == ModItems.BOTTLE_SMOKE)
		{
			tooltip.add(ItemLore.loreMisc[4]);
			tooltip.add(ItemLore.loreMisc[2]);
		}
		if (this == ModItems.FLESH_SILVER)
		{
			tooltip.add(ItemLore.loreConsumable[2]);
		}
		if (this == ModItems.FLESH_GOLD)
		{
			tooltip.add(ItemLore.loreConsumable[3]);
		}
		if (this == ModItems.TICKET_LUCK)
		{
			tooltip.add(ItemLore.loreConsumable[0]);
		}
		if (this == ModItems.TICKET_FORTUNE)
		{
			tooltip.add(ItemLore.loreConsumable[1]);
		}
		if (this == ModItems.POOR_METAL || this == ModItems.FRAGMENT_POOR_METAL)
		{
			tooltip.add(ItemLore.loreMaterial[1]);
		}
		if (this == ModItems.SUGAR)
		{
			tooltip.add(ItemLore.elementLore[4]);
			tooltip.add(ItemLore.loreMaterial[8]);
		}
		if (this == ModItems.FLESH_ELSEN)
		{
			tooltip.add(ItemLore.loreItem[1]);
		}
		if (this == ModItems.FLESH_ELSEN_BURNED)
		{
			tooltip.add(ItemLore.loreItem[2]);
		}
		if (this == ModItems.BOTTLE_SEALED)
		{
			tooltip.add(ItemLore.loreItem[0]);
		}
		if (this == ModItems.CROWBAR)
		{
			tooltip.add(ItemLore.loreItem[3]);
		}
	}

	@Override
	public IItemLore addLore(EnumItemLore... loreIn)
	{
		for (EnumItemLore lore : loreIn)
			loreList.add(lore);
		return this;
	}
}
