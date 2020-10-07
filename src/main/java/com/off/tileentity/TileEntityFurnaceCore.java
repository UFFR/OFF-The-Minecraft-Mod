package com.off.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFurnaceCore extends TileEntity implements ITickable
{
	public int fuel = 0;
	public long progress = 0;
	public boolean active = false;
	public float heat = 20.0F;
	public float maxHeat = 1000.0F;
	public ItemStackHandler inventory;
	public ICapabilityProvider dropProvider;
	public static int processingTime = 50;
	
	private String customName;
	
	public TileEntityFurnaceCore()
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
	
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}
	
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.customName : "container.machineFurnace";
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
		compound.setShort("cookTime", (short) progress);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
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
		
		/*if (!world.isRemote)
		{
			
		}*/
	}
}
