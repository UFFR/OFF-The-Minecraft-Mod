package com.off.inventory.container;

import com.off.inventory.KilnRecipes;
import com.off.inventory.SlotOutput;
import com.off.tileentity.TileEntityKilnCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerKiln extends Container
{
	private final TileEntityKilnCore tileEntity;
	//private final IInventory tileInventory;
	private int processingTime, totalTime, fuel;
	private float heat;
	//EntityPlayerMP player;

	public ContainerKiln(InventoryPlayer player, TileEntityKilnCore tileEntity)
	{
		this.tileEntity = tileEntity;
		//this.tileInventory = (IInventory) tileEntity.inventory;
		this.processingTime = 0;
		this.totalTime = tileEntity.totalTime;
		this.fuel = tileEntity.fuel;
		this.heat = tileEntity.heat;
		
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 56, 53));
		this.addSlotToContainer(new SlotItemHandler(itemHandler, 1, 56, 17));
		this.addSlotToContainer(new SlotOutput(itemHandler, 2, 116, 35));
		
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
		//listener.sendAllWindowProperties(this, (IInventory) tileInventory);
		//listener.sendAllWindowProperties(this, (IInventory) this.tileEntity);
		listener.sendWindowProperty(this, 0, tileEntity.progress);
		listener.sendWindowProperty(this, 1, tileEntity.totalTime);
		listener.sendWindowProperty(this, 2, tileEntity.fuel);
		listener.sendWindowProperty(this, 3, (int) tileEntity.heat);
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
			if (this.fuel != this.tileEntity.getField(2))
			{
				listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			}
			if (this.heat != this.tileEntity.getField(3))
			{
				listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
			}
		}
		
		this.processingTime = tileEntity.getField(0);
		this.totalTime = tileEntity.getField(1);
		this.fuel = tileEntity.getField(2);
		this.heat = tileEntity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		tileEntity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
	    ItemStack itemstack = ItemStack.EMPTY;
	    Slot slot = this.inventorySlots.get(index);

	    if (slot != null && slot.getHasStack())
	    {
	        ItemStack itemstack1 = slot.getStack();
	        itemstack = itemstack1.copy();

	        if (index == 2)
	        {
	            if (!this.mergeItemStack(itemstack1, 3, 39, true))
	            {
	                return ItemStack.EMPTY;
	            }
	            slot.onSlotChange(itemstack1, itemstack);
	        }
	        else if (index != 1 && index != 0)
	        {
	            if (!KilnRecipes.getOutput(itemstack1.getItem()).output.isEmpty())
	            {
	                if (!this.mergeItemStack(itemstack1, 0, 1, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            }
	            else if (TileEntityFurnace.isItemFuel(itemstack1))
	            {
	                if (!this.mergeItemStack(itemstack1, 1, 2, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            }
	            else if (index >= 3 && index < 30)
	            {
	            	if (!this.mergeItemStack(itemstack1, 30, 39, false))
	            	{
	            		return ItemStack.EMPTY;
	            	}
	            }
	            else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
	            {
	            	return ItemStack.EMPTY;
	            }
	        }
	        else if (!this.mergeItemStack(itemstack1, 3, 39, false))
	        {
	        	return ItemStack.EMPTY;
	        }
	        if (itemstack1.isEmpty())
	        {
	            slot.putStack(ItemStack.EMPTY);
	        }
	        else
	        {
	            slot.onSlotChanged();
	        }
	        if (itemstack1.getCount() == itemstack.getCount())
	        {
	            return ItemStack.EMPTY;
	        }
	        slot.onTake(playerIn, itemstack1);
    	}
	    return itemstack;
	}
}
