package kappafox.di.base.compat;

import ic2.api.item.IC2Items;
import kappafox.di.DiscreteIndustry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ToolHelper
{

	public static boolean isWrench(ItemStack item)
	{
		
		ItemStack wrench = IC2Items.getItem("wrench");
		ItemStack ewrench = IC2Items.getItem("electricWrench");

		boolean ignore = false;
		if(item != null)
		{
			
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{
				
				Item i = item.getItem();

				if(DiscreteIndustry.BCWrench != null)
				{
					Class[] interfaces = i.getClass().getInterfaces();
					
					for(int j = 0; j < interfaces.length; j++)
					{
						if(interfaces[j].getName().equalsIgnoreCase("buildcraft.api.tools.IToolWrench"))
						{
							return true;
						}
					}
				}
				
				if(item.getDisplayName().equalsIgnoreCase(wrench.getDisplayName()) || item.getDisplayName().equalsIgnoreCase(ewrench.getDisplayName()))
				{
					return true;
				}
				
				if(item.getDisplayName().equalsIgnoreCase("Crescent Hammer")) return true;
			}
		}
		return false;
	}
	
	public static short getOppositeSide(short side)
	{
		switch(side)
		{
			case 0:
				return 1;
			
			case 1:
				return 0;
			
			case 2:
				return 3;
			
			case 3:
				return 2;
			
			case 4:
				return 5;
			
			case 5:
				return 4;
				
			default:
				return side;
		}
	}
}
