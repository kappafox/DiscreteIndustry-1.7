package kappafox.di.decorative;

import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.lib.DiscreteID;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.blocks.BlockHazard;
import kappafox.di.decorative.blocks.items.ItemDiscreteDecorBlock;
import kappafox.di.decorative.blocks.items.ItemDiscreteHazardBlock;
import kappafox.di.decorative.renderers.DiscreteDecorativeItemRenderer;
import kappafox.di.decorative.renderers.DiscreteDecorativeRenderManager;
import kappafox.di.decorative.renderers.TileEntitySwordRackRenderer;
import kappafox.di.decorative.tileentities.TileEntityBridgeBlock;
import kappafox.di.decorative.tileentities.TileEntityHazardBlock;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import kappafox.di.decorative.tileentities.TileEntityStripHazardBlock;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DiscreteDecorative
{
	
	//Blocks
	public static Block hazardBlock;
	public static Block decorBlock;
	
	//Items
	
	//RIDS
	public static int hazardRenderID;
	public static int decorRenderID;
	
	//tests
	
	//Listeners
	private static DiscreteDecorativeEventHandler events;
	
	public DiscreteDecorative( )
	{
		events = new DiscreteDecorativeEventHandler();
	}
	
	public void load(FMLInitializationEvent event_)
	{
		//Renderers
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			registerRenderers();
		}
		
		//Blocks
		registerBlocks();
		
		//Recipies
		registerRecipies();
		
		//Event Handler
		MinecraftForge.EVENT_BUS.register(events);
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityHazardBlock.class, "Hazard Block");
		GameRegistry.registerTileEntity(TileEntityStripHazardBlock.class, "Hazard Strip Block");
		GameRegistry.registerTileEntity(TileEntityLoomBlock.class, "DD Loom Block");
		GameRegistry.registerTileEntity(TileEntitySwordRack.class, "DD Sword Rack");
		GameRegistry.registerTileEntity(TileEntitySidedConnector.class, "DD Fixture");
		GameRegistry.registerTileEntity(TileEntityBridgeBlock.class, "DD Bridge Tile Entity");
		
		//register actual entities
		//EntityRegistry.registerGlobalEntityID(EntityWorldItem.class, "EntWorldItem", EntityRegistry.findGlobalUniqueEntityId());		
		//EntityRegistry.registerModEntity(EntityWorldItem.class, "EntWorldItem", 0, this, 350, 5, false);
		
	    DiscreteDecorativePacketHandler.initPackets();
	}


	public void preInitialisation(FMLPreInitializationEvent event, Configuration config)
	{
		//grab the id database
		DiscreteID ids = DiscreteIndustry.librarian.dibi;
		
		//hazardBlockID = config.getBlock("DiscreteHazard", ids.discreteHazard).getInt(ids.discreteHazard);
		//decorBlockID = config.getBlock("DiscreteDecor", ids.discreteDecor).getInt(ids.discreteDecor);
	}
	
	private void registerBlocks( )
	{
		hazardBlock = new BlockHazard(Material.rock, hazardRenderID);
		decorBlock = new BlockDecor(Material.rock, decorRenderID);
		
		GameRegistry.registerBlock(hazardBlock, ItemDiscreteHazardBlock.class, DiscreteIndustry.MODID + "hazardBlock");
		GameRegistry.registerBlock(decorBlock, ItemDiscreteDecorBlock.class, DiscreteIndustry.MODID + "decorBlock");
		
		//Hazard Blocks
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 0), "Diagonal Hazard Block");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 1), "Arrow Hazard Block");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 2), "Red Diagonal Hazard Block");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 3), "Red Arrow Hazard Block");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 4), "Checkered Hazard Block");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 5), "Small Checkered Hazard Block");
		
		//Hazard Blocks with complex stuff
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 6), "Hazard Strip");
		LanguageRegistry.addName(new ItemStack(hazardBlock, 1, 7), "Angled Hazard Strip");
		
		//Decor Blocks
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 0), "Overhead Fixture");
		//LanguageRegistry.addName(new ItemStack(decorBlock, 1, 1), "Industrial Ladder");
		//LanguageRegistry.addName(new ItemStack(decorBlock, 1, 2), "Classic Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 3), "Loom");
		//LanguageRegistry.addName(new ItemStack(decorBlock, 1, 4), "Sword Rack");
		
		//Overloaded Meta Decor Blocks
		//800-820 Ladders
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 800), "Foothold Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 801), "Pole Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 802), "Simple Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 803), "Rope Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 804), "Fixed Plank Ladder");		
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 806), "Classic Ladder");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 807), "Industrial Ladder");
		
		//821-840 Sword/Tool Racks
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 821), "Sword Rest");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 822), "Sword Rack");
		
		//841-860 Doors/Hatches
		
		//861-870 Stairs
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 861), "Discrete Stairs");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 862), "Discrete Small Stairs");
		
		//871-880 Struts etc
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 871), "2x2 Strut");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 872), "4x4 Strut");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 873), "6x6 Strut");
		
		//881-890 Simple shapes
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 881), "Discrete Slab");
		
		//900-999 Walls, Fences, Bridges, Catawalks
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, BlockDecor.ID_WALL_DISCRETE), "Discrete Wall");
		
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, BlockDecor.ID_WALL_RAILING_SIMPLE), "Simple Railing");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, BlockDecor.ID_WALL_RAILING_DOUBLE), "Double Railing");
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, BlockDecor.ID_WALL_RAILING_TRIPLE), "Triple Railing");
		
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 913), "Square Railing");
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 914), "Square Halved Railing");
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 915), "Triple Quartered Railing");
	    
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 916), "Danger Tape");
	    
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 950), "Square Panel");
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 951), "Halved Square Panel");
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, 952), "Quartered Square Panel");
	    
	    //1000 - 1100 Catwalks, bridges
	    LanguageRegistry.addName(new ItemStack(decorBlock, 1, BlockDecor.ID_BRIDGE_CATWALK_SIMPLE), "Simple Catwalk");
	    
		//Tests
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 805), "Flag Yolo Test");		
		
		LanguageRegistry.addName(new ItemStack(decorBlock, 1, 888), "Test Item");
	}
	
	@SideOnly(Side.CLIENT)
	private void registerRenderers( )
	{
		int renderID = RenderingRegistry.getNextAvailableRenderId();
		hazardRenderID = RenderingRegistry.getNextAvailableRenderId();
		decorRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		DiscreteDecorativeRenderManager manager = new DiscreteDecorativeRenderManager(renderID);
		
		RenderingRegistry.registerBlockHandler(hazardRenderID, manager);
		RenderingRegistry.registerBlockHandler(decorRenderID, manager);

		DiscreteDecorativeItemRenderer r1 = new DiscreteDecorativeItemRenderer();
		//MinecraftForgeClient.registerItemRenderer(decorBlockID, r1);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwordRack.class, new TileEntitySwordRackRenderer());
		
		//RenderingRegistry.registerEntityRenderingHandler(EntityWorldItem.class, new RenderItemFrame());
	}

	private void registerRecipies( )
	{
		ItemStack frameDiscreteCable = new ItemStack(DiscreteElectrics.discreteCableBlock, 1, 0);
		
		ItemStack yellowDiagonalHazard = new ItemStack(hazardBlock, 8, 0);
		ItemStack yellowArrowHazard = new ItemStack(hazardBlock, 8, 1);
		ItemStack redDiagonalHazard = new ItemStack(hazardBlock, 8, 2);
		ItemStack redArrowHazard  = new ItemStack(hazardBlock, 8, 3);
		ItemStack checkeredYellowHazard  = new ItemStack(hazardBlock, 8, 4);
		ItemStack singleCheckeredYellowHazard  = new ItemStack(hazardBlock, 1, 4);
		ItemStack smallcheckeredYellowHazard  = new ItemStack(hazardBlock, 4, 5);
		ItemStack hazardStrip8 = new ItemStack(hazardBlock, 8, 6);
		ItemStack angledHazardStrip8 = new ItemStack(hazardBlock, 8, 7);
		ItemStack fixture = new ItemStack(decorBlock, 1, 0);
		ItemStack strut22 = new ItemStack(decorBlock, 1, 871);
		ItemStack strut44 = new ItemStack(decorBlock, 1, 872);
		ItemStack strut444 = new ItemStack(decorBlock, 4, 872);
		ItemStack strut448 = new ItemStack(decorBlock, 8, 872);
		ItemStack strut66 = new ItemStack(decorBlock, 1, 873);
		ItemStack strut664 = new ItemStack(decorBlock, 4, 873);
		ItemStack discreteStairs8 = new ItemStack(decorBlock, 8, 861);
		ItemStack discreteStairs = new ItemStack(decorBlock, 1, 861);
		ItemStack discreteSmallStairs = new ItemStack(decorBlock, 1, 862);
		
		
		//Ladders 800-820
		ItemStack footLadder6 = new ItemStack(decorBlock, 6, 800);
		ItemStack poleLadder6 = new ItemStack(decorBlock, 6, 801);
		ItemStack simpleLadder6 = new ItemStack(decorBlock, 6, 802);
		ItemStack ropeLadder6 = new ItemStack(decorBlock, 6, 803);
		ItemStack fixedLadder6 = new ItemStack(decorBlock, 6, 804);
		ItemStack classicLadder6 = new ItemStack(decorBlock, 6, 806);
		ItemStack indLadder6 = new ItemStack(decorBlock, 6, 807);
		
		//Racks
		ItemStack swordRest2 = new ItemStack(decorBlock, 2, 821);
		ItemStack swordRack2 = new ItemStack(decorBlock, 2, 822);
		
		//Shapes
		ItemStack slab6 = new ItemStack(decorBlock, 6, 881);
		
		//Ladders
		GameRegistry.addRecipe(new ShapelessOreRecipe(footLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Blocks.cobblestone}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(poleLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Blocks.stone}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(simpleLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Blocks.planks}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ropeLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Items.reeds}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fixedLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Blocks.planks}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(classicLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Items.iron_ingot}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(indLadder6, new Object[]{frameDiscreteCable, Blocks.ladder, Blocks.stonebrick}));
		
		//Racks
		GameRegistry.addRecipe(new ShapedOreRecipe(swordRest2, new Object[]{ "   ", " Y ", "XXX", 'X', Blocks.stone_slab, 'Y', frameDiscreteCable,}));
		GameRegistry.addRecipe(new ShapedOreRecipe(swordRack2, new Object[]{ "Z Z", "ZYZ", "XXX", 'X', Blocks.stone_slab, 'Y', frameDiscreteCable, 'Z', Blocks.stone}));
		
		//fixture
		//GameRegistry.addRecipe(new ShapedOreRecipe(fixture8, new Object[]{ " X ", "XYX", " X ", 'X', Item.stick, 'Y', frameDiscreteCable,}));
		GameRegistry.addRecipe(new ShapedOreRecipe(strut448, new Object[]{ " X ", "XYX", " X ", 'X', Items.stick, 'Y', frameDiscreteCable,}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(strut44, new Object[]{fixture}));
		
		
		//Struts
		GameRegistry.addRecipe(new ShapelessOreRecipe(strut664, new Object[]{strut44, strut44, strut44, strut44}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(strut22, new Object[]{strut44}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(strut444, new Object[]{strut22, strut22, strut22, strut22}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(strut44, new Object[]{strut66}));
		//Recipes.compressor.addRecipe(new RecipeInputItemStack(strut44), null, strut22);
		//Recipes.compressor.addRecipe(new RecipeInputItemStack(strut66), null, strut44);
		
		//Recipes.extractor.addRecipe(new RecipeInputItemStack(strut44), null, strut66);
		//Recipes.extractor.addRecipe(new RecipeInputItemStack(strut22), null, strut44);
		
		//Stairs
		GameRegistry.addRecipe(new ShapedOreRecipe(discreteStairs8, new Object[]{ "X  ", "XX ", "XXX", 'X', frameDiscreteCable}));		
		GameRegistry.addRecipe(new ShapelessOreRecipe(discreteSmallStairs, new Object[]{discreteStairs}));
		
		//Slab
		GameRegistry.addRecipe(new ShapedOreRecipe(slab6, new Object[]{ "XXX", "   ", "   ", 'X', frameDiscreteCable}));		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowDiagonalHazard, new Object[]{ "X  ", " Z ", "  Y", 'X', "dyeBlack", 'Y', "dyeYellow", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowDiagonalHazard, new Object[]{ "  X", " Z ", "Y  ", 'X', "dyeBlack", 'Y', "dyeYellow", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowDiagonalHazard, new Object[]{ "X  ", " Z ", "  Y", 'X', "dyeYellow", 'Y', "dyeBlack", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowDiagonalHazard, new Object[]{ "  X", " Z ", "Y  ", 'X', "dyeYellow", 'Y', "dyeBlack", 'Z', frameDiscreteCable}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowArrowHazard, new Object[]{ " X ", " ZY", " X ", 'X', "dyeBlack", 'Y', "dyeYellow", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(yellowArrowHazard, new Object[]{ " X ", " ZY", " X ", 'X', "dyeYellow", 'Y', "dyeBlack", 'Z', frameDiscreteCable}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(redDiagonalHazard, new Object[]{ "X  ", " Z ", "  Y", 'X', "dyeWhite", 'Y', "dyeRed", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redDiagonalHazard, new Object[]{ "  X", " Z ", "Y  ", 'X', "dyeWhite", 'Y', "dyeRed", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redDiagonalHazard, new Object[]{ "X  ", " Z ", "  Y", 'X', "dyeRed", 'Y', "dyeWhite", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redDiagonalHazard, new Object[]{ "  X", " Z ", "Y  ", 'X', "dyeRed", 'Y', "dyeWhite", 'Z', frameDiscreteCable}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(redArrowHazard, new Object[]{ " X ", " ZY", " X ", 'X', "dyeWhite", 'Y', "dyeRed", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(redArrowHazard, new Object[]{ " X ", " ZY", " X ", 'X', "dyeRed", 'Y', "dyeWhite", 'Z', frameDiscreteCable}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(checkeredYellowHazard, new Object[]{ "X Y", " Z ", "Y X", 'X', "dyeBlack", 'Y', "dyeYellow", 'Z', frameDiscreteCable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(checkeredYellowHazard, new Object[]{ "X Y", " Z ", "Y X", 'X', "dyeYellow", 'Y', "dyeBlack", 'Z', frameDiscreteCable}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(smallcheckeredYellowHazard, new Object[]{singleCheckeredYellowHazard, singleCheckeredYellowHazard, singleCheckeredYellowHazard, singleCheckeredYellowHazard}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(hazardStrip8, new Object[]{frameDiscreteCable, singleCheckeredYellowHazard}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(angledHazardStrip8, new Object[]{frameDiscreteCable, singleCheckeredYellowHazard, singleCheckeredYellowHazard}));
		
	    ItemStack discreteWall1 = new ItemStack(decorBlock, 1, 900);
	    ItemStack discreteWall8 = new ItemStack(decorBlock, 8, 900);
	    ItemStack railingSimple = new ItemStack(decorBlock, 1, 910);
	    ItemStack railingDouble = new ItemStack(decorBlock, 1, 911);
	    ItemStack railingTriple = new ItemStack(decorBlock, 1, 912);
	    ItemStack railingSquare = new ItemStack(decorBlock, 1, 913);
	    ItemStack railingSquareDouble = new ItemStack(decorBlock, 1, 914);
	    ItemStack railingSquareTriple = new ItemStack(decorBlock, 1, 915);
	    ItemStack panelSquare = new ItemStack(decorBlock, 1, 950);
	    ItemStack panelSquareDouble = new ItemStack(decorBlock, 1, 951);
	    ItemStack panelSquareTriple = new ItemStack(decorBlock, 1, 952);
	    
	    GameRegistry.addRecipe(new ShapelessOreRecipe(discreteWall8, new Object[] { frameDiscreteCable, Blocks.cobblestone_wall }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingSimple, new Object[] { discreteWall1, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingDouble, new Object[] { railingSimple, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingTriple, new Object[] { railingDouble, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingSquare, new Object[] { discreteWall1, Blocks.planks }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingSquareDouble, new Object[] { railingSquare, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(railingSquareTriple, new Object[] { railingSquareDouble, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(decorBlock, 1, 916), new Object[] { discreteWall1, yellowDiagonalHazard }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(panelSquare, new Object[] { discreteWall1, Blocks.stone}));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(panelSquareDouble, new Object[] { panelSquare, Items.stick }));
	    GameRegistry.addRecipe(new ShapelessOreRecipe(panelSquareTriple, new Object[] { panelSquareDouble, Items.stick }));
	}
}

