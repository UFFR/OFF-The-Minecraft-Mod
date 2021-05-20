package com.off.util;
/** EL - Element; IT - Item; MT - Material; BL - Block; MA - Machine; CO - Consumable; MISC - Miscellaneous **/
public enum EnumItemLore
{
	EL_SMOKE("smoke"),
	EL_METAL("metal"),
	EL_PLASTIC("plastic"),
	EL_MEAT("meat"),
	EL_SUGAR("sugar"),
	IT_TIGHT_BOTTLE("tightBottle"),
	IT_CREMATE("cremate"),
	IT_FURNACE("furnace"),
	IT_HL("hl"),
	MT_GOOD_METAL("goodMetal"),
	MT_POOR_METAL("badMetal"),
	MT_PLAS_RAW("rawPlastic"),
	MT_MEAT_RAW("rawMeat"),
	MT_MEAT_COOKED("cookedMeat"),
	MT_MEAT_METAL("metalMeat"),
	MT_METAL_IMP("impureMetal"),
	MT_METAL_EXT("extractableMetal"),
	MT_INEDIBLE("inedible"),
	BL_ROCK("rock"),
	BL_BRICK_METAL("brickMetal"),
	BL_DECO("deco"),
	BL_TREE("tree"),
	BL_EMPTY_ORE("smokeOreEmpty"),
	BL_CUBE_YELLOW("cubeYellow"),
	BL_CUBE_RED("cubeRed"),
	BL_ROCK_YELLOW("rockYellow"),
	MA_COMPACTOR("compactor"),
	MA_KILN("kiln"),
	CO_HP1("hp1"),
	CO_HP2("hp2"),
	CO_CP1("cp1"),
	CP_CP2("cp2"),
	MISC_TECHNICAL("technical"),
	MISC_FLUID_TRADING("tradingFluid"),
	MISC_TRADING("trading"),
	MISC_BOTTLE_MEAT("bottleMeat"),
	MISC_BOTTLE_SMOKE("bottleSmoke"),
	MISC_BOTTLE_PLASTIC("bottlePlasic");
	public String key;
	private EnumItemLore(String keyIn)
	{
		String enumString = this.toString().toLowerCase().substring(0, 2);
		String prefix;
		switch(enumString)
		{
		case "el":
			prefix = "element";
			break;
		case "it":
			prefix = "item";
			break;
		case "mt":
			prefix = "material";
			break;
		case "bl":
			prefix = "block";
			break;
		case "ma":
			prefix = "machine";
			break;
		case "co":
			prefix = "consumable";
			break;
		default:
			prefix = "miscellaneous";
			break;
		}
		key = String.format("lore.%s.%s", prefix, key);
	}
}
