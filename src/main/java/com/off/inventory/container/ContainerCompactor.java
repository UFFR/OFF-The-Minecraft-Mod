package com.off.inventory.container;

import com.off.inventory.SlotOutput;
import com.off.tileentity.TileEntityCompactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompactor extends Container
{
	private final TileEntityCompactor tileEntity;
	private int processingTime, totalTime;
	
	public ContainerCompactor(InventoryPlayer player, TileEntityCompactor tileEntity)
	{
		this.tileEntity = tileEntity;
		this.processingTime = tileEntity.processingTime;
		this.totalTime = tileEntity.totalTime;
		
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 56, 34));
		this.addSlotToContainer(new SlotOutput(itemHandler, 1, 116, 35));
		
		// Player inventory
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + (y * 9) + 9, 8 + (x * 18), 84 + (y * 18)));
			}
		}
		
		for (int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, this.tileEntity.processingTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if (this.processingTime != this.tileEntity.getField(0))
			{
				listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			}
			if (this.totalTime != this.tileEntity.getField(1))
			{
				listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			}
		}
		
		this.processingTime = tileEntity.getField(0);
		this.totalTime = tileEntity.getField(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileEntity.isUsableByPlayer(playerIn);
	}
	
	
	// Heavy: Are you sure this will work?
	// Medic: I have no idea!
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemStack1 = ItemStack.EMPTY;
		Slot slot1 = (Slot)this.inventorySlots.get(index);
		
		if (slot1 != null && slot1.getHasStack())
		{
			ItemStack itemStack2 = slot1.getStack();
			itemStack1 = itemStack2.copy();
			
			if (index == 0)
			{
				if (!this.mergeItemStack(itemStack2, 4, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
				//slot1.onSlotChange(itemStack2, itemStack1);
			}
			else if (index != 0 || !this.mergeItemStack(itemStack2, 0, 3, false))
			{
				return ItemStack.EMPTY;
			}
			else if (!this.mergeItemStack(itemStack2, 4, 40, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (itemStack2.isEmpty())
			{
				slot1.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot1.onSlotChanged();
			}
			
			if (itemStack2.getCount() == itemStack1.getCount())
			{
				return ItemStack.EMPTY;
			}
			//slot1.onTake(playerIn, itemStack2);
		}
		return itemStack1;
	}
}
