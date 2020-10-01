package com.off.blocks;

import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IHasModel;
import com.off.util.ItemLore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBase extends Block implements IHasModel
{
	public BlockBase(String name, Material material, SoundType sound, Float hardness, Float resistance, String toolType, Integer harvestLevel)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		setSoundType(sound);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(toolType, harvestLevel);
		//setLightLevel(0.0F);
		//setLightOpacity(255);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels()
	{
		MainInit.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this == ModBlocks.ORE_METAL || this == ModBlocks.BLOCK_METAL)
		{
			tooltip.add(ItemLore.elementLore[1]);
			tooltip.add(ItemLore.loreAll[5]);
		}
		if (this == ModBlocks.BLOCK_PLASTIC)
		{
			tooltip.add(ItemLore.elementLore[2]);
		}
		if (this == ModBlocks.HEAT_SHIELDING)
		{
			tooltip.add(ItemLore.loreAll[14]);
		}
		if (this == ModBlocks.BRICKS_METAL)
		{
			tooltip.add(ItemLore.loreAll[11]);
			tooltip.add(ItemLore.loreAll[12]);
		}
		if (this == ModBlocks.BRICKS)
		{
			tooltip.add(ItemLore.loreAll[13]);
		}
		if (this == ModBlocks.DIRT)
		{
			tooltip.add(ItemLore.elementLore[1]);
			tooltip.add(ItemLore.loreAll[6]);
		}
		if (this == ModBlocks.ROCK)
		{
			tooltip.add(ItemLore.loreAll[28]);
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		// TODO Auto-generated method stub
		return EnumRarity.COMMON;
	}

}
