package com.off.inventory;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class Common
{
	public static abstract class AStack implements Comparable<AStack>
	{
		protected int stackSize;
		
		public boolean isApplicable(ItemStack stack)
		{
			return isApplicable(new NbtComparableStack(stack));
		}
		
		public AStack singularize()
		{
			stackSize = 1;
			return this;
		}
		
		public int count()
		{
			return stackSize;
		}
		
		public boolean isApplicable(ComparableStack comp)
		{
			if (this instanceof ComparableStack)
			{
				return ((ComparableStack)this).equals(comp);
			}
			
			if (this instanceof OreDictStack)
			{
				List<ItemStack> ores = OreDictionary.getOres(((OreDictStack)this).name);
				
				for (ItemStack stack : ores)
				{
					if (stack.getItem() == comp.item && stack.getItemDamage() == comp.meta)
						return true;
				}
			}
			return false;
		}
		
		public abstract AStack copy();
		public abstract ItemStack getStack();
		
		@Override
		public String toString()
		{
			return "AStack: size, " + stackSize;
		}
	}
	
	public static class ComparableStack extends AStack
	{
		Item item;
		int meta;
		
		public ComparableStack(ItemStack stack)
		{
			this.item = stack.getItem();
			this.stackSize = stack.getCount();
			this.meta = stack.getItemDamage();
		}
		
		public ComparableStack makeSingular()
		{
			stackSize = 1;
			return this;
		}
		
		@Override
		public AStack singularize()
		{
			stackSize = 1;
			return this;
		}
		
		public ComparableStack(Item item)
		{
			this.item = item;
			this.stackSize = 1;
			this.meta = 0;
		}
		
		public ComparableStack(Item item, int stackSize)
		{
			this(item);
			this.stackSize = stackSize;
		}
		
		public ComparableStack(Item item, int stackSize, int meta)
		{
			this(item, stackSize);
			this.meta = meta;
		}
		
		public ComparableStack(Block itemBlock, int stackSize)
		{
			this.item = Item.getItemFromBlock(itemBlock);
			this.stackSize = stackSize;
			this.meta = 0;
		}
		
		public ComparableStack(Block itemBlock, int stackSize, int meta)
		{
			this.item = Item.getItemFromBlock(itemBlock);
			this.stackSize = stackSize;
			this.meta = meta;
		}
		
		public ItemStack toStack()
		{
			return new ItemStack(item, stackSize, meta);
		}
		
		@Override
		public ItemStack getStack()
		{
			return toStack();
		}
		
		public String[] getDictKeys()
		{
			int[] ids = OreDictionary.getOreIDs(toStack());
			
			if (ids == null || ids.length == 0)
				return new String[0];
			
			String[] entries = new String[ids.length];
			
			for (int i = 0; i < ids.length; i++)
			{
				entries[i] = OreDictionary.getOreName(i);
			}
			
			return entries;
		}
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			ResourceLocation name = Item.REGISTRY.getNameForObject(item);
			if (name != null)
				result = prime * result + name.hashCode();
			result = prime * result + meta;
			result = prime * result + stackSize;
			return result;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ComparableStack))
				return false;
			ComparableStack other = (ComparableStack) obj;
			if (item == null)
			{
				if (other.item != null)
					return false;
			}
			else if (!item.equals(other.item))
				return false;
			if (meta != OreDictionary.WILDCARD_VALUE && other.meta != OreDictionary.WILDCARD_VALUE && other.meta != meta)
				return false;
			if (stackSize != other.stackSize)
				return false;
			return true;
		}
		
		@Override
		public int compareTo(AStack stack)
		{
			if (stack instanceof ComparableStack)
			{
				ComparableStack comp = (ComparableStack) stack;
				
				int thisID1 = Item.getIdFromItem(item);
				int thisID2 = Item.getIdFromItem(comp.item);
				
				if (thisID1 > thisID2)
					return 1;
				if (thisID1 < thisID2)
					return -1;
				
				if (meta > comp.meta)
					return 1;
				if (meta < comp.meta)
					return -1;
				
				return 0;
			}
			
			if (stack instanceof OreDictStack)
				return 1;
			
			return 0;
		}
		
		@Override
		public AStack copy()
		{
			return new ComparableStack(item, stackSize, meta);
		}
		
		@Override
		public String toString()
		{
			return "ComparableStack: item, " + item.getRegistryName() + ", meta, " + meta + ", stack size, " + stackSize;
		}
	}
	
	public static class NbtComparableStack extends ComparableStack
	{
		ItemStack stack;
		
		public NbtComparableStack(ItemStack stack)
		{
			super(stack);
			this.stack = stack.copy();
		}
		
		@Override
		public ComparableStack makeSingular()
		{
			ItemStack itemStack = stack.copy();
			itemStack.setCount(1);
			return new NbtComparableStack(itemStack);
		}
		
		@Override
		public AStack singularize()
		{
			stack.setCount(1);
			this.stackSize = 1;
			return this;
		}
		
		@Override
		public ItemStack toStack()
		{
			return stack.copy();
		}
		
		@Override
		public ItemStack getStack()
		{
			return toStack();
		}
		
		@Override
		public int hashCode()
		{
			if (!stack.hasTagCompound())
				return super.hashCode();
			else
				return super.hashCode() * 31 + stack.getTagCompound().hashCode();
		}
		
		@Override
		public AStack copy()
		{
			return new NbtComparableStack(stack);
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (!stack.hasTagCompound() || !(obj instanceof NbtComparableStack))
				return super.equals(obj);
			else
				return super.equals(obj) && tagContainsOther(stack.getTagCompound(), ((NbtComparableStack)obj).stack.getTagCompound());
		}
		
		@Override
		public String toString()
		{
			return "NbtComparableStack: " + stack.toString();
		}
	}
	
	public static class OreDictStack extends AStack
	{
		public String name;
		
		public OreDictStack(String name)
		{
			this.name = name;
			this.stackSize = 1;
		}
		
		public OreDictStack(String name, int stackSize)
		{
			this(name);
			this.stackSize = stackSize;
		}
		
		public List<ItemStack> toStacks()
		{
			return OreDictionary.getOres(name);
		}
		
		@Override
		public ItemStack getStack()
		{
			ItemStack itemStack = toStacks().get(0);
			return new ItemStack(itemStack.getItem(), stackSize, itemStack.getMetadata());
		}
		
		@Override
		public AStack singularize()
		{
			stackSize = 1;
			return this;
		}
		
		@Override
		public int compareTo(AStack stack)
		{
			if (stack instanceof OreDictStack)
			{
				OreDictStack comp = (OreDictStack) stack;
				return name.compareTo(comp.name);
			}
			
			if (stack instanceof ComparableStack)
			{
				return -1;
			}
			
			return 0;
		}
		
		@Override
		public AStack copy()
		{
			return new OreDictStack(name, stackSize);
		}
		
		@Override
		public String toString()
		{
			return "OreDictStack: name, " + name + ", stack size, " + stackSize;
		}
	}
	
	public static boolean tagContainsOther(NBTTagCompound tester, NBTTagCompound container)
	{
		if(tester == null && container == null)
			return true;
		if(tester == null ^ container == null)
			return false;
		else
			for(String s : tester.getKeySet())
			{
				if(!container.hasKey(s))
					return false;
				else
				{
					NBTBase nbt1 = tester.getTag(s);
					NBTBase nbt2 = container.getTag(s);
					if(nbt1 instanceof NBTTagCompound && nbt2 instanceof NBTTagCompound)
						if(!tagContainsOther((NBTTagCompound)nbt1, (NBTTagCompound) nbt2))
							return false;
					else
						if(!nbt1.equals(nbt2))
							return false;
				}
			}
		return true;
	}
}
