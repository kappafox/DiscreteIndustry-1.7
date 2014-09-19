package kappafox.di.base.util;

import cpw.mods.fml.common.FMLCommonHandler;

public class SideHelper
{
	public static boolean onServer( )
	{
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
	public static boolean onClient( )
	{
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
}
