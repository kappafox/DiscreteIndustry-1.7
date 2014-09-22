package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.blocks.BlockDecor;

public class SubRendererWall extends SubBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock t = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		int subtype = t.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_WALL_DISCRETE:
				return this.renderWorldDiscreteWallBlock(world, x, y, z, block, t, renderer, subtype);
		}
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		tessellator.startDrawingQuads();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		switch(subtype)
		{		
			case BlockDecor.ID_WALL_DISCRETE:
			{
				//this.renderInventoryDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	
	private boolean renderWorldDiscreteWallBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock t, RenderBlocks renderer, int subtype) 
	{
		renderer.setRenderBounds(px.zero, px.zero, px.five, px.sixteen, px.thirteen, px.eleven);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		return false;
	}
}
