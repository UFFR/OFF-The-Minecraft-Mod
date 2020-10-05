package com.off.init;

import java.util.ArrayList;
import java.util.List;

import com.off.items.ItemBase;
import com.off.items.ItemRecordCustom;

import net.minecraft.item.Item;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	//public static String defaultLore = null;
	
	/* Materials */
	// Metal items
	public static final Item INGOT_METAL = new ItemBase("ingot_metal");
	public static final Item NUGGET_METAL = new ItemBase("nugget_metal");
	public static final Item POOR_METAL = new ItemBase("poor_metal");
	public static final Item NUGGET_METAL_IMPURE = new ItemBase("nugget_metal_impure");
	public static final Item FRAGMENT_POOR_METAL = new ItemBase("fragment_poor_metal");
	public static final Item MEAT_METAL = new ItemBase("meat_metal");
	
	// Plastic items
	public static final Item INGOT_PLASTIC = new ItemBase("ingot_plastic");
	public static final Item INGOT_PLASTIC_RAW = new ItemBase("ingot_plastic_raw");
	public static final Item NUGGET_PLASTIC_RAW = new ItemBase("nugget_plastic_raw");
	public static final Item BOTTLE_PLASTIC = new ItemBase("bottle_plastic");
	
	// Meat items
	public static final Item MEAT_RAW = new ItemBase("meat_raw");
	public static final Item MEAT = new ItemBase("meat");
	public static final Item BOTTLE_MEAT = new ItemBase("bottle_meat");
	
	// Smoke items
	public static final Item BOTTLE_SMOKE = new ItemBase("bottle_smoke");
	
	// Sugar items
	public static final Item FLESH_ELSEN = new ItemBase("flesh_elsen");
	public static final Item FLESH_ELSEN_BURNED = new ItemBase("flesh_elsen_burned");
	public static final Item SUGAR = new ItemBase("sugar");
	
	/* Consumables */
	// Tickets
	public static final Item TICKET_LUCK = new ItemBase("ticket_luck");
	public static final Item TICKET_FORTUNE = new ItemBase("ticket_fortune");
	
	// Flesh
	public static final Item FLESH_SILVER = new ItemBase("flesh_silver");
	public static final Item FLESH_GOLD = new ItemBase("flesh_gold");
	
	/* Miscellaneous */
	// Misc items
	public static final Item LOGO = new ItemBase("logo");
	public static final Item BOTTLE_LARGE = new ItemBase("bottle_large");
	public static final Item BOTTLE_SEALED = new ItemBase("bottle_sealed");
	public static final Item CROWBAR = new ItemBase("crowbar");
	
	// Discs
	public static final Item DISC_SILENCIO = new ItemRecordCustom("disc_silencio", SoundRegistry.silencio, "Silencio");
	public static final Item DISC_FOURTEEN_RESIDENTS = new ItemRecordCustom("disc_fourteen_residents", SoundRegistry.fourteenResidents, "Fourteen Residents");
	public static final Item DISC_PEPPER_STEAK = new ItemRecordCustom("disc_pepper_steak", SoundRegistry.pepperSteak, "Pepper Steak");
	public static final Item DISC_NOT_SAFE = new ItemRecordCustom("disc_not_safe", SoundRegistry.notSafe, "Not Safe");
	public static final Item DISC_STAY_IN_YOUR_COMA = new ItemRecordCustom("disc_stay_in_your_coma", SoundRegistry.stayInYourComa, "Stay In Your Coma");
	public static final Item DISC_EMPTY_WAREHOUSE = new ItemRecordCustom("disc_empty_warehouse", SoundRegistry.emptyWarehouse, "Empty Warehouse");
	public static final Item DISC_TENDER_SUGAR = new ItemRecordCustom("disc_tender_sugar", SoundRegistry.tenderSugar, "Tender Sugar");
	public static final Item DISC_RAINY_DAY = new ItemRecordCustom("disc_rainy_day", SoundRegistry.rainyDay, "Rainy Day");
	public static final Item DISC_SOFT_BREEZE = new ItemRecordCustom("disc_soft_breeze", SoundRegistry.softBreeze, "Soft Breeze");
	public static final Item DISC_CLOCKWORK = new ItemRecordCustom("disc_clockwork", SoundRegistry.clockwork, "Clockwork");
	public static final Item DISC_FLESH_MAZE_TANGO = new ItemRecordCustom("disc_flesh_maze_tango", SoundRegistry.fleshMazeTango, "Flesh Maze Tango");
	public static final Item DISC_FAKE_ORCHESTRA = new ItemRecordCustom("disc_fake_orchestra", SoundRegistry.fakeOrchestra, "Fake Orchestra");
	
}
