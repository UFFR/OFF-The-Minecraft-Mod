package com.off.util;

import java.util.List;
import java.util.Random;

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

public interface IHasModel
{
	public void registerModels();

	void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag);

	public Item getItemDropped(IBlockState state, Random rand, int fortune);
	
	boolean isOpaqueCube();

	EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY);

	EnumRarity getRarity(ItemStack stack);
}
