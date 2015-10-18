package kappafox.di.base.recipes;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import kappafox.di.base.util.MirrorlessShapedRecipe;

public class MirrorlessRecipes 
{
	private static LinkedList<MirrorlessShapedRecipe> RECIPIES = new LinkedList<MirrorlessShapedRecipe>();
	
	
	public static LinkedList<MirrorlessShapedRecipe> getRecipes()
	{
		return RECIPIES;
	}
	
	public static void registerRecipe(MirrorlessShapedRecipe recipe)
	{
		if(recipe != null) 
		{
			RECIPIES.add(recipe);
			
			GameRegistry.addRecipe(recipe);
		}
	}
}
