package kappafox.di.transport.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemDiscreteHopperBlock extends ItemBlock
{

	public ItemDiscreteHopperBlock(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int par1_)
	{
		return par1_;
	}
	
	public String getUnlocalizedName(ItemStack istack_)
	{
		String name = "";
		
		switch(istack_.getItemDamage())
		{
			case 0:
			{
				name = "empty";
				break;
			}
			
			case 1:
			{
				name = "tin";
				break;
			}
			case 2:
			{
				name = "copper";
				break;
			}
			case 3:
			{
				name = "gold";
				break;
			}
			case 4:
			{
				name = "glass";
				break;
			}
			case 5:
			{
				name = "iron";
				break;
			}
			default:
			{
				name = "how did you get this?";
				break;
			}
		}
		
		return getUnlocalizedName() + "." + name;
	}

}
