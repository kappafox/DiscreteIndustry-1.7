package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.tileentities.TileEntitySubtype;

public class SubRendererStrut extends SubBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		
		return this.renderWorldStrut(world, x, y, z, block, tile, renderer, tile.getSubtype());
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		
		this.renderInventoryStrut(block, subtype, modelID, renderer);
		
		tessellator.draw();		
	}
	
	private void renderInventoryStrut(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
	
		double y1 = 0;
		double y2 = 0;
		double z1 = 0;
		double z2 = 0;
		
		switch(subtype)
		{
			case 871:
			{
				y1 = px.seven;
				y2 = px.nine;
				break;
			}
			
			case 872:
			{
				y1 = px.six;
				y2 = px.ten;
				break;
			}
			
			case 873:
			{
				y1 = px.five;
				y2 = px.eleven;
				break;
			}
		}
		
        renderer.setRenderBounds(px.zero, y1, y1, px.sixteen, y2, y2);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
        renderer.setRenderBounds(px.zero, y1 - px.one, y1 - px.one, px.one, y2 + px.one, y2 + px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
        renderer.setRenderBounds(px.fifteen, y1 - px.one, y1 - px.one, px.sixteen, y2 + px.one, y2 + px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);

	}
	
	private boolean renderWorldStrut(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int subtype)
	{
		double ymin = px.six;
		double ymax = px.ten;
		
		double start = px.zero;
		double end = px.sixteen;
		
		double plugStart = px.five;
		double plugEnd = px.eleven;
		
		double coreMin = ymin;
		double coreMax = ymax;
		
		double length = px.six;
		
		boolean[] connections = new boolean[6];
		
		for(int i = 0; i < connections.length; i++)
		{
			connections[i] = true;
		}
		//we need this legacy because of the old fixtures not using this tileentity
		if(tile != null && tile instanceof TileEntitySidedConnector)
		{
			int type = subtype;
			
			TileEntitySidedConnector t2 = (TileEntitySidedConnector)tile;
			
			switch(type)
			{
	
				case 871:	//2x2
				{
					ymin = px.seven;
					ymax = px.nine;
					
					plugStart = px.six;
					plugEnd = px.ten;
					
					coreMin = px.seven;
					coreMax = px.nine;
					
					length = px.seven;
					break;
				}
				
				case 873:	//6x6
				{
					ymin = px.four;
					ymax = px.twelve;
					plugStart = px.three;
					plugEnd = px.thirteen;
					
					coreMin = px.four;
					coreMax = px.twelve;
					
					length = px.four;
					break;
				}
			}
			
			if(t2.getAllConnections().length > 0)
			{
				connections = t2.getAllConnections();
			}
		}
			
		boolean[] flips;

		renderer.flipTexture = false;
		
		DiscreteRenderHelper drh = new DiscreteRenderHelper();
		
		Block id = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
        //SOUTH Arm +Z 3
        if(this.shouldConnect(world, x, y, z + 1) == true && connections[3] == true)
        {
        	if(!this.blockEquals(id, meta, world.getBlock(x, y, z + 1), world.getBlockMetadata(x, y, z + 1)))
        	{
        		renderer.setRenderBounds(plugStart, plugStart, px.fifteen, plugEnd , plugEnd, px.sixteen);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}

            renderer.setRenderBounds(ymin, ymin, px.sixteen - length, ymax, ymax, end);
            drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,true,false,false,false,false});
        }


        //NORTH Arm -Z 2
        if(this.shouldConnect(world, x, y, z - 1) == true  && connections[2] == true)
        {
        	if(!this.blockEquals(id, meta, world.getBlock(x, y, z - 1), world.getBlockMetadata(x, y, z - 1)))
        	{
        		renderer.setRenderBounds(plugStart, plugStart, px.zero, plugEnd , plugEnd, px.one);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}
        	//renderer.uvRotateSouth = 1;
            renderer.setRenderBounds(ymin, ymin, px.zero, ymax, ymax, length);
            drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,true,false,false,false,false});
            //renderer.uvRotateSouth = 0;
        }
        
        
        //EAST + X 5
        if(this.shouldConnect(world, x + 1, y, z) == true  && connections[5] == true)
        { 
        	if(!this.blockEquals(id, meta, world.getBlock(x + 1, y, z), world.getBlockMetadata(x + 1, y, z)))
        	{
        		renderer.setRenderBounds(px.fifteen, plugStart, plugStart, px.sixteen , plugEnd, plugEnd);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}
        	renderer.setRenderBounds(px.sixteen - length, ymin, ymin, end, ymax, ymax);
        	drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,false,false,false,true,false});

        }

        //WEST 4 - X
        if(this.shouldConnect(world, x - 1, y, z) == true  && connections[4] == true)
        {
        	if(!this.blockEquals(id, meta, world.getBlock(x - 1, y, z), world.getBlockMetadata(x - 1, y, z)))
        	{
        		renderer.setRenderBounds(px.zero, plugStart, plugStart, px.one , plugEnd, plugEnd);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}
            renderer.setRenderBounds(start, ymin, ymin, length, ymax, ymax);
            //renderer.renderStandardBlock(block, x, y, z);
            drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,false,false,false,true,false});
        }
       
        //TOP 1
        if(this.shouldConnect(world, x, y + 1, z) == true  && connections[1] == true)
        {
        	if(!this.blockEquals(id, meta, world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z)))
        	{
        		renderer.setRenderBounds(plugStart, px.fifteen, plugStart, plugEnd , px.sixteen, plugEnd);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}
            renderer.setRenderBounds(ymin, px.sixteen - length, ymin, ymax, end, ymax);
            drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,true,false,false,true,false});
        }
        
        //BOTTOM 0
        if(this.shouldConnect(world, x, y - 1, z) == true  && connections[0] == true)
        {
        	if(!this.blockEquals(id, meta, world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z)))
        	{
        		renderer.setRenderBounds(plugStart, px.zero, plugStart, plugEnd , px.one, plugEnd);
        		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
        	}
        	
            renderer.setRenderBounds(ymin, start, ymin, ymax, length, ymax);
            drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,true,false,false,true,false});
        }
        
        //core block
        renderer.setRenderBounds(coreMin, coreMin, coreMin, coreMax, coreMax, coreMax);
        drh.renderDiscreteQuadWithFlip(world, renderer, block, x, y, z, new boolean[]{false,true,false,false,false,false});
        
        return true;		
	}
	
    private boolean shouldConnect(IBlockAccess world, int x, int y, int z)
    {
    	if(world.isAirBlock(x, y, z) == true)
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    private boolean blockEquals(Block blockA, int metaa, Block blockB, int metab)
    {
    	if(blockA == blockB && metaa == metab)
    	{
    		return true;
    	}
    	
    	return false;
    }

}
