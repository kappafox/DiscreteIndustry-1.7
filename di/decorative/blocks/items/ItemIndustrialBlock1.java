package kappafox.di.decorative.blocks.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemIndustrialBlock1 extends ItemBlock
{
	public static String[] names;
	
	public ItemIndustrialBlock1(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		
		if(names == null)
		{
			names = new String[16];
			
			for(int i = 0; i < 16; i++)
			{
				names[i] = "Industrial Block " + i;				
			}
		}
	}
	
	public String getUnlocalizedName(ItemStack istack)
	{
		if(istack == null) return "ERROR.SWAG";
		
		return names[istack.getItemDamage()];
	}
	
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}
}
