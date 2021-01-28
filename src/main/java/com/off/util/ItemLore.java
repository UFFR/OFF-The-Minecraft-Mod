package com.off.util;

import net.minecraft.item.Item;

public class ItemLore extends Item
{
	/* Lore tooltips */
	// Elemental lore, applys to only basic item forms as to not be superfluous
	public static String[] elementLore = 
	{
		"\"As the first of four elements... It's an important element. Because without smoke, people would have nothing to breathe.\"",
		"\"As the first of the four elements... It's an important element. Because without metal, people would have nothing to walk on. They would sink and drown.\"",
		"\"As the first of the four elements... It's an important element. Because without plastic, the world would have no boundaries. People would walk and walk without ever stopping.\"",
		"\"As the first of the four elements... It's an important element. Because without meat, people would have nothing to eat. They would die of starvation, one after another.\"",
		"\"It's a secret element... The fifth element... The most important element... Because without sugar, people could no longer bear reality, and they would go mad.\""
	};
	// Generic ttems
	public static String[] loreItem =
		{
				"Air-tight bottle",
				"I guess all we can do now is cremate it",
				"I think we're gonna need a bigger furnace",
				"Can be used for Half-Life roleplaying"
		};
	// Material type items (ie ingots)
	public static String[] loreMaterial =
		{
				"It is of desireable grade",
				"It is of poor grade",
				"It is still a semi-solid, therefore unusable",
				"It is raw and inedible",
				"It is cooked and looks edible",
				"It is inedible due to the metal infused within",
				"A metal nugget with lots of impurities, must be purified",
				"You can extract the metal",
				"Yeah, I wouldn't eat that"
		};
	// Generic blocks
	public static String[] loreBlock =
		{
				"Compressed low quality metal",
				"Metal coated bricks",
				"Weaker than metal blocks, but stronger than normal bricks",
				"A nice building material",
				"From a mysterious tree that lives in the Nothingness",
				"It has been depleted of smoke, it's like a brittle sponge",
				"A curious brightly colored cube...",
				"Its bright colors clash with the environment, going against any form of the plastic arts",
				"Yellow-colored rock unique to Zone 0, no benefit other than differing color choice"
		};
	// Machines and machine components
	public static String[] loreMachine =
		{
				"Industrial Furnace Component",
				"Squashes things into different forms"
		};
	// Consumables (but not food, that would be regular items or material if it's elemental meat)
	public static String[] loreConsumable =
		{
				"Restores a moderate amount of HP",
				"Restores a large amount of HP",
				"Restores a moderate amount of CP",
				"Restores a large amount of CP"
		};
	// Misc (usually trade items it seems)
	public static String[] loreMisc =
		{
				"Hey! You weren't supposed to obtain this!",
				"Can be used to hold fluid elements for trading",
				"Used for trading",
				"Large bottle of meat",
				"Bottle of smoke, quite simply",
				"Large bottle of plastic"
		};
}
