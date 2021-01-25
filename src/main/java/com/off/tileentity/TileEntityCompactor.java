package com.off.tileentity;

import com.off.blocks.machines.BlockCompactor;
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
	private int processingTime;
	private int totalTime = 200;
	
	public TileEntityCompactor()
	{
		inventory = new ItemStackHandler(2)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public static boolean isActive(TileEntityCompactor tileEntity)
	{
		return tileEntity.getField(0) > 0;
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
		/*super.writeToNBT(compound);
		compound.setInteger("ProcessingTime", (short)this.processingTime);
		compound.setInteger("TotalTime", (short)this.totalTime);
		compound.setTag("Inventory", this.inventory.serializeNBT());
		
		if (this.hasCustomName()) compound.setString("CustomName", customName);
		return compound;*/
		compound.setShort("ProcessingTime", (short)processingTime);
		compound.setTag("Inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		/*super.readFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.processingTime = compound.getInteger("ProcessingTime");
		this.totalTime = compound.getInteger("TotalTime");
		
		if (compound.hasKey("CustomName", 8))
		{
			this.setCustomName(compound.getString("CustomName"));
		}*/
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
			return false;
		}
		else
		{
			CompactorRecipe recipe = CompactorRecipes.getOutput(inventory.getStackInSlot(0));
			
			if (recipe == null)
			{
				return false;
			}
			else
			{
				//ItemStack outputSlot = inventory.getStackInSlot(1);
				
				if (/*outputSlot.isEmpty()*/inventory.getStackInSlot(1).isEmpty())
				{
					return true;
				}
				else if (/*outputSlot.isItemEqual(recipe.output)*/!inventory.getStackInSlot(1).isItemEqual(recipe.output))
				{
					return false;
				}
				else if (/*outputSlot.getCount() < 64 && outputSlot.getCount() < outputSlot.getMaxStackSize()*/inventory.getStackInSlot(1).getCount() < inventory.getSlotLimit(1) && inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getMaxStackSize())
				{
					return true;
				}
				else
				{
					return /*outputSlot.getCount() < recipe.output.getMaxStackSize();*/inventory.getStackInSlot(1).getCount() < recipe.output.getMaxStackSize();
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
			CompactorRecipe recipe = CompactorRecipes.getOutput(inventory.getStackInSlot(0));
			
			if (recipe == null)
			{
				return;
			}
			
			ItemStack itemStack = recipe.output;
			
			if (inventory.getStackInSlot(1).isEmpty())
			{
				inventory.setStackInSlot(1, itemStack.copy());
			}
			else if (inventory.getStackInSlot(1).isItemEqual(itemStack))
			{
				inventory.getStackInSlot(1).grow(itemStack.getCount());
			}
			
			for (int i = 1; i < 2; i++)
			{
				if (inventory.getStackInSlot(i).isEmpty())
				{
					inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem()));
				}
				else
				{
					inventory.getStackInSlot(0).shrink(1);
				}
				/*if (inventory.getStackInSlot(i).isEmpty())
					inventory.setStackInSlot(i, ItemStack.EMPTY);*/
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
				processingTime++;
				
				if (this.processingTime == this.totalTime)
				{
					this.processingTime = 0;
					this.processItem();
					dirty = true;
				}
				else
				{
					this.processingTime = 0;
				}
				
				boolean trigger = true;
				
				if (canProcess() && this.processingTime == 0)
				{
					trigger = false;
				}
				
				if (trigger)
				{
					dirty = true;
					BlockCompactor.updateBlockState(canProcess(), this.world, pos);
				}
				
				if (dirty)
				{
					markDirty();
				}
			}
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
