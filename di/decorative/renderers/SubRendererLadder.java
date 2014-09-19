package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import kappafox.di.decorative.blocks.BlockDecor;


public class SubRendererLadder extends SubBlockRenderingHandler
{
	
	private static final PointSet PART_POLELADDER_SUPPORT = new PointSet(px.seven, px.zero, px.one, px.nine, px.sixteen, px.three);
	private static final PointSet PART_POLELADDER_RIGHTPEG = new PointSet(px.nine, px.two, px.one ,px.fifteen, px.three, px.two);
	private static final PointSet PART_POLELADDER_LEFTPEG = new PointSet(px.one, px.one, px.one, px.seven, px.two, px.two);
	private static final PointSet PART_POLELADDER_RIGHTSTUD = new PointSet(px.fourteen, px.two, px.one ,px.fifteen, px.three, px.two);
	private static final PointSet PART_POLELADDER_LEFTSTUD = new PointSet(px.one, px.one, px.one, px.two, px.two, px.two);
	
	private static final PointSet PART_SIMPLELADDER_LEFTSUPPORT = new PointSet(px.two, px.zero, px.zero, px.three, px.sixteen, px.one);
	private static final PointSet PART_SIMPLELADDER_RIGHTSUPPORT = new PointSet(px.thirteen, px.zero, px.zero, px.fourteen, px.sixteen, px.one);
	private static final PointSet PART_SIMPLELADDER_RUNG = new PointSet(px.three, px.three, px.zero, px.thirteen, px.four, px.one);
	
	private static final PointSet PART_ROPELADDER_ROPE = new PointSet(px.seven, px.zero, px.one, px.nine, px.sixteen, px.three);
	private static final PointSet PART_ROPELADDER_TOP = new PointSet(px.six, px.fifteen, px.zero, px.ten, px.sixteen, px.four);
	
	private static final PointSet PART_FOOTLADDER_RIGHTRUNG = new PointSet(px.nine, px.one, px.one, px.fourteen, px.two, px.two);
	private static final PointSet PART_FOOTLADDER_RIGHTRUNG_STUD1 = new PointSet(px.nine, px.one, px.zero, px.ten, px.two, px.one);
	private static final PointSet PART_FOOTLADDER_RIGHTRUNG_STUD2 = new PointSet(px.thirteen, px.one, px.zero, px.fourteen, px.two, px.one);
	
	private static final PointSet PART_FOOTLADDER_LEFTRUNG = new PointSet(px.two, px.five, px.one, px.seven, px.six, px.two);
	private static final PointSet PART_FOOTLADDER_LEFTRUNG_STUD1 = new PointSet(px.two, px.five, px.zero, px.three, px.six, px.one);
	private static final PointSet PART_FOOTLADDER_LEFTRUNG_STUD2 = new PointSet(px.six, px.five, px.zero, px.seven, px.six, px.one);
	
	private static final PointSet PART_CLASSICLADDER_LEFTSUPPORT = new PointSet(px.two, px.zero, px.zero, px.three, px.sixteen, px.three);
	private static final PointSet PART_CLASSICLADDER_RIGHTSUPPORT = new PointSet(px.thirteen, px.zero, px.zero, px.fourteen, px.sixteen, px.three);
	private static final PointSet PART_CLASSICLADDER_RUNG = new PointSet(px.three, px.one, px.one, px.thirteen, px.two, px.two);
	
	private static final PointSet PART_INDLADDER_RUNG = new PointSet(px.two, px.one, px.one, px.fourteen, px.three, px.two);
	private static final PointSet PART_INDLADDER_LEFTSTUD = new PointSet(px.two, px.one, px.zero, px.three, px.three, px.one);
	private static final PointSet PART_INDLADDER_RIGHTSTUD = new PointSet(px.thirteen, px.one, px.zero, px.fourteen, px.three, px.one);
	
	

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		int type = tile.getSubtype();
		
		switch(type)
		{
			case BlockDecor.ID_LADDER_FOOTHOLD:
			{
				return this.renderWorldFootholdLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_SIMPLE:
			{
				return this.renderWorldSimpleLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_ROPE:
			{
				return this.renderWorldRopeLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_FIXED:
			{
				return this.renderWorldFixedPlankLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_CLASSIC:
			{
				return this.renderWorldClassicLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_INDUSTRIAL:
			{
				return this.renderWorldIndustrialLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
			
			case BlockDecor.ID_LADDER_POLE:
			{
				return this.renderWorldPoleLadderBlock(world, x, y, z, block, tile, renderer, type);
			}
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
			
			case BlockDecor.ID_LADDER_FOOTHOLD:
			{
				this.renderInventoryFootholdLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_SIMPLE:
			{
				this.renderInventorySimpleLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_ROPE:
			{
				this.renderInventoryRopeLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_FIXED:
			{
				this.renderInventoryFixedPlankLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_CLASSIC:
			{
				this.renderInventoryClassicLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_INDUSTRIAL:
			{
				this.renderInventoryIndustrialLadderBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_LADDER_POLE:
			{
				this.renderInventoryPoleLadderBlock(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	
	









	private static final PointSet PART_FIXEDLADDER_RUNG = new PointSet(px.one, px.two, px.zero, px.fifteen, px.six, px.one);

	private boolean renderWorldFixedPlankLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{

		
		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);

		
		if(tile == null)
		{
			return true;
		}
		
		
		ForgeDirection fdirection = ForgeDirection.getOrientation(te.getDirection());	
		PointSet rung = drh.translate(ForgeDirection.NORTH, fdirection, PART_FIXEDLADDER_RUNG);
		
		float inc = px.eight;
		
		for(int i = 0; i < 2; i++)
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, rung);
			
			rung.y1 += inc;
			rung.y2 += inc;
		}


		return true;
	}
	
	private boolean renderWorldIndustrialLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{

		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		if(tile == null)
		{
			return true;
		}
		
		float inc = px.four;
		
		ForgeDirection fdirection = ForgeDirection.getOrientation(te.getDirection());
		
		PointSet rung = drh.translate(ForgeDirection.NORTH, fdirection, PART_INDLADDER_RUNG);
		PointSet leftstud = drh.translate(ForgeDirection.NORTH, fdirection, PART_INDLADDER_LEFTSTUD);
		PointSet rightstud = drh.translate(ForgeDirection.NORTH, fdirection, PART_INDLADDER_RIGHTSTUD);
		
		for(int i = 0; i < 4; i++)
		{
			drh.setRenderBounds(renderer, rung);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			
			drh.setRenderBounds(renderer, leftstud);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			
			drh.setRenderBounds(renderer, rightstud);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			
			rung.y1 += inc;
			rung.y2 += inc;
			leftstud.y1 += inc;
			leftstud.y2 += inc;
			rightstud.y1 += inc;
			rightstud.y2 += inc;
		}
		
		return true;
	}
	
	private boolean renderWorldClassicLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{

		
		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);

		
		if(tile == null)
		{
			return true;
		}
				
		float inc = px.four;
		
		int side = te.getDirection();
		ForgeDirection fdirection = ForgeDirection.getOrientation(side);
		
		
		PointSet left = drh.translate(ForgeDirection.NORTH, fdirection, PART_CLASSICLADDER_LEFTSUPPORT);
		PointSet right = drh.translate(ForgeDirection.NORTH, fdirection, PART_CLASSICLADDER_RIGHTSUPPORT);
		PointSet rung = drh.translate(ForgeDirection.NORTH, fdirection, PART_CLASSICLADDER_RUNG);

		

		
		drh.setRenderBounds(renderer, left);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
		
		drh.setRenderBounds(renderer, right);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
		
		for(int i = 0; i < 4; i++)
		{
			
			drh.setRenderBounds(renderer, rung);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			
			rung.y1 += inc;
			rung.y2 += inc;
		}
		
		return true;
	}
	
	
	private boolean renderWorldFootholdLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		if(tile == null)
		{
			return true;
		}
		
		
		float ymin = px.one;
		float ymax = px.two;
		float inc = px.four;
		
		//renderer.setRenderBounds(twoPx, zeroPx, onePx, fourteenPx, sixteenPx, twoPx);
		
		int direction = te.getDirection();
		ForgeDirection fdirection = ForgeDirection.getOrientation(direction);
		
		PointSet rightrung = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_RIGHTRUNG);
		PointSet rightstud1 = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_RIGHTRUNG_STUD1);
		PointSet rightstud2 = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_RIGHTRUNG_STUD2);
		
		PointSet leftrung = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_LEFTRUNG);
		PointSet leftstud1 = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_LEFTRUNG_STUD1);
		PointSet leftstud2 = drh.translate(ForgeDirection.NORTH, fdirection, PART_FOOTLADDER_LEFTRUNG_STUD2);
		
		for(int i = 0; i < 4; i++)
		{
			if(i % 2 == 0)
			{				

				
				rightrung.y1 = ymin;
				rightrung.y2 = ymax;
				rightstud1.y1 = ymin;
				rightstud1.y2 = ymax;
				rightstud2.y1 = ymin;
				rightstud2.y2 = ymax;
				
				drh.setRenderBounds(renderer, rightrung);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, rightstud1);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, rightstud2);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			}
			else
			{				
				
				leftrung.y1 = ymin;
				leftrung.y2 = ymax;
				leftstud1.y1 = ymin;
				leftstud1.y2 = ymax;
				leftstud2.y1 = ymin;
				leftstud2.y2 = ymax;
				
				
				drh.setRenderBounds(renderer, leftrung);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, leftstud1);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, leftstud2);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			}
			
			ymin += inc;
			ymax += inc;
		}
		
		return true;
	}
	
	private boolean renderWorldRopeLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		if(tile == null)
		{
			return true;
		}
		
		boolean renderEnd = false;
		
		if(world.isAirBlock(x, y + 1, z) == false && world.getBlock(x, y + 1, z).isNormalCube() == true)
		{
			renderEnd = true;
		}
		
		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)tile;
		
		int direction = te.getDirection();
		ForgeDirection fdirection = ForgeDirection.getOrientation(direction);
		
		PointSet rope = drh.translate(ForgeDirection.NORTH, fdirection, PART_ROPELADDER_ROPE);
		PointSet top = drh.translate(ForgeDirection.NORTH, fdirection, PART_ROPELADDER_TOP);
		
		if(renderEnd)
		{
			renderer.setOverrideBlockTexture(block.getIcon(3, 2));
			drh.setRenderBounds(renderer, top);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			renderer.clearOverrideBlockTexture();
		}

		drh.setRenderBounds(renderer, rope);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);							
		
		return true;
	}
	
	private boolean renderWorldSimpleLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
	
		if(tile == null)
		{
			return true;
		}
		
		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)tile;
				
		double ymin = px.three;
		double ymax = px.four;
		double inc = px.eight;
		
		
		int direction = te.getDirection();
		ForgeDirection fdirection = ForgeDirection.getOrientation(direction);
		
		PointSet leftSupport = drh.translate(ForgeDirection.NORTH, fdirection, PART_SIMPLELADDER_LEFTSUPPORT);
		PointSet rightSupport = drh.translate(ForgeDirection.NORTH, fdirection, PART_SIMPLELADDER_RIGHTSUPPORT);
		PointSet rung = drh.translate(ForgeDirection.NORTH, fdirection, PART_SIMPLELADDER_RUNG);
		
		drh.setRenderBounds(renderer, leftSupport);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
		
		drh.setRenderBounds(renderer, rightSupport);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
		
		for(int i = 0; i < 2; i++)
		{
			drh.setRenderBounds(renderer, rung);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			
			rung.y1 += inc;
			rung.y2 += inc;
		}
		return true;
	}
	
	private boolean renderWorldPoleLadderBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntity te, RenderBlocks renderer, int type)
	{

		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)te;

		
		if(tile == null)
		{
			return true;
		}
				
		float ymin = px.one;
		float ymax = px.two;
		float yinc = px.four;

		int direction = tile.getDirection();
		ForgeDirection fdirection = ForgeDirection.getOrientation(direction);

		PointSet support = drh.translate(ForgeDirection.NORTH, fdirection, PART_POLELADDER_SUPPORT);
		PointSet leftpeg = drh.translate(ForgeDirection.NORTH, fdirection, PART_POLELADDER_LEFTPEG);
		PointSet rightpeg = drh.translate(ForgeDirection.NORTH, fdirection, PART_POLELADDER_RIGHTPEG);
		PointSet leftpegStud = drh.translate(ForgeDirection.NORTH, fdirection, PART_POLELADDER_LEFTSTUD);
		PointSet rightpegStud = drh.translate(ForgeDirection.NORTH, fdirection, PART_POLELADDER_RIGHTSTUD);
		
		drh.setRenderBounds(renderer, support);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
		
		for(int i = 0; i < 4; i++)
		{
			
			if(i % 2 == 0)
			{
				rightpeg.y1 = ymax;
				rightpeg.y2 = ymax + px.one;
				rightpegStud.y1 = ymax + px.one;
				rightpegStud.y2 = rightpeg.y2 + px.one;
				
				drh.setRenderBounds(renderer, rightpeg);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, rightpegStud);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
			}
			else
			{
				
				leftpeg.y1 = (float)ymin;
				leftpeg.y2 = (float)ymax;
				leftpegStud.y1 = ymax;
				leftpegStud.y2 = leftpeg.y2 + px.one;
				
				drh.setRenderBounds(renderer, leftpeg);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
				
				drh.setRenderBounds(renderer, leftpegStud);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);

			}
			
			ymin += yinc;
			ymax += yinc;
		}
		return true;
	}
	
	
	private void renderInventoryFootholdLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		GL11.glScaled(1.5, 1.5, 1.5);
		
		float ymin = px.one;
		float ymax = px.two;
		float inc = px.four;
		float zoffset = px.seven;
		
		PointSet rightrung = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_RIGHTRUNG), zoffset);
		PointSet rightstud1 = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_RIGHTRUNG_STUD1), zoffset);
		PointSet rightstud2 = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_RIGHTRUNG_STUD2), zoffset);
		
		PointSet leftrung = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_LEFTRUNG), zoffset);
		PointSet leftstud1 = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_LEFTRUNG_STUD1), zoffset);
		PointSet leftstud2 = drh.centerForInventoryRender(new PointSet(PART_FOOTLADDER_LEFTRUNG_STUD2), zoffset);
		
		for(int i = 0; i < 4; i++)
		{
			if(i % 2 == 0)
			{				

				
				rightrung.y1 = ymin;
				rightrung.y2 = ymax;
				rightstud1.y1 = ymin;
				rightstud1.y2 = ymax;
				rightstud2.y1 = ymin;
				rightstud2.y2 = ymax;
				
				drh.setRenderBounds(renderer, rightrung);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, rightstud1);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, rightstud2);
				drh.tessellateInventoryBlock(renderer, block, subtype);
			}
			else
			{				
				
				leftrung.y1 = ymin;
				leftrung.y2 = ymax;
				leftstud1.y1 = ymin;
				leftstud1.y2 = ymax;
				leftstud2.y1 = ymin;
				leftstud2.y2 = ymax;
				
				
				drh.setRenderBounds(renderer, leftrung);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, leftstud1);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, leftstud2);
				drh.tessellateInventoryBlock(renderer, block, subtype);
			}
			
			ymin += inc;
			ymax += inc;
		}
	}
	
	private void renderInventoryPoleLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		drh.setGL11Scale(1.3);

		float ymin = px.one;
		float ymax = px.two;
		float yinc = px.four;
	
		float zoffset = px.six;
		
		//The central pole
		PointSet support = drh.centerForInventoryRender(new PointSet(PART_POLELADDER_SUPPORT), zoffset);
		PointSet leftpeg = drh.centerForInventoryRender(new PointSet(PART_POLELADDER_LEFTPEG), zoffset);
		PointSet rightpeg = drh.centerForInventoryRender(new PointSet(PART_POLELADDER_RIGHTPEG), zoffset);
		PointSet leftpegStud = drh.centerForInventoryRender(new PointSet(PART_POLELADDER_LEFTSTUD), zoffset);
		PointSet rightpegStud = drh.centerForInventoryRender(new PointSet(PART_POLELADDER_RIGHTSTUD), zoffset);
		
		
		drh.setRenderBounds(renderer, support);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		for(int i = 0; i < 4; i++)
		{
			
			if(i % 2 == 0)
			{
				rightpeg.y1 = ymax;
				rightpeg.y2 = ymax + px.one;
				rightpegStud.y1 = ymax + px.one;
				rightpegStud.y2 = rightpeg.y2 + px.one;
				
				drh.setRenderBounds(renderer, rightpeg);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, rightpegStud);
				drh.tessellateInventoryBlock(renderer, block, subtype);
			}
			else
			{
				
				leftpeg.y1 = (float)ymin;
				leftpeg.y2 = (float)ymax;
				leftpegStud.y1 = ymax;
				leftpegStud.y2 = leftpeg.y2 + px.one;
				
				drh.setRenderBounds(renderer, leftpeg);
				drh.tessellateInventoryBlock(renderer, block, subtype);
				
				drh.setRenderBounds(renderer, leftpegStud);
				drh.tessellateInventoryBlock(renderer, block, subtype);

			}
			
			ymin += yinc;
			ymax += yinc;
		}
	}
	
	
	private void renderInventorySimpleLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.setGL11Scale(1.3);
		
		double ymin = px.three;
		double ymax = px.four;
		double inc = px.eight;
		
		double zmin = px.seven;
		double zmax = px.eight;


		renderer.setRenderBounds(px.two, px.zero, zmin, px.three, px.sixteen, zmax);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.thirteen, px.zero, zmin, px.fourteen, px.sixteen, zmax);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		for(int i = 0; i < 2; i++)
		{
			renderer.setRenderBounds(px.three, ymin, zmin, px.thirteen, ymax, zmax);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			ymin += inc;
			ymax += inc;
		}
	}
	
	private void renderInventoryRopeLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.setGL11Scale(1.3);
		
		renderer.setOverrideBlockTexture(block.getIcon(3, 2));
		renderer.setRenderBounds(px.six, px.fifteen, px.six, px.ten, px.sixteen, px.nine);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		renderer.clearOverrideBlockTexture();
		
		renderer.setRenderBounds(px.seven, px.zero, px.seven, px.nine, px.fifteen, px.eight);
		drh.tessellateInventoryBlock(renderer, block, subtype);
	}
	
	private void renderInventoryFixedPlankLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.setGL11Scale(1.3);
		
		double ymin = px.two;
		double ymax = px.six;
		double inc = px.eight;
		
		for(int i = 0; i < 2; i++)
		{
			renderer.setRenderBounds(px.one, ymin, px.seven, px.fifteen, ymax, px.eight);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			ymin += inc;
			ymax += inc;
		}
	}
	
	private void renderInventoryIndustrialLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		GL11.glScaled(1.3, 1.3, 1.3);
		
		float ymin = px.one;
		float ymax = px.three;
		float inc = px.four;
		
		for(int i = 0; i < 4; i++)
		{
			renderer.setRenderBounds(px.two, ymin, px.seven, px.fourteen, ymax, px.eight);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			renderer.setRenderBounds(px.two, ymin, px.six, px.three, ymax, px.seven);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			
			renderer.setRenderBounds(px.thirteen, ymin, px.six, px.fourteen, ymax, px.seven);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			ymin += inc;
			ymax += inc;
		}
	}
	
	private void renderInventoryClassicLadderBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		GL11.glScaled(1.3, 1.3, 1.3);
		
		float ymin = px.one;
		float ymax = px.three;
		float inc = px.four;
			
			renderer.setRenderBounds(px.two, px.zero, px.six, px.three, px.sixteen, px.nine);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			
			renderer.setRenderBounds(px.thirteen, px.zero, px.six, px.fourteen, px.sixteen, px.nine);
			drh.tessellateInventoryBlock(renderer, block, subtype);
		
			for(int i = 0; i < 4; i++)
			{
				renderer.setRenderBounds(px.two, ymin, px.seven, px.fourteen, ymax, px.eight);
				drh.tessellateInventoryBlock(renderer, block, subtype);

				ymin += inc;
				ymax += inc;
			}
	}

}
