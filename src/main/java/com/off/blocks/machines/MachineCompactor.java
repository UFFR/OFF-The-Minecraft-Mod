package com.off.blocks.machines;

import java.util.List;
import java.util.Random;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.tileentity.TileEntityCompactor;
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
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MachineCompactor extends BlockContainer
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	private final boolean isActive;
	
	public MachineCompactor(String name)
	{
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(ModBlocks.defaultHardness);
		setResistance(ModBlocks.defaultResistance);
		setHarvestLevel(ModBlocks.defaultToolType, ModBlocks.defaultHarvestLevel);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
		this.isActive = false;
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCompactor();
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
		return Item.getItemFromBlock(ModBlocks.COMPACTOR);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityCompactor tileEntity = (TileEntityCompactor) worldIn.getTileEntity(pos);
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack1 = itemHandler.getStackInSlot(0);
		ItemStack stack2 = itemHandler.getStackInSlot(1);
		if (!stack1.isEmpty())
		{
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack1));
		}
		if (!stack2.isEmpty())
		{
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack2));
		}
		super.breakBlock(worldIn, pos, state);
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
			TileEntityCompactor tileEntity = (TileEntityCompactor) worldIn.getTileEntity(pos);
			if (tileEntity != null)
			{
				playerIn.openGui(MainInit.instance, Reference.GUI_COMPACTOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void updateBlockState(boolean isActive, World worldIn, BlockPos pos)
	{
		EnumFacing facing = worldIn.getBlockState(pos).getValue(FACING);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		worldIn.setBlockState(pos, ModBlocks.COMPACTOR.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, isActive));
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

	public void setState(boolean active, World worldIn, BlockPos pos)
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		if (stack.hasDisplayName())
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			
			if (tileEntity instanceof TileEntityCompactor)
			{
				((TileEntityCompactor)tileEntity).setCustomName(stack.getDisplayName());
			}
		}
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
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
		return new BlockStateContainer(this, new IProperty[] {ACTIVE, FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean active = (meta & 1) == 1 ? true : false;
		meta = meta >> 1;
		EnumFacing enumFacing = EnumFacing.getFront(meta);
		
		if (enumFacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumFacing = EnumFacing.NORTH;
		}
		
		return this.getDefaultState().withProperty(FACING, enumFacing).withProperty(ACTIVE, active);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = ((EnumFacing)state.getValue(FACING)).getIndex() << 1;
		meta += state.getValue(ACTIVE) ? 1 : 0;
		return meta;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(ItemLore.loreMachine[1]);
	}
}