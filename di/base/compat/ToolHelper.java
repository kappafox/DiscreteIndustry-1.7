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
				
				if(DiscreteIndustry.GTWrench != null)
				{
					/*
					if(DiscreteIndustry.GTWrench.isAssignableFrom(i.getClass()));
					{
						System.out.println(DiscreteIndustry.GTWrench.getClass());
						System.out.println(i.getClass());
						return true;
					}
					*/
				}

				
				if(DiscreteIndustry.BCWrench != null)
				{

					if(DiscreteIndustry.BCWrench.isAssignableFrom(i.getClass()))
					{
						//System.out.println(DiscreteIndustry.BCWrench.getClass());
						//System.out.println(i.getClass());
						return true;
					}
				}
			
				if(item.getDisplayName().equalsIgnoreCase(wrench.getDisplayName()) || item.getDisplayName().equalsIgnoreCase(ewrench.getDisplayName()))
				{
					return true;
				}
			}
		}
		return false;
	}
}
