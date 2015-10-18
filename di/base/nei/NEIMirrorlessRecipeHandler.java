package kappafox.di.base.nei;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;

import java.util.*;

import kappafox.di.base.recipes.MirrorlessRecipes;
import kappafox.di.base.util.MirrorlessShapedRecipe;

public class NEIMirrorlessRecipeHandler extends ShapedRecipeHandler   
{	
	@Override
	public void loadCraftingRecipes(ItemStack result) 
	{
		for (MirrorlessShapedRecipe recipe : MirrorlessRecipes.getRecipes()) 
		{
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getRecipeOutput(), result)) 
            {
            	if(result != null && result.isItemEqual(recipe.getRecipeOutput()))
            	{
                	arecipes.add(getShape(recipe));           		
            	}
            }
		}		
	}
	
	private ShapedRecipeHandler.CachedShapedRecipe getShape(MirrorlessShapedRecipe recipe) 
	{
		ShapedRecipeHandler.CachedShapedRecipe shape = new ShapedRecipeHandler.CachedShapedRecipe(0, 0, null, recipe.getRecipeOutput());
		
		for (int x = 0; x < 3; x++) 
		{
			for (int y = 0; y < 3; y++) 
			{
				if (recipe.recipe[y * 3 + x] == null) 
				{
					continue;
				}
				
				PositionedStack stack = new PositionedStack(recipe.recipe[y * 3 + x], 25 + x * 18, 6 + y * 18);
				stack.setMaxSize(1);
				shape.ingredients.add(stack);
			}
		}

		shape.result.relx = 136;
		shape.result.rely = 36;
		return shape;
	}
	
	@Override
    public PositionedStack getResultStack(int recipe) 
	{
        PositionedStack stack = arecipes.get(recipe).getResult();
        
        PositionedStack newStack = stack.copy();
        
        newStack.relx -= 17;
        newStack.rely -= 12;
        
        return newStack;
    }
}
