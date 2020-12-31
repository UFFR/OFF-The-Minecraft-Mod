package com.off.tileentity;

import com.off.blocks.machines.BlockCompactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompactor extends TileEntity implements ITickable
{
	private ItemStackHandler inventory = new ItemStackHandler(2);
	private String customName;
	private ItemStack processing = ItemStack.EMPTY;
	private int processingTime;
	private int totalTime = 200;
	
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName)
	{
		this.customName = customName;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.compactor");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("ProcessingTime", (short)this.processingTime);
		compound.setInteger("TotalTime", (short)this.totalTime);
		compound.setTag("Inventory", this.inventory.serializeNBT());
		
		if (this.hasCustomName()) compound.setString("CustomName", customName);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.processingTime = compound.getInteger("ProcessingTime");
		this.totalTime = compound.getInteger("TotalTime");
		
		if (compound.hasKey("CustomName", 8))
		{
			this.setCustomName(compound.getString("CustomName"));
		}
	}
	
	private boolean canProcess()
	{
		if (((ItemStack)this.inventory.getStackInSlot(0)).isEmpty())
			return false;
		else
		{
			ItemStack result = CompactorRecipes.getInstance().getCompactorResult((ItemStack)this.inventory.getStackInSlot(0));
			
			if (result.isEmpty())
				return false;
			else
			{
				ItemStack output = (ItemStack)this.inventory.getStackInSlot(1);
				if (output.isEmpty()) return false;
				if (!output.isItemEqual(result)) return false;
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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

	@Override
	public void update()
	{
		ItemStack input = inventory.getStackInSlot(0);
		
		if (this.canProcess() && processingTime > 0)
		{
			BlockCompactor.setState(true, this.world, this.pos);
			processingTime++;
			if (processingTime == totalTime)
			{
				if (inventory.getStackInSlot(1).getCount() > 0)
				{
					inventory.getStackInSlot(1).grow(1);
				}
				else
				{
					inventory.insertItem(3, processing, false);
				}
				
				processing = ItemStack.EMPTY;
				processingTime = 0;
				return;
			}
		}
		else
		{
			if (this.canProcess())
			{
				ItemStack output = CompactorRecipes.getInstance().getCompactorResult(input);
				if (!output.isEmpty())
				{
					BlockCompactor.setState(true, this.world, this.pos);
					processing = output;
					processingTime++;
					input.shrink(1);
					inventory.setStackInSlot(0, input);
				}
			}
		}
		BlockCompactor.setState(false, this.world, this.pos);
	}

}
