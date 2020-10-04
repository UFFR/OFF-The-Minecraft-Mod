package com.off.init;

import java.util.ArrayList;
import java.util.List;

import com.off.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class SoundRegistry
{
	public static List<SoundEvent> ALL_SOUNDS = new ArrayList<SoundEvent>();
	
	public static SoundEvent global;
	public static SoundEvent silencio;
	public static SoundEvent fourteenResidents;
	public static SoundEvent pepperSteak;
	
	public static void init()
	{
		// Regular
		global = register(":01_global");
		
		// Discs
		silencio = registerBypass(":02_silencio");
		fourteenResidents = registerBypass(":03_fourteen_residents");
		pepperSteak = registerBypass(":04_pepper_steak");
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
