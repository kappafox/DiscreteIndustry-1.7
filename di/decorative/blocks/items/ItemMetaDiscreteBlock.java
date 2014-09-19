package kappafox.di.decorative.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemMetaDiscreteBlock extends ItemBlock
{

	public ItemMetaDiscreteBlock(Block block)
	{
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public abstract String getUnlocalizedName(ItemStack istack_);
	
	@Override
	public int getMetadata(int par1_)
	{
		return par1_;
	}

}
