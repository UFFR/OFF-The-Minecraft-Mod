package com.off.blocks;

import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
	
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz)
	{
		if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem().equals(ModItems.CROWBAR))
		{
			BlockPos pos = new BlockPos(x, y, z);
			worldIn.setBlockToAir(pos);
			worldIn.spawnEntity(new EntityItem(worldIn, x + 0.5, y + 0.5, z + 0.5, new ItemStack(Item.getItemFromBlock(Blocks.CHEST))));
			ItemStack loot = null;
			if (this == ModBlocks.CRATE_MEAT)
			{
				loot = new ItemStack(ModItems.MEAT);
			}
			if (this == ModBlocks.CRATE_METAL)
			{
				loot = new ItemStack(ModItems.INGOT_METAL);
			}
			if (this == ModBlocks.CRATE_PLASTIC)
			{
				loot = new ItemStack(ModItems.INGOT_PLASTIC);
			}
			if (this == ModBlocks.CRATE_SMOKE)
			{
				loot = new ItemStack(ModItems.BOTTLE_SMOKE);
			}
			worldIn.spawnEntity(new EntityItem(worldIn, x + 0.5, y + 0.5, z + 0.5, loot));
			return true;
		}
			
		return false;
	}
	
	@Override
	public void registerModels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
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
