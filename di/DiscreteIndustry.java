package kappafox.di;

import net.minecraftforge.common.config.Configuration;
import kappafox.di.base.lib.IC2Data;
import kappafox.di.base.lib.Library;
import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.base.tileentities.TileEntitySingleVariable;
import kappafox.di.decorative.DiscreteDecorative;
import kappafox.di.electrics.DiscreteElectrics;
import kappafox.di.transport.DiscreteTransport;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(modid = DiscreteIndustry.MODID, name = DiscreteIndustry.NAME, version = DiscreteIndustry.VERSION, dependencies = DiscreteIndustry.DEPENDENCIES)

public class DiscreteIndustry
{
	
	@Instance("DiscreteIndustry")
	public static DiscreteIndustry instance;
	
	//Network
	public static SimpleNetworkWrapper network;
	
	//Globals
	public static final String MODID = "DiscreteIndustry";
	public static final String NAME = "Discrete Industry";
	public static final String VERSION = "1.0.1g";
	public static final String DEPENDENCIES = "required-after:IC2";
	
	
	//Helper classes
	public static final Library librarian = new Library();
	
	
	public static Class<?> GTWrench = null;
	public static Class<?> BCWrench = null;

	//Items
		
	//Modules
	private DiscreteElectrics electrics = new DiscreteElectrics();
	private DiscreteTransport transport = new DiscreteTransport();
	private DiscreteDecorative decorative = new DiscreteDecorative();
	
	// Gui Ids
	public static final int GID_DISCRETE_HOPPER = 0;
	public static final int GID_SWORD_RACK = 2;
	public static final int GID_DUST_UNIFIER = 3;
	
	
	@EventHandler
	public void preInitialisation(FMLPreInitializationEvent event)
	{
		//grab the id database
		librarian.preInit();
		
		//Initialise network
		network = NetworkRegistry.INSTANCE.newSimpleChannel("DINetworkChannel");
		//network.registerMessage(DiscretePacketHandler.class, discret, discriminator, side);
		
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());	//grab the config file or create it
		config.load();
	
		//Packet.addIdClassMapping(88, true, true, PacketDiscreteSync.class);
		

		electrics.preInitialisation(event, config);
		transport.preInitialisation(event, config);
		decorative.preInitialisation(event, config);
		
		config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event_ )
	{
		
		librarian.init();
		registerBlocks();

		
		electrics.load(event_);
		transport.load(event_);
		decorative.load(event_);
		
		//Gui Stuff
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new DiscreteGuiHandler());
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntitySingleVariable.class, "SVTE");
		
		FMLInterModComms.sendMessage("Waila", "register", "kappafox.di.base.compat.DiscreteWailaProvider.callbackRegister");
		
	}
	
	private void registerBlocks( ){}
	
	@EventHandler
	public void postInitialisation(FMLPostInitializationEvent event_)
	{
		librarian.postInit();
		this.reflection();
	}
	
	private void reflection( )
	{
		try
		{
			GTWrench = Class.forName("gregtechmod.api.items.GT_WrenchIC_Item");
		}
		catch(Exception e_)
		{
			
		}
		
		try
		{
			BCWrench = Class.forName("buildcraft.api.tools.IToolWrench");
		}
		catch(Exception e_)
		{
			
		}
	}
	
	

}
