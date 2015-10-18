package kappafox.di.base.nei;

import kappafox.di.DiscreteIndustry;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.IConfigureNEI", modid = "NotEnoughItems")
public class NEIDiscreteIndustryConfig implements IConfigureNEI
{
	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public String getName() 
	{
		return DiscreteIndustry.NAME;
	}

	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public String getVersion() 
	{
		return DiscreteIndustry.VERSION;
	}

	@Override
	@Optional.Method(modid = "NotEnoughItems")
	public void loadConfig() 
	{
		API.registerRecipeHandler(new NEIMirrorlessRecipeHandler());		
	}
}
