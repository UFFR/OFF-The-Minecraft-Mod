package com.off.items;

import java.util.List;
import java.util.Random;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IHasModel;
import com.off.util.ItemLore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBase extends Item implements IHasModel
{

	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		if (this == ModItems.LOGO)
		{
			setCreativeTab(null);
		}
		else
		{
			setCreativeTab(MainInit.tabOFFItems);
		}
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		if (this == ModItems.TICKET_FORTUNE || this == ModItems.FLESH_GOLD)
		{
			return EnumRarity.UNCOMMON;
		}
		if (this == ModItems.FLESH_ELSEN || this == ModItems.FLESH_ELSEN_BURNED || this == ModItems.SUGAR || this == Item.getItemFromBlock(ModBlocks.BLOCK_SUGAR))
		{
			return EnumRarity.RARE;
		}
		if (this == Item.getItemFromBlock(ModBlocks.VOID_TREE_LEAVES) || this == Item.getItemFromBlock(ModBlocks.VOID_TREE_STEM))
		{
			return EnumRarity.EPIC;
		}
		return EnumRarity.COMMON;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		if (this == ModItems.TICKET_FORTUNE || this == ModItems.FLESH_GOLD || this == ModItems.SUGAR)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		if (this == ModItems.LOGO)
		{
			tooltip.add(ItemLore.loreAll[17]);
		}
		if (this == ModItems.SUGAR)
		{
			tooltip.add(ItemLore.elementLore[4]);
		}
		if (this == ModItems.INGOT_METAL || this == ModItems.NUGGET_METAL || this == ModItems.NUGGET_METAL_IMPURE || this == ModItems.FRAGMENT_POOR_METAL || this == ModItems.POOR_METAL)
		{
			tooltip.add(ItemLore.elementLore[1]);
		}
		if (this == ModItems.INGOT_PLASTIC || this == ModItems.NUGGET_PLASTIC_RAW || this == ModItems.INGOT_PLASTIC_RAW)
		{
			tooltip.add(ItemLore.elementLore[2]);
		}
		if (this == ModItems.NUGGET_PLASTIC_RAW || this == ModItems.INGOT_PLASTIC_RAW)
		{
			tooltip.add(ItemLore.loreAll[18]);
		}
		if (this == ModItems.MEAT || this == ModItems.MEAT_RAW)
		{
			tooltip.add(ItemLore.elementLore[3]);
		}
		if (this == ModItems.MEAT_RAW)
		{
			tooltip.add(ItemLore.loreAll[19]);
		}
		if (this == ModItems.MEAT)
		{
			tooltip.add(ItemLore.loreAll[20]);
		}
		if (this == ModItems.MEAT_METAL)
		{
			tooltip.add(ItemLore.loreAll[21]);
		}
		if (this == ModItems.NUGGET_METAL_IMPURE)
		{
			tooltip.add(ItemLore.loreAll[22]);
		}
		if (this == ModItems.BOTTLE_LARGE)
		{
			tooltip.add(ItemLore.loreAll[0]);
		}
		if (this == ModItems.BOTTLE_MEAT)
		{
			tooltip.add(ItemLore.loreAll[2]);
			tooltip.add(ItemLore.loreAll[1]);
		}
		if (this == ModItems.BOTTLE_PLASTIC)
		{
			tooltip.add(ItemLore.loreAll[4]);
			tooltip.add(ItemLore.loreAll[1]);
		}
		if (this == ModItems.BOTTLE_SMOKE)
		{
			tooltip.add(ItemLore.loreAll[3]);
			tooltip.add(ItemLore.loreAll[1]);
		}
		if (this == ModItems.FLESH_SILVER)
		{
			tooltip.add(ItemLore.loreAll[9]);
		}
		if (this == ModItems.FLESH_GOLD)
		{
			tooltip.add(ItemLore.loreAll[10]);
		}
		if (this == ModItems.TICKET_LUCK)
		{
			tooltip.add(ItemLore.loreAll[7]);
		}
		if (this == ModItems.TICKET_FORTUNE)
		{
			tooltip.add(ItemLore.loreAll[8]);
		}
		if (this == ModItems.POOR_METAL || this == ModItems.FRAGMENT_POOR_METAL)
		{
			tooltip.add(ItemLore.loreAll[6]);
		}
		if (this == ModItems.SUGAR)
		{
			tooltip.add(ItemLore.elementLore[0]);
			tooltip.add(ItemLore.loreAll[16]);
		}
		if (this == ModItems.FLESH_ELSEN)
		{
			tooltip.add(ItemLore.loreAll[26]);
		}
		if (this == ModItems.FLESH_ELSEN_BURNED)
		{
			tooltip.add(ItemLore.loreAll[15]);
		}
		if (this == ModItems.BOTTLE_SEALED)
		{
			tooltip.add(ItemLore.loreAll[27]);
		}
	}
	
	@Override
	public void registerModels()
	{
		MainInit.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY) {
		// TODO Auto-generated method stub
		return null;
	}

}
