package kappafox.di.base;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteRenderManager implements ISimpleBlockRenderingHandler
{
	private HashMap<Integer, BlockRenderingHandler> renderers;
	
	private int renderID;
	private boolean inventory3D;
	
	public DiscreteRenderManager(int rid, boolean inventory3d)
	{
		renderID = rid;
		inventory3D = inventory3d;
		
		renderers = new HashMap<Integer, BlockRenderingHandler>();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		if(renderers.containsKey(modelID))
		{
			renderers.get(modelID).renderInventoryBlock(block, meta, modelID, renderer);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if(renderers.containsKey(modelId))
		{
			return renderers.get(modelId).renderWorldBlock(world, x, y, z, block, modelId, renderer);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int i)
	{
		return inventory3D;
	}

	@Override
	public int getRenderId( )
	{
		return renderID;
	}
	
	public void registerRenderer(int modelID, BlockRenderingHandler renderer)
	{
		if(!renderers.containsKey(modelID))
		{
			renderers.put(modelID, renderer);
		}
	}
}
