package kappafox.di.decorative.renderers;

import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.PointSet;
import kappafox.di.decorative.blocks.BlockDecor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class SubRendererBridge extends SubBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		int type = tile.getSubtype();
		
		switch(type)
		{
			case BlockDecor.ID_BRIDGE_CATWALK_SIMPLE:
			{
				return this.renderWorldBridgeCatwalkSimpleBlock(world, x, y, z, block, tile, renderer, type);
			}
		}
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		// TODO Auto-generated method stub
	}
	

	private boolean renderWorldBridgeCatwalkSimpleBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type) 
	{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.zero, -px.one, px.zero, px.sixteen, px.zero, px.zero));
		return false;
	}

}
