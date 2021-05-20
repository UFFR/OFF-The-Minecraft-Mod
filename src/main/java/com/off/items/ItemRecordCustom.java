package com.off.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.off.MainInit;
import com.off.init.ModItems;
import com.off.util.EnumItemLore;
import com.off.util.IItemLore;

import net.minecraft.block.BlockJukebox;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRecordCustom extends ItemRecord implements IItemLore
{
	EnumRarity rarity = EnumRarity.RARE;
	boolean hasEffect = false;
	private List<EnumItemLore> loreList = new ArrayList<EnumItemLore>();
	private static final Map<String, ItemRecordCustom> modRecords = new HashMap<String, ItemRecordCustom>();
	public final String recordName;
	/**
	 * Custom record constructor
	 * @param name - Registry name
	 * @param soundIn - Sound to play
	 * @param itemName - Record name
	 */
	public ItemRecordCustom(String name, SoundEvent soundIn, String itemName)
	{
		super(itemName, soundIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(MainInit.tabOFFMusic);
		recordName = itemName;
		modRecords.put(itemName, this);
		ModItems.ITEMS.add(this);
		ModItems.RECORDS.add(this);
	}
	
	//@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY)
	{
		if (worldIn.getBlockState(pos).getBlock() == Blocks.JUKEBOX && !worldIn.getBlockState(pos).getValue(BlockJukebox.HAS_RECORD).booleanValue())
		{
			if (worldIn.isRemote)
			{
				return EnumActionResult.SUCCESS;
			}
			else
			{
				ItemStack stack = player.getHeldItem(hand);
				((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, worldIn.getBlockState(pos), stack);
				worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(this));
				stack.shrink(1);
				player.addStat(StatList.RECORD_PLAYED);
				return EnumActionResult.SUCCESS;
			}
		}
		else
		{
			return EnumActionResult.PASS;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(this.getRecordNameLocal());
		tooltip.add("Track #" + (ModItems.RECORDS.indexOf(this) + 2));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordNameLocal()
	{
		return I18n.format("item.record." + this.recordName + ".desc");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return rarity;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return hasEffect;
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
	{
		return (I18n.format(Items.RECORD_11.getUnlocalizedName() + ".name")).trim();
	}

	@Override
	public ItemRecordCustom addLore(EnumItemLore... loreIn)
	{
		for (EnumItemLore lore : loreIn)
			loreList.add(lore);
		return this;
	}

	@Override
	public IItemLore setHasEffect()
	{
		hasEffect = true;
		return this;
	}

	@Override
	public IItemLore setRarity(EnumRarity rarityIn)
	{
		rarity = rarityIn;
		return this;
	}
}
