package kappafox.di.decorative.renderers;

import kappafox.di.base.BlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.decorative.DiscreteDecorative;
import kappafox.di.decorative.blocks.BlockDecor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteDecorativeRenderManager implements ISimpleBlockRenderingHandler
{

	private int renderID;
	private static ISimpleBlockRenderingHandler stripRenderer;
	private static ISimpleBlockRenderingHandler decorRenderer;
	
	private static BlockRenderingHandler HANDLER_DECOR_BLOCK;
	
	public DiscreteDecorativeRenderManager(int rID)
	{
		renderID = rID;
		stripRenderer = new StripRenderer(rID);
		//decorRenderer = new DecorRenderer(rID);
		
		HANDLER_DECOR_BLOCK = new BlockDecorRenderer();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		if(modelID == DiscreteDecorative.hazardRenderID)
		{
			stripRenderer.renderInventoryBlock(block, meta, modelID, renderer);
		}
		
		if(modelID == DiscreteDecorative.decorRenderID)
		{		
			HANDLER_DECOR_BLOCK.renderInventoryBlock(block, meta, modelID, renderer);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if(modelID == DiscreteDecorative.hazardRenderID)
		{
			if(meta >= 0 && meta <= 5)
			{
				renderer.renderStandardBlock(block, x, y, z);
				return true;
			}
			
			return stripRenderer.renderWorldBlock(world, x, y, z, block, modelID, renderer);
		}
		
		if(modelID == DiscreteDecorative.decorRenderID)
		{
			TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
			
			if(tile != null)
			{
				return HANDLER_DECOR_BLOCK.renderWorldBlock(world, x, y, z, block, modelID, renderer);
			}

			//return decorRenderer.renderWorldBlock(world, x, y, z, block, modelID, renderer);	
		}
		
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int param_1)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return renderID;
	}

	
}
