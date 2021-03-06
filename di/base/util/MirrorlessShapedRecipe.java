package kappafox.di.base.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MirrorlessShapedRecipe implements IRecipe 
{

    public final ItemStack[] recipe;
    public final ItemStack output;

    public MirrorlessShapedRecipe(ItemStack output, ItemStack[] recipe)
    {
        this.recipe = recipe;
        this.output = output.copy();
    }

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) 
    {
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 3; ++x)
            {
                ItemStack itemInSlot = p_77569_1_.getStackInRowAndColumn(x, y);
                ItemStack recipeSlot = recipe[y * 3 + x];
                
                if(itemInSlot != null || recipeSlot != null) 
                {
                    if ((itemInSlot == null && recipeSlot != null) || (itemInSlot != null && recipeSlot == null)) 
                    {
                        return false;
                    }

                    if (itemInSlot.getItem() != recipeSlot.getItem()) 
                    {
                        return false;
                    }

                    if(recipeSlot.getItemDamage() != Short.MAX_VALUE && recipeSlot.getItemDamage() != itemInSlot.getItemDamage())
                    {
                        return false;
                    }

                }

            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) 
    {
        return output.copy();
    }

    @Override
    public int getRecipeSize() 
    {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() 
    {
        return output.copy();
    }
}