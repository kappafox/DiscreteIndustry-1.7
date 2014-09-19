	package kappafox.di.transport.recipies;

import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import kappafox.di.transport.DiscreteTransport;
import kappafox.di.transport.items.ItemDiscreteTransport;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUpgradeStorageUnit implements IRecipe
{
	
    private ArrayList input = new ArrayList();
	private ItemStack output;
	private int tagSlot;
	
	
	public RecipeUpgradeStorageUnit(ItemStack result, int nbtSlot, Object... pattern)
	{
		tagSlot = nbtSlot;
		
		//Using the code from ShapelessOreRecipe
        output = result.copy();
        for (Object in : pattern)
        {
            if (in instanceof ItemStack)
            {
            	//System.out.println("ITEMSTACK");
                input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
            	//System.out.println("ITEM");
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
            	//System.out.println("BLOCK");
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
            	//System.out.println("STRING");
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp : pattern)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
	}
	
	@Override
	public boolean matches(InventoryCrafting grid, World world)
	{
		int matches = 0;
		for(int i = 0; i < grid.getSizeInventory(); i++)
		{
			ItemStack istack = grid.getStackInSlot(i);
			if(istack != null)
			{
				//4th slot is special
				if(i == tagSlot)
				{
					//Do nothing for now
				}
				else
				{
					if(input.get(i) == null)
					{
						return false;
					}
					else
					{
						if(this.doesMatch(grid.getStackInSlot(i), i))
						{
							matches++;
						}
						else
						{
							return false;
						}
					}
				}
			}
			else
			{
				if(input.get(i) == null)
				{
					matches++;
				}
			}
		}
		
		if(matches == 8)
		{
			ItemStack istack = grid.getStackInSlot(tagSlot);
			
			if(istack != null)
			{
				//System.out.println(istack.itemID + "==" + (DiscreteTransport.discreteTransportItemID - 256) + "\t\t" + istack.getItemDamage());
				if((istack.getItem() instanceof ItemDiscreteTransport) && istack.getItemDamage() >= 0 && istack.getItemDamage() <= 16)
				{
					if(istack.getItemDamage() < output.getItemDamage())
					{
						matches++;
					}
					else
					{
						matches = 0;
					}
				}
				else
				{
					//something in the slot that's not appropriate
					matches = 0;
				}
			}
		}
		return matches >= 8;
	}
	
	private boolean doesMatch(ItemStack target, int slot)
	{
		
		if(target != null)
		{
			Object o = input.get(slot);
			
			//single item
			if(o instanceof ItemStack)
			{
				ItemStack r = (ItemStack)o;
				if(OreDictionary.itemMatches(r, target, false))
				{
					return true;
				}
				
				return false;
			}
			
			//ore dictionary list
			if(o instanceof ArrayList)
			{
				ArrayList oredic = (ArrayList)o;
				for(Object p : oredic)
				{
					ItemStack t2 = (ItemStack)p;
					
					if(t2 != null)
					{
						if(target.isItemEqual(t2))
						{
							return true;
						}
					}
				}
				
				return false;
			}
		}
		else
		{
			//target is null, is the pattern?
			if(input.get(slot) == null)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid)
	{
		if(grid.getStackInSlot(tagSlot) != null)
		{
			if(grid.getStackInSlot(tagSlot).hasTagCompound() == true)
			{
				ItemStack t = output.copy();
				t.setTagCompound((grid.getStackInSlot(tagSlot).getTagCompound()));
				return t;
			}
		}
		return output.copy();
	}

	@Override
	public int getRecipeSize()
	{
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}

}
