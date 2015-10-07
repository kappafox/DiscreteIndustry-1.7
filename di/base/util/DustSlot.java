package kappafox.di.base.util;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DustSlot extends Slot
{

	public DustSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) 
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}
	
	@Override
    public boolean isItemValid(ItemStack istack)
    {
        if(istack == null) return false;
        
        List<String> names = getOreDictionaryNames(istack);
        
        for(String name : names)
        {
        	if(name.startsWith("dustTiny") || name.startsWith("dustSmall"))
        		return true;
        }
        
        return false;
    }
	
	private List<String> getOreDictionaryNames(ItemStack istack)
	{
		if(istack == null) return new LinkedList<String>();
		
		List<String> names = new LinkedList<String>();
		
		int[] itemIds = OreDictionary.getOreIDs(istack);
		
		for(int id : itemIds)
		{
			names.add(OreDictionary.getOreName(id));
		}
		
		return names;
	}
}
