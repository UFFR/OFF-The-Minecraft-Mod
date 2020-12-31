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
	public static final Item INGOT_METAL = new ItemBase("ingot_metal"); // Typical high grade metal
	public static final Item PLATE_METAL = new ItemBase("plate_metal"); // Compressed and stamped metal
	public static final Item NUGGET_METAL = new ItemBase("nugget_metal"); // Small amount of high grade metal
	public static final Item POOR_METAL = new ItemBase("poor_metal"); // Typical low grade metal
	public static final Item NUGGET_METAL_IMPURE = new ItemBase("nugget_metal_impure"); // Extracted from cows, high grade
	public static final Item FRAGMENT_POOR_METAL = new ItemBase("fragment_poor_metal"); // Small amounts of low grade metal
	public static final Item MEAT_METAL = new ItemBase("meat_metal"); // Extracted from cows, low grade
	
	// Plastic items
	public static final Item INGOT_PLASTIC = new ItemBase("ingot_plastic"); // Typical plastic
	public static final Item PLATE_PLASTIC = new ItemBase("plate_plastic"); // Extra compressed plastic
	public static final Item INGOT_PLASTIC_RAW = new ItemBase("ingot_plastic_raw"); // Semi-liquid plastic, must be compressed into a solid to be used in most recipes
	public static final Item NUGGET_PLASTIC_RAW = new ItemBase("nugget_plastic_raw"); // Small amount of raw plastic
	public static final Item BOTTLE_PLASTIC = new ItemBase("bottle_plastic"); // Bottle containing plastic, used in trading
	
	// Meat items
	public static final Item MEAT_RAW = new ItemBase("meat_raw"); // Raw meat
	public static final Item MEAT = new ItemBase("meat"); // Cooked meat, edible
	public static final Item BOTTLE_MEAT = new ItemBase("bottle_meat"); // Bottle containing meat, used in trading
	
	// Smoke items
	public static final Item BOTTLE_SMOKE = new ItemBase("bottle_smoke"); // Bottle containing smoke, item storage, also used in trading
	
	// Sugar items
	public static final Item FLESH_ELSEN = new ItemBase("flesh_elsen"); // From dead Elsen
	public static final Item FLESH_ELSEN_BURNED = new ItemBase("flesh_elsen_burned"); // From attempting to cook it in a regular furnace
	public static final Item SUGAR = new ItemBase("sugar"); // Put the sugar in the tube
	
	/* Consumables */
	// Tickets
	public static final Item TICKET_LUCK = new ItemBase("ticket_luck"); // Restores some health
	public static final Item TICKET_FORTUNE = new ItemBase("ticket_fortune"); // Restores a moderate amount of health
	
	// Flesh
	public static final Item FLESH_SILVER = new ItemBase("flesh_silver"); // Restores some competence points
	public static final Item FLESH_GOLD = new ItemBase("flesh_gold"); // Restores a moderate amounts of competence points
	
	/* Miscellaneous */
	// Misc items
	public static final Item LOGO = new ItemBase("logo").setCreativeTab(null); // Logo for the items creative tab
	public static final Item BOTTLE_LARGE = new ItemBase("bottle_large"); // Bottle for liquids
	public static final Item BOTTLE_SEALED = new ItemBase("bottle_sealed"); // Bottle for gases
	public static final Item CROWBAR = new ItemBase("crowbar"); // Crate opening device version 2.35.8 revision 4
	
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
