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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockCrate extends Block implements IHasModel
{

	public BlockCrate(String name, Material materialIn)
	{
		super(materialIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.WOOD);
		setHardness(ModBlocks.defaultHardness);
		setResistance(ModBlocks.defaultResistance);
		setHarvestLevel("axe", 0);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitx, float hity, float hitz)
	{
		if (!worldIn.isRemote)
		{
			if (player.getHeldItemMainhand().getItem().equals(ModItems.CROWBAR))
			{
				worldIn.setBlockToAir(pos);
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getItemFromBlock(Blocks.CHEST))));
				ItemStack loot = null;
				if (this == ModBlocks.CRATE_MEAT)
				{
					loot = new ItemStack(ModItems.MEAT, 8);
				}
				if (this == ModBlocks.CRATE_METAL)
				{
					loot = new ItemStack(ModItems.INGOT_METAL, 8);
				}
				if (this == ModBlocks.CRATE_PLASTIC)
				{
					loot = new ItemStack(ModItems.INGOT_PLASTIC, 8);
				}
				if (this == ModBlocks.CRATE_SMOKE)
				{
					loot = new ItemStack(ModItems.BOTTLE_SMOKE, 8);
				}
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), loot));
			}
			return true;
		}
		else
		{
			if (worldIn.isRemote)
			{
				player.sendMessage(new TextComponentTranslation("You're going to need a crate opener for that..."));
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(ItemLore.loreAll[1]);
	}
	
	@Override
	public void registerModels()
	{
		MainInit.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public boolean isOpaqueCube() {
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
		return null;
	}

}
