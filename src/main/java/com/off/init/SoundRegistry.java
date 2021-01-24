package com.off.init;

import java.util.ArrayList;
import java.util.List;

import com.off.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class SoundRegistry
{
	public static List<SoundEvent> ALL_SOUNDS = new ArrayList<SoundEvent>();
	
	public static SoundEvent nullSound;
	public static SoundType nullBlock;
	public static SoundEvent global;
	public static SoundEvent silencio;
	public static SoundEvent fourteenResidents;
	public static SoundEvent pepperSteak;
	public static SoundEvent pepperSteakLoop;
	public static SoundEvent notSafe;
	public static SoundEvent stayInYourComa;
	public static SoundEvent emptyWarehouse;
	public static SoundEvent tenderSugar;
	public static SoundEvent rainyDay;
	public static SoundEvent softBreeze;
	public static SoundEvent clockwork;
	public static SoundEvent fleshMazeTango;
	public static SoundEvent fakeOrchestra;
	
	public static void init()
	{
		/* Regular */
		// Entity sounds
		global = register("01_global");

		// Ambient sounds
		notSafe = register("05_not_safe");
		rainyDay = register("09_rainy_day");
		softBreeze = register("10_soft_breeze");
		emptyWarehouse = register("07_empty_warehouse");
		
		// Discs
		silencio = registerBypass("02_silencio");
		fourteenResidents = registerBypass("03_fourteen_residents");
		pepperSteak = registerBypass("04_pepper_steak");
		stayInYourComa = registerBypass("06_stay_in_your_coma");
		tenderSugar = registerBypass("08_tender_sugar");
		clockwork = registerBypass("11_clockwork");
		fleshMazeTango = registerBypass("12_flesh_maze_tango");
		fakeOrchestra = registerBypass("13_fake_orchestra");
		
		/* Special */
		// Battle sounds
		pepperSteakLoop = register("pepper_steak_loop");
		// Misc
		nullSound = register("null_sound");
		nullBlock = new SoundType(1.0F, 1.0F, nullSound, nullSound, nullSound, nullSound, nullSound);
}
	
	public static SoundEvent register(String name)
	{
		SoundEvent event = new SoundEvent(new ResourceLocation(Reference.MODID, name));
		event.setRegistryName(name);
		ALL_SOUNDS.add(event);
		return event;
	}
	
	public static SoundEvent registerBypass(String name)
	{
		SoundEvent event = new SoundEvent(new ResourceLocation(Reference.MODID, name));
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
