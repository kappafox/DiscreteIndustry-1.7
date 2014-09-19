package kappafox.di.decorative.renderers;

import kappafox.di.decorative.DiscreteDecorative;
import kappafox.di.decorative.blocks.BlockDecor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class DiscreteDecorativeItemRenderer implements IItemRenderer
{
	
	private static RenderBlocks renderer = new RenderBlocks();
	private static DiscreteDecorativeRenderManager ddrm = new DiscreteDecorativeRenderManager(9999);
	
	@Override
	public boolean handleRenderType(ItemStack item_, ItemRenderType type_) 
	{
		int dmg = item_.getItemDamage();
		
		if(dmg < 16)
		{
			return false;
		}
		
		
		if(dmg == 800)
		{
			//if(type_ == ItemRenderType.INVENTORY || type_ == ItemRenderType.ENTITY)
			//{
				return true;
				//System.out.println(dmg);
			//}
		}
		
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type_, ItemStack item_, ItemRendererHelper helper_) 
	{
		/*
		if(type_ == ItemRenderType.ENTITY)
		{
			return true;
		}
		
		
		if(type_ == ItemRenderType.EQUIPPED)
		{
			if(helper_ == ItemRendererHelper.)
		}
		*/
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		//int id = item.itemID;
		int dmg = item.getItemDamage();
		
		//Block b = Block.blocksList[id];
		Block b = Block.getBlockFromItem(item.getItem());
		
		//DiscreteDecorative d = new DiscreteDecorative();
		if(b != null)
		{	
			if(b instanceof BlockDecor)
			{
				ddrm.renderInventoryBlock(b, dmg, DiscreteDecorative.decorRenderID, renderer);
			}
		}
		
	}
}
