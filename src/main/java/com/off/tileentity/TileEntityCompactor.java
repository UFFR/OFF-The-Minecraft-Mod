package com.off.tileentity;

import com.off.blocks.machines.MachineCompactor;
import com.off.inventory.CompactorRecipes;
import com.off.inventory.CompactorRecipes.CompactorRecipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompactor extends TileEntity implements ITickable
{
	private ItemStackHandler inventory;
	private String customName;
	public int processingTime;
	public int totalTime = 150;
	
	public TileEntityCompactor()
	{
		inventory = new ItemStackHandler(2)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				//System.out.println("[Compactor]: Contents changed, marking dirty...");
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public boolean isActive()
	{
		return getField(0) > 0 || canProcess();
	}
	
	public String getInventoryName()
	{
		return this.hasCustomName() ? this.customName : "container.compactor";
	}
	
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty() && this.customName.length() > 0;
	}
	
	public void setCustomName(String customName)
	{
		this.customName = customName;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setShort("ProcessingTime", (short)processingTime);
		compound.setTag("Inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		processingTime = compound.getShort("ProcessingTime");
		if (compound.hasKey("Inventory"))
		{
			inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
		}
		super.readFromNBT(compound);
	}
	
	public boolean canProcess()
	{
		if (inventory.getStackInSlot(0).isEmpty())
		{
			//System.out.println("[Compactor]: Input slot is empty");
			return false;
		}
		else
		{
			CompactorRecipe recipe = CompactorRecipes.getOutput(inventory.getStackInSlot(0).getItem());
			//System.out.println("[Compactor]: Input is: " + inventory.getStackInSlot(0).getDisplayName());
			if (recipe == null)
			{
				//System.out.println("[Compactor]: Recipe is null");
				return false;
			}
			else
			{
				//ItemStack outputSlot = inventory.getStackInSlot(1);
				//System.out.println("[Compactor]: Recipe output: " + recipe.output.getDisplayName());
				if (inventory.getStackInSlot(1).isEmpty())
				{
					//System.out.println("[Compactor]: Output slot is empty (Can process)");
					return true;
				}
				else if (!inventory.getStackInSlot(1).isItemEqual(recipe.output))
				{
					//System.out.println("[Compactor]: Output slot is not empty and is not the same as recipe output (Cannot process)");
					//System.out.println("[Compactor]: Stack in output slot: " + inventory.getStackInSlot(1).getItem().getRegistryName());
					return false;
				}
				else if (inventory.getStackInSlot(1).getCount() < inventory.getSlotLimit(1) && inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getMaxStackSize())
				{
					return true;
				}
				else
				{
					return inventory.getStackInSlot(1).getCount() < recipe.output.getMaxStackSize();
				}
			}
		}
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (world.getTileEntity(pos) != this)
			return false;
		else
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
	}
	
	public String getGUIID()
	{
		return "off:compactor";
	}

	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.processingTime;
		case 1:
			return this.totalTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			this.processingTime = value;
			break;
		case 1:
			this.totalTime = value;
			break;
		}
	}
	
	private void processItem()
	{
		if (canProcess())
		{
			boolean processingSuccessful = false;
			//System.out.println("[Compactor]: Machine is currently processing...");
			CompactorRecipe recipe = CompactorRecipes.getOutput(inventory.getStackInSlot(0).getItem());
			//System.out.println("[Compactor]: Input identified as: " + inventory.getStackInSlot(0).getDisplayName());
			if (recipe == null)
			{
				System.out.println("[Compactor]: Recipe is null");
				return;
			}
			
			ItemStack itemStack = recipe.output.copy();
			//System.out.println("[Compactor]: Recipe output identified as: " + itemStack.getDisplayName());
			if (inventory.getStackInSlot(1).isEmpty())
			{
				//System.out.println("[Compactor]: Output slot was empty, setting new item stack...");
				inventory.setStackInSlot(1, itemStack.copy());
				processingSuccessful = true;
			}
			else if (inventory.getStackInSlot(1).isItemEqual(itemStack))
			{
				//System.out.println("[Compactor]: Output slot is the same as recipe output, growing by 1...");
				inventory.getStackInSlot(1).grow(itemStack.getCount());
				processingSuccessful = true;
			}
			
			if (processingSuccessful)
			{
				inventory.getStackInSlot(0).shrink(1);
			}
		}
	}
	
	@Override
	public void update()
	{
		boolean dirty = false;
		if (!world.isRemote)
		{
			if (canProcess())
			{
				//System.out.println("[Compactor]: Is able to process");
				processingTime++;
				//System.out.println("[Compactor]: Current processing time: " + processingTime);
				if (processingTime == totalTime)
				{
					//System.out.println("[Compactor]: Processing item...");
					processingTime = 0;
					this.processItem();
					dirty = true;
				}
				
				boolean trigger = true;
				
				if (canProcess() && processingTime == 0)
				{
					trigger = false;
				}
				
				if (trigger)
				{
					//System.out.println("[Compactor]: Is dirty and updating block state");
					dirty = true;
				}
				
				if (dirty)
				{
					markDirty();
				}
			}
			else
			{
				processingTime = 0;
			}
			MachineCompactor.updateBlockState(isActive(), world, pos);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
