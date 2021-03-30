package com.off.blocks.machines;

import java.util.List;
import java.util.Random;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.tileentity.TileEntityKilnHatch;
import com.off.util.ItemLore;
import com.off.util.Reference;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class MachineKilnHatch extends BlockContainer
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	
	private final boolean isBurning;
	
	public MachineKilnHatch(String name)
	{
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(ModBlocks.defaultHardness);
		setResistance(ModBlocks.defaultResistance);
		setHarvestLevel(ModBlocks.defaultToolType, ModBlocks.defaultHarvestLevel);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
		this.isBurning = false;
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityKilnHatch();
	}
	
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.FURNACE_ACCESS);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else if (!playerIn.isSneaking())
		{
			TileEntity coreTE = TileEntityKilnHatch.getCoreTE(worldIn, pos);
			if (coreTE == null)
			{
				if (worldIn.getBlockState(TileEntityKilnHatch.anticipatedPos).getBlock() != ModBlocks.FURNACE_CORE)
				{
					playerIn.sendMessage(new TextComponentTranslation("[INDUSTRIAL FURNACE (KILN)]: CORE NOT FOUND"));
					playerIn.sendMessage(new TextComponentTranslation("[INDUSTRIAL FURNACE (KILN)]: CORE ANTICIPATED AT: " + TileEntityKilnHatch.anticipatedPos));
				}
				else
				{
					playerIn.sendMessage(new TextComponentTranslation("[INDUSTRIAL FURNACE (KILN)]: CORE FOUND, BUT NO TILE ENTITY, CAN'T CHECK STRUCTURE"));
				}
			}
			else
			{
				if (!TileEntityKilnHatch.isStructureValid(worldIn))
				{
					playerIn.sendMessage(new TextComponentTranslation("[INDUSTRIAL FURNACE (KILN)]: STRUCTURE INVALID AT: " + TileEntityKilnHatch.getCoreTE(worldIn, pos).errorPoint));
				}
				else
				{
					//playerIn.sendMessage(new TextComponentTranslation("[INDUSTRIAL FURNACE (KILN)]: STRUCTURE VALID!"));
					playerIn.openGui(MainInit.instance, Reference.GUI_KILN, worldIn, TileEntityKilnHatch.corePos.getX(), TileEntityKilnHatch.corePos.getY(), TileEntityKilnHatch.corePos.getZ());
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void updateBlockState(boolean isBurning, World worldIn, BlockPos pos)
	{
		EnumFacing facing = worldIn.getBlockState(pos).getValue(FACING);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		worldIn.setBlockState(pos, ModBlocks.FURNACE_ACCESS.getDefaultState().withProperty(FACING, facing).withProperty(BURNING, isBurning));
		if (tileEntity != null)
		{
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.setDefaultFacing(worldIn, pos, state);
	}
	
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	public static void setState(boolean burning, World worldIn, BlockPos pos)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if (tileEntity != null)
		{
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		}
	}
		
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {BURNING, FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean burning = (meta & 1) == 1 ? true : false;
		meta = meta >> 1;
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		
		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(BURNING, burning);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = ((EnumFacing)state.getValue(FACING)).getIndex() << 1;
		meta += state.getValue(BURNING) ? 1 : 0;
		return meta;
	}
	
	public boolean isBurning()
	{
		return isBurning;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(ItemLore.loreMachine[0]);
	}
}
