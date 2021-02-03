package com.off.tileentity;

import com.off.init.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityKilnCore extends TileEntity implements ITickable
{
	/*
	 * Slots
	 0 = Fuel
	 1 = Input
	 2 = Output
	*/
	public int fuel = 0; // Not sure if it will count in furnace operations or burn time in ticks, probably ticks right?
	public long progress = 0;
	public boolean active = false;
	public static long heat = 20; // Room temperature
	public float maxHeat = 1500.0F; // Anything hotter would be a bit unrealistic for a simple furnace/kiln
	public int maxFuel = 2000; // Open to change during testing
	public ItemStackHandler inventory;
	public ICapabilityProvider dropProvider;
	public static int processingTime = (int) (-0.111111*heat + 216.666667); // Base level (200), changes with temperature, uses typical linear function, might change to a exponential or quadratic depending on how it goes
	
	public String errorPoint;
	private String customName;
	
	public TileEntityKilnCore()
	{
		inventory = new ItemStackHandler(27)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		dropProvider = new ICapabilityProvider()
		{
			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing)
			{
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
			}
			
			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing)
			{
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : null;
			}
		};
	}
	
	// Check the blocks that make up the structure
	public boolean isStructureValid(World worldIn)
	{
		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		errorPoint = null;
		/* Components */
		// Check outer shell
		// Front and back
		for (int x1 = -3; x1 <= 3; x1++)
		{
			for (int y1 = -3; y1 <= 3; y1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z + 3)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x + x1, y + y1, z + 3);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z - 3)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x + x1, y + y1, z - 3);
					return false;
				}
			}
		}
		// Top and bottom
		for (int x1 = -3; x1 <= 3; x1++)
		{
			for (int z1 = -3; z1 <= 3; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + 3, z + z1)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x + x1, y + 3, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y - 3, z + z1)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x + x1, y - 3, z + z1);
					return false;
				}
			}
		}
		// Left and right
		for (int y1 = -3; y1 <= 3; y1++)
		{
			for (int z1 = -3; z1 <= 3; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + 3, y + y1, z + z1)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x + 3, y + y1, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x - 3, y + y1, z + z1)).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%o, %o, %o)]", x - 3, y + y1, z + z1);
					return false;
				}
			}
		}
		// Heat shielding
		// Front and back
		for (int x1 = -2; x1 <= 2; x1++)
		{
			for (int y1 = -2; y1 <= 2; y1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z + 2)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x + x1, y + y1, z + 2);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z - 2)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x + x1, y + y1, z - 2);
					return false;
				}
			}
		}
		// Top and bottom
		for (int x1 = -2; x1 <= 2; x1++)
		{
			for (int z1 = -2; z1 <= 2; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + 2, z + z1)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x + x1, y + 2, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y - 2, z + z1)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x + x1, y - 2, z + z1);
					return false;
				}
			}
		}
		// Left and right
		for (int y1 = -2; y1 <= 2; y1++)
		{
			for (int z1 = -2; z1 <= 2; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + 2, y + y1, z + z1)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x + 2, y + y1, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x - 2, y + y1, z + z1)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%o, %o, %o)]", x - 2, y + y1, z + z1);
					return false;
				}
			}
		}
		// Core components
		// Front and back
		for (int x1 = -1; x1 <= 1; x1++)
		{
			for (int y1 = -1; y1 <= 1; y1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z + 1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x + x1, y + y1, z + 1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + y1, z - 1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x + x1, y + y1, z - 1);
					return false;
				}
			}
		}
		// Top and bottom
		for (int x1 = -1; x1 <= 1; x1++)
		{
			for (int z1 = -1; z1 <= 1; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + x1, y + 1, z + z1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x + x1, y + 1, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x + x1, y - 1, z + z1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x + x1, y - 1, z + z1);
					return false;
				}
			}
		}
		// Left and right
		for (int y1 = -1; y1 <= 1; y1++)
		{
			for (int z1 = -1; z1 <= 1; z1++)
			{
				if (worldIn.getBlockState(mPos.setPos(x + 1, y + y1, z + z1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x + 1, y + y1, z + z1);
					return false;
				}
				if (worldIn.getBlockState(mPos.setPos(x - 1, y + y1, z + z1)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%o, %o, %o)]", x - 1, y + y1, z + z1);
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}
	
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.customName : "container.kiln";
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (world.getTileEntity(pos) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		TileEntityKilnCore.heat = compound.getLong("heatTime");
		this.progress = compound.getShort("cookTime");
		if (compound.hasKey("inventory"))
		{
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
			super.readFromNBT(compound);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setFloat("heatTime", heat);
		compound.setInteger("cookTime", (short) progress);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public boolean hasHeat()
	{
		return heat >= 200;
	}
	
	public boolean isProcessing()
	{
		return TileEntityKilnCore.processingTime > 0;
	}
	
	public int getDiFurnaceProgressScaled(int i)
	{
		return (int) ((progress * i) / processingTime);
	}
	
	public long getHeatRemainingScaled(long i)
	{
		return (long) ((heat * i) / maxHeat);
	}
	
	public boolean canProcess()
	{
		if (inventory.getStackInSlot(1).isEmpty())
		{
			return false;
		}
		ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(1));
		if (itemStack == null || itemStack.isEmpty())
		{
			return false;
		}
		if (inventory.getStackInSlot(2).isEmpty())
		{
			return true;
		}
		if (!inventory.getStackInSlot(2).isEmpty())
		{
			return false;
		}
		if (inventory.getStackInSlot(2).getCount() < inventory.getSlotLimit(2) && inventory.getStackInSlot(2).getCount() < inventory.getStackInSlot(2).getMaxStackSize())
		{
			return true;
		}
		if (heat < 200)
		{
			return false;
		}
		else
		{
			return inventory.getStackInSlot(2).getCount() < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem()
	{
		if (canProcess())
		{
			ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(1));
			
			if (inventory.getStackInSlot(2).isEmpty())
			{
				inventory.setStackInSlot(2, itemStack.copy());
			}
			else if (inventory.getStackInSlot(2).isItemEqual(itemStack))
			{
				inventory.getStackInSlot(2).grow(itemStack.getCount());
			}
			
			for (int i = 1; i < 2; i++)
			{
				if (inventory.getStackInSlot(2).isEmpty())
				{
					inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem()));
				}
				else
				{
					inventory.getStackInSlot(i).shrink(1);
				}
				if (inventory.getStackInSlot(i).isEmpty())
				{
					inventory.setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}
	}

	@Override
	public void update()
	{
		boolean flag = false;
		
		if (!world.isRemote || isStructureValid(world))
		{
			long prevHeat = heat;
						
			if (hasHeat() && canProcess())
			{
				progress++;
				
				heat -= 50;
				
				if (this.progress == TileEntityKilnCore.processingTime)
				{
					this.progress = 0;
					this.processItem();
					flag = true;
				}
				else
				{
					progress = 0;
				}
				
				boolean trigger = true;
				
				if (hasHeat() && canProcess() && this.progress == 0)
				{
					trigger = false;
				}
				
				if (trigger)
				{
					flag = true;
					//Furnacecore
				}
			}
		}
	}
}
