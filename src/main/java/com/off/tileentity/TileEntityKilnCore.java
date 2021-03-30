package com.off.tileentity;

import com.off.init.ModBlocks;
import com.off.inventory.KilnRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
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
	public boolean hasKilnRecipe;
	public boolean hasFurnaceRecipe;
	public int fuel = 0; // Not sure if it will count in furnace operations or burn time in ticks, probably ticks right?
	public int progress = 0;
	public boolean active = false;
	public float heat = 20.0F; // Room temperature
	public static int maxHeat = 1500; // Anything hotter would be a bit unrealistic for a simple furnace/kiln
	public static int maxFuel = 64000; // Open to change during testing
	public ItemStackHandler inventory;
	public int totalTime = 200; // Base temp
	
	public String errorPoint;
	private String customName;
	
	public TileEntityKilnCore()
	{
		inventory = new ItemStackHandler(3)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				System.out.println("[KILN] Contents changed, marking dirty");
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public void updateTotalTime()
	{
		if (totalTime - 10 * ((int) heat - totalTime) > 50)
		{
			totalTime = totalTime - 10 * ((int) heat - totalTime);
		}
		else
		{
			totalTime = 50;
		}
	}
	
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return progress;
		case 1:
			return totalTime;
		case 2:
			return fuel;
		case 3:
			return (int)heat;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0:
			progress = value;
			break;
		case 1:
			totalTime = value;
			break;
		case 2:
			fuel = value;
			break;
		case 3:
			heat = value;
			break;
		}
	}
	
	// Check the blocks that make up the structure
	public boolean isStructureValid(World worldIn)
	{
		// Base coordinates
		BlockPos checkPos = pos;// Coordinates to check blocks with
		errorPoint = null;
		/* Components */
		// Check outer shell
		// Front and back
		for (int x = -3; x <= 3; x++)
		{
			for (int y = -3; y <= 3; y++)
			{
				checkPos = pos.add(x, y, 3);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL && !(x == 0 && y == 0))
				{
					errorPoint = String.format("[OUTER SHELL, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #1]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				else if ((x == 0 && y == 0) && worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_ACCESS)
					{
						errorPoint = String.format("[OUTER SHELL, (%s); WAS NOT CASING BLOCK, THEREFORE EXPECTED: [%s], GOT: [%s]; CHECK #1", checkPos, ModBlocks.FURNACE_ACCESS, worldIn.getBlockState(checkPos).getBlock());
						return false;
					}
				}
				checkPos = pos.add(x, y, -3);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL && !(x == 0 && y == 0))
				{
					errorPoint = String.format("[OUTER SHELL, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #2]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				else if ((x == 0 && y == 0) && worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_ACCESS)
					{
						errorPoint = String.format("[OUTER SHELL, (%s); WAS NOT CASING BLOCK, THEREFORE EXPECTED: [%s], GOT: [%s]; CHECK #2", checkPos, ModBlocks.FURNACE_ACCESS, worldIn.getBlockState(checkPos).getBlock());
						return false;
					}
				}
			}
		}
		// Top and bottom
		for (int x = -3; x <= 3; x++)
		{
			for (int z = -3; z <= 3; z++)
			{
				checkPos = pos.add(x, 3, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #3]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(x, 3, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					errorPoint = String.format("[OUTER SHELL, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #4]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Left and right
		for (int y = -3; y <= 3; y++)
		{
			for (int z = -3; z <= 3; z++)
			{
				checkPos = pos.add(3, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL && !(y == 0 && z == 0))
				{
					errorPoint = String.format("[OUTER SHELL, (%s), CHECK #5]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				else if ((y == 0 && z == 0) && worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_ACCESS)
					{
						errorPoint = String.format("[OUTER SHELL, (%s); WAS NOT CASING BLOCK, THEREFORE EXPECTED: [%s], GOT: [%s]; CHECK #5", checkPos, ModBlocks.FURNACE_ACCESS, worldIn.getBlockState(checkPos).getBlock());
						return false;
					}
				}
				checkPos = pos.add(-3, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL && !(y == 0 && z == 0))
				{
						errorPoint = String.format("[OUTER SHELL, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #6]", checkPos, ModBlocks.BRICKS_METAL, worldIn.getBlockState(checkPos).getBlock());
						return false;
				}
				else if ((y == 0 && z == 0) && worldIn.getBlockState(checkPos).getBlock() != ModBlocks.BRICKS_METAL)
				{
					if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_ACCESS)
					{
						errorPoint = String.format("[OUTER SHELL, (%s); WAS NOT CASING BLOCK, THEREFORE EXPECTED: [%s], GOT: [%s]; CHECK #6", checkPos, ModBlocks.FURNACE_ACCESS, worldIn.getBlockState(checkPos).getBlock());
						return false;
					}
				}
			}
		}
		// Heat shielding
		// Front and back
		for (int x = -2; x <= 2; x++)
		{
			for (int y = -2; y <= 2; y++)
			{
				checkPos = pos.add(x, y, 2);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #7]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(x, y, -2);
				if (worldIn.getBlockState(pos.add(x, y, -2)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #8]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Top and bottom
		for (int x = -2; x <= 2; x++)
		{
			for (int z = -2; z <= 2; z++)
			{
				checkPos = pos.add(x, 2, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #9]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(x, -2, z);
				if (worldIn.getBlockState(pos.add(x, -2, z)).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK 10]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Left and right
		for (int y = -2; y <= 2; y++)
		{
			for (int z = -2; z <= 2; z++)
			{
				checkPos = pos.add(2, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #11]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(-2, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.HEAT_SHIELDING)
				{
					errorPoint = String.format("[HEAT SHIELDING, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #12]", checkPos, ModBlocks.HEAT_SHIELDING, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Core components
		// Front and back
		for (int x = -1; x <= 1; x++)
		{
			for (int y = -1; y <= 1; y++)
			{
				checkPos = pos.add(x, y, 1);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #13]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(x, y, -1);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #14]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Top and bottom
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				checkPos = pos.add(x, 1, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #15]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(x, -1, z);
				if (worldIn.getBlockState(pos.add(x, -1, z)).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #16]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
			}
		}
		// Left and right
		for (int y = -1; y <= 1; y++)
		{
			for (int z = -1; z <= 1; z++)
			{
				checkPos = pos.add(1, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #17]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
					return false;
				}
				checkPos = pos.add(-1, y, z);
				if (worldIn.getBlockState(checkPos).getBlock() != ModBlocks.FURNACE_CORE_COMPONENT)
				{
					errorPoint = String.format("[CORE COMPONENTS, (%s); EXPECTED: [%s], GOT: [%s]; CHECK #18]", checkPos, ModBlocks.FURNACE_CORE_COMPONENT, worldIn.getBlockState(checkPos).getBlock());
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
			return true;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		progress = compound.getShort("cookTime");
		fuel = compound.getInteger("fuel");
		heat = compound.getInteger("heat");
		if (compound.hasKey("inventory"))
		{
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
			super.readFromNBT(compound);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("cookTime", (short) progress);
		compound.setInteger("fuel", fuel);
		compound.setFloat("heat", heat);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public boolean hasHeat()
	{
		if (hasKilnRecipe)
		{
			System.out.println("[KILN] Heat required for recipe: " + KilnRecipes.getOutput(inventory.getStackInSlot(1).getItem()).heat);
			return KilnRecipes.getOutput(inventory.getStackInSlot(1).getItem()).heat <= heat;
		}
		else
		{
			System.out.println("[KILN] Heat required for recipe: 350");
			return heat >= 350;
		}
	}
	
	public boolean isProcessing()
	{
		return progress > 0;
	}
	
	public int getProgressScaled(int i)
	{
		return (int) ((progress * i) / totalTime);
	}
	
	public int getFuelRemainingScaled(int i)
	{
		return ((fuel * i) / maxFuel);
	}
	
	public int getHeatRemainingScaled(int i)
	{
		return (int)((heat * i) / maxHeat);
	}
	
	public boolean isBurning()
	{
		return getField(2) > 0;
	}
	
	public boolean canProcess()
	{
		if (inventory.getStackInSlot(1).isEmpty())
		{
			System.out.println("[KILN] Input is empty");
			return false;
		}
		else
		{
			hasFurnaceRecipe = !FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(1)).isEmpty();
			hasKilnRecipe = false;
			ItemStack recipe;
			if (KilnRecipes.getOutput(inventory.getStackInSlot(1).getItem()) != null)
			{
				System.out.println("[KILN] Has kiln recipe");
				hasKilnRecipe = true;
			}
			
			if (hasKilnRecipe)
			{
				recipe = KilnRecipes.getOutput(inventory.getStackInSlot(1).getItem()).output;
				System.out.println("[KILN] Kiln recipe: " + recipe.getItem());
			}
			else if (hasFurnaceRecipe)
			{
				System.out.println("[KILN] No kiln recipe, using furnace recipe");
				recipe = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(1));
				System.out.println("[KILN] Furnace recipe: " + recipe.getItem());
			}
			else
			{
				recipe = null;
			}
			
			if (recipe == null || recipe.isEmpty())
			{
				System.out.println("[KILN] No recipe at all");
				return false;
			}
			else
			{
				if (inventory.getStackInSlot(1).isEmpty())
				{
					return true;
				}
				else if (!inventory.getStackInSlot(1).isItemEqual(recipe))
				{
					return false;
				}
				else if (inventory.getStackInSlot(2).getCount() < inventory.getSlotLimit(1) && inventory.getStackInSlot(2).getCount() < inventory.getStackInSlot(2).getMaxStackSize())
				{
					return true;
				}
				else
				{
					return (inventory.getStackInSlot(2).getCount() < recipe.getMaxStackSize()) && (inventory.getSlotLimit(2) < recipe.getCount());
				}
			}
		}
	}
	
	private void processItem()
	{
		if (canProcess() && hasHeat())
		{
			ItemStack itemStack;
			boolean processingSuccessful = false;
			
			if (hasKilnRecipe)
			{
				itemStack = KilnRecipes.getOutput(inventory.getStackInSlot(1).getItem()).output;
			}
			else
			{
				itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(1));
			}
			
			if (inventory.getStackInSlot(2).isEmpty())
			{
				System.out.println("[KILN] Processing successful, new item stack in output slot");
				inventory.setStackInSlot(2, itemStack.copy());
				processingSuccessful = true;
			}
			else if (inventory.getStackInSlot(2).isItemEqual(itemStack))
			{
				System.out.println("[KILN] Processing successful, current item stack grown by: " + itemStack.getCount());
				inventory.getStackInSlot(2).grow(itemStack.getCount());
				processingSuccessful = true;
			}
			if (processingSuccessful)
			{
				System.out.println("[KILN] Input slot shrunk by 1");
				inventory.getStackInSlot(1).shrink(1);
			}
		}
	}

	@Override
	public void update()
	{
		// FIXME Everything
		updateTotalTime();
		boolean dirty = false;
		if (!world.isRemote && isStructureValid(world))
		{
			ItemStack fuelSlot = inventory.getStackInSlot(0);
			if (TileEntityFurnace.isItemFuel(fuelSlot) && (maxFuel >= fuel + TileEntityFurnace.getItemBurnTime(fuelSlot)))
			{
				//System.out.println("[KILN] Item in input slot is fuel, identified as: " + fuelSlot.getItem());
				//System.out.println("[KILN] Item fuel level: " + TileEntityFurnace.getItemBurnTime(fuelSlot));
				fuel += TileEntityFurnace.getItemBurnTime(fuelSlot);
				//System.out.println("[KILN] Current fuel level: " + fuel);
				if (fuelSlot.getItem().getRegistryName().toString().contains("bucket"))
				{
					fuelSlot.shrink(1);
					inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
				}
				else
				{
					fuelSlot.shrink(1);
				}
				dirty = true;
			}
			
			if (isBurning())
			{
				fuel -= 2;
				//System.out.println("[KILN] Is burning, fuel level decreased and is now: " + fuel);
				if (heat + 1.25F > maxHeat)
				{
					//System.out.println("[KILN] Heat maxed out");
					heat = maxHeat;
				}
				else
				{
					//System.out.println("[KILN] Heat increased and is now: " + heat);
					heat += 1.25F;
				}
			}
			else
			{
				if (heat - 0.5F < 20)
				{
					heat = 20.0F;
				}
				else
				{
					//System.out.println("[KILN] Heat decreased and is now: " + heat);
					heat -= 0.5F;
				}
			}
			
			if (hasHeat() && canProcess())
			{
				System.out.println("[KILN] Can process, will");
				progress++;	
				heat -= 0.75F;
				
				if (progress == totalTime)
				{
					System.out.println("[KILN] Processing...");
					progress = 0;
					this.processItem();
					dirty = true;
				}
				
				boolean trigger = true;
				
				if (hasHeat() && canProcess() && progress == 0)
				{
					trigger = false;
				}
				
				if (trigger)
				{
					dirty = true;
				}
				if (dirty)
				{
					markDirty();
				}
			}
			else
			{
				progress = 0;
			}
			
			if (heat < 20)
			{
				heat = 20;
			}
			
			if (fuel < 0)
			{
				fuel = 0;
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
