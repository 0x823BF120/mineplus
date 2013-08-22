package mineplus;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = MinePlus.modid, name = "MinePlus", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MinePlus {
	@Instance("MinePlus")
	public static MinePlus instance;
	public static final String modid = "Heartless_MinePlus";
	private GuiHandlerGrinder guiHandlerGrinder = new GuiHandlerGrinder();
	
	public static Block copperOre;
	public static Block tinOre;
	public static Block Limestone;
	public static Block blueriteOre;
	public static Block daeyaltOre;
	public static Block silverOre;
	public static Block bronzeOre;
	public static Block rubyOre;
	public static Block sapphireOre;
	public static Block emeraldOre;

	public static Block grinder;
	public static Block grinderActive;
	
	public static Item copperIngot;
	public static Item tinIngot;
	public static Item steelIngot;
	public static Item rubyGem;
	public static Item sapphireGem;
	public static Item emeraldGem;
	public static Item coalDust;
	public static Item ironDust;
	public static Item goldDust;
	public static Item diamondDust;

	public int global_block_id = 500;
	public int global_item_id = 5000;

	@SidedProxy(clientSide = "mineplus.ClientProxy", serverSide = "mineplus.CommonProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		copperOre = new MinePlusBlock(global_block_id + 1, Material.rock).setUnlocalizedName("copperOre");
		tinOre = new MinePlusBlock(global_block_id + 2, Material.rock).setUnlocalizedName("tinOre");
		bronzeOre = new MinePlusBlock(global_block_id + 4, Material.rock).setUnlocalizedName("bronzeOre");
		rubyOre = new MinePlusBlock(global_block_id + 5, Material.rock).setUnlocalizedName("rubyOre");
		sapphireOre = new MinePlusBlock(global_block_id + 6, Material.rock).setUnlocalizedName("sapphireOre");
		emeraldOre = new MinePlusBlock(global_block_id + 7, Material.rock).setUnlocalizedName("emeraldOre");

		grinder = new MinePlusGrinderBlock(global_block_id + 8, false);
		grinderActive = new MinePlusGrinderBlock (global_block_id+9, false);
		
		copperIngot = new MinePlusItem(global_item_id+1).setUnlocalizedName("copperIngot");
		tinIngot = new MinePlusItem(global_item_id+2).setUnlocalizedName("tinIngot");
		steelIngot = new MinePlusItem(global_item_id+3).setUnlocalizedName("steelIngot");
		rubyGem = new MinePlusItem(global_item_id+4).setUnlocalizedName("rubyGem");
		sapphireGem = new MinePlusItem(global_item_id+5).setUnlocalizedName("sapphireGem");
		emeraldGem = new MinePlusItem(global_item_id+6).setUnlocalizedName("emeraldGem");
		coalDust = new MinePlusItem(global_item_id+7).setUnlocalizedName("coalDust");
		ironDust = new MinePlusItem(global_item_id+8).setUnlocalizedName("ironDust");
		goldDust = new MinePlusItem(global_item_id+9).setUnlocalizedName("goldDust");
		diamondDust = new MinePlusItem(global_item_id+10).setUnlocalizedName("diamondDust");

		GameRegistry.registerBlock(copperOre, modid + copperOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(tinOre, modid + tinOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(bronzeOre, modid + bronzeOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(rubyOre, modid + rubyOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(sapphireOre, modid + sapphireOre.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(emeraldOre, modid + emeraldOre.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(copperIngot, modid + copperIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(tinIngot, modid + tinIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(steelIngot, modid + steelIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(rubyGem, modid + rubyGem.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(sapphireGem, modid + sapphireGem.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(emeraldGem, modid + emeraldGem.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(coalDust, modid + coalDust.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ironDust, modid + ironDust.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(goldDust, modid + goldDust.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(diamondDust, modid + diamondDust.getUnlocalizedName().substring(5));

		GameRegistry.registerBlock(grinder, modid + grinder.getUnlocalizedName().substring(5));
		
		GameRegistry.registerTileEntity(TileEntityGrinder.class,"tileEntityGrinder");
		
		//RenderingRegistry.registerBlockHandler(2105, Render.INSTANCE);
		NetworkRegistry.instance().registerGuiHandler(this, guiHandlerGrinder);		
		
		//GameRegistry.addShapelessRecipe(new ItemStack(copperIngot), new Object[] { new ItemStack(Item.diamond), new ItemStack(Block.stone), new ItemStack(Item.dyePowder, 1, 15) });
		
		//FurnaceRecipes.smelting().addSmelting(Item.beefCooked.itemID, 0, new ItemStack(Item.diamond), 0.1F);

		LanguageRegistry.addName(copperOre, "Copper Ore");
		LanguageRegistry.addName(tinOre, "Tin Ore");
		LanguageRegistry.addName(bronzeOre, "Bronze Ore");
		LanguageRegistry.addName(rubyOre, "Ruby Ore");
		LanguageRegistry.addName(sapphireOre, "Sapphire Ore");
		LanguageRegistry.addName(emeraldOre, "Emerald Ore");
		
		LanguageRegistry.addName(copperIngot, "Copper Ingot");
		LanguageRegistry.addName(tinIngot, "Tin Ingot");
		LanguageRegistry.addName(steelIngot, "Steel Ingot");
		LanguageRegistry.addName(rubyGem, "Ruby");
		LanguageRegistry.addName(sapphireGem, "Sapphire");
		LanguageRegistry.addName(emeraldGem, "Emerald");
		LanguageRegistry.addName(coalDust, "Coal Dust");
		LanguageRegistry.addName(ironDust, "Iron Dust");
		LanguageRegistry.addName(goldDust, "Gold Dust");
		LanguageRegistry.addName(diamondDust, "Diamond Dust");
		
		proxy.registerRenderThings();
	}
}