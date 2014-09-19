package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.PointSet;
import kappafox.di.decorative.blocks.BlockDecor;

public class SubRendererShape extends SubBlockRenderingHandler
{

	private static final PointSet PART_SHAPE_SLAB_LOWER = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.eight, px.sixteen);
	private static final PointSet PART_SHAPE_SLAB_UPPER = new PointSet(px.zero, px.eight, px.zero, px.sixteen, px.sixteen, px.sixteen);
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		
		switch(tile.getSubtype())
		{
			case BlockDecor.ID_SHAPE_SLAB:
				return this.renderWorldDiscreteSlabBlock(world, x, y, z, block, tile, renderer, tile.getSubtype());
		}
		
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		
		tessellator.startDrawingQuads();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		switch(subtype)
		{
			
			case BlockDecor.ID_SHAPE_SLAB:
			{
				this.renderInventoryDiscreteSlabBlock(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	
	private void renderInventoryDiscreteSlabBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SHAPE_SLAB_LOWER);
	}

	public boolean renderWorldDiscreteSlabBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		TileEntityDiscreteBlock t = (TileEntityDiscreteBlock)tile;
		
		if(t != null)
		{
			if(t.getVariable() == 1)
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, PART_SHAPE_SLAB_UPPER);
			}
			else
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, PART_SHAPE_SLAB_LOWER);
			}
		}

		return true;
	}

}
