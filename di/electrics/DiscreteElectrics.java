package kappafox.di.electrics;

import ic2.api.item.IC2Items;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.lib.IC2Data;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.blocks.BlockDiscreteCable;
import kappafox.di.electrics.blocks.items.ItemDiscreteCableBlock;
import kappafox.di.electrics.items.ItemHorseInspector;
import kappafox.di.electrics.renderers.DiscreteRenderManager;
import kappafox.di.electrics.tileentities.TileEntityDiscreteCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class DiscreteElectrics
{
	
	//blocks
	public static Block discreteCableBlock;
	
	//items
	public static Item horseInspector;
	
	//ids
	//public static int discreteCableID;
	//public static int discreteElectricItemID;
	
	//Render IDS
	private static int renderID;

	public static int discreteCableModelID;
	
	
	public DiscreteElectrics( )
	{
		
	}
	
	public void load(FMLInitializationEvent event_)
	{
		//Renderers
		registerRenderers();
		
		//Blocks
		registerBlocks();
		
		//Items
		this.registerItems();
		//Recipies
		registerRecipies();
		
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityDiscreteCable.class, "Cable Block");
		GameRegistry.registerTileEntity(TileEntityDiscreteBlock.class, "Discrete Block");
	}

	private void registerItems( ) 
	{
		horseInspector = new ItemHorseInspector("Horse Inspector", 1);
		GameRegistry.registerItem(horseInspector, "Horse Inspector");
	}

	public void preInitialisation(FMLPreInitializationEvent event, Configuration config)
	{
		//discreteElectricItemID = config.getItem("DiscreteElectricItems", ids.itemDiscreteItem).getInt(ids.itemDiscreteItem);
		//discreteCableID = config.getBlock("DiscreteCables", ids.discreteCable).getInt(ids.discreteCable);
	}
	
	private void registerBlocks( )
	{
		//grab the IC2 name stuff first
		//IC2Data ic2 = DiscreteIndustry.librarian.ic2;
		
		//initialise all our block objects
		discreteCableBlock = new BlockDiscreteCable(Material.anvil, discreteCableModelID);
		
		//add them to the game registry
		GameRegistry.registerBlock(discreteCableBlock, ItemDiscreteCableBlock.class, DiscreteIndustry.MODID + "cableBlock");
		
		//And finally for meta blocks add the subblocks
		
		String prefix = "Discrete ";
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 0), prefix + IC2Data.cableName[0]);
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 1), prefix + IC2Data.cableName[1]);
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 2), prefix + IC2Data.cableName[2]);
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 3), prefix + IC2Data.cableName[3]);
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 4), prefix + IC2Data.cableName[4]);
		LanguageRegistry.addName(new ItemStack(discreteCableBlock, 1, 5), prefix + IC2Data.cableName[5]);
	}
	
	private void registerRenderers( )
	{
		renderID = RenderingRegistry.getNextAvailableRenderId();
		discreteCableModelID = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(discreteCableModelID, new DiscreteRenderManager(renderID));
	}
	
	//OreDictionary n = new OreDictionary();

	private void registerRecipies( )
	{
		ItemStack frameDiscreteCable = new ItemStack(discreteCableBlock, 24, 0);
		ItemStack frameDiscreteCable24 = new ItemStack(discreteCableBlock, 24, 0);
		ItemStack frameDiscreteCable32 = new ItemStack(discreteCableBlock, 32, 0);
		ItemStack frameDiscreteCable1 = new ItemStack(discreteCableBlock, 1, 0);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(horseInspector, new Object[]{ "X X", "ZYZ", "ZMZ", 'X', Items.iron_ingot, 'Z', "itemRubber", 'Y', Blocks.glass, 'M', IC2Items.getItem("electronicCircuit")}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(frameDiscreteCable, new Object[]{ "X X", " Y ", "X X", 'Y', Items.iron_ingot, 'X', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(frameDiscreteCable24, new Object[]{ "X X", " Y ", "X X", 'Y', IC2Items.getItem("plateiron"), 'X', "itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(frameDiscreteCable32, new Object[]{ "X X", " Y ", "X X", 'Y', Items.gold_ingot, 'X', "itemRubber"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(discreteCableBlock, 1, 1), new Object[]{frameDiscreteCable1, IC2Items.getItem("tinCableItem")}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(discreteCableBlock, 1, 2), new Object[]{frameDiscreteCable1, IC2Items.getItem("insulatedCopperCableItem")}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(discreteCableBlock, 1, 3), new Object[]{frameDiscreteCable1, IC2Items.getItem("doubleInsulatedGoldCableItem")}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(discreteCableBlock, 1, 4), new Object[]{frameDiscreteCable1, IC2Items.getItem("glassFiberCableItem")}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(discreteCableBlock, 1, 5), new Object[]{frameDiscreteCable1, IC2Items.getItem("trippleInsulatedIronCableItem")}));
	}
}
