package kappafox.di.decorative.renderers;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.TextureOffset;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.blocks.SubBlockWall;

public class SubRendererWall extends SubBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		int subtype = tile.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_WALL_DISCRETE:
				return this.renderWorldDiscreteWallBlock(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_SIMPLE:
				return this.renderWorldDiscreteWallRailingSimple(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_DOUBLE:
				return this.renderWorldDiscreteWallRailingDouble(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_TRIPLE:
				return this.renderWorldDiscreteWallRailingTriple(world, x, y, z, block, tile, renderer, subtype);				
		    case 913: 
		        return renderWorldDiscreteWallRailingSquare(world, x, y, z, block, tile, renderer, subtype);
	    	case 914: 
	    		return renderWorldDiscreteWallRailingSquareHalved(world, x, y, z, block, tile, renderer, subtype);
			case 915: 
				return renderWorldDiscreteWallRailingSquareQuartered(world, x, y, z, block, tile, renderer, subtype);
			case 916: 
				return renderWorldDiscreteWallDangerTape(world, x, y, z, block, tile, renderer, subtype);
			case 950: 
				return renderWorldDiscretePanelSquare(world, x, y, z, block, tile, renderer, subtype);
			case 951: 
				return renderWorldDiscretePanelSquareHalved(world, x, y, z, block, tile, renderer, subtype);
			case 952: 
				return renderWorldDiscretePanelSquareQuartered(world, x, y, z, block, tile, renderer, subtype);
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
			case 900: 
				renderInventoryDiscreteWall(block, subtype, modelID, renderer);
				break;
			case 910: 
				renderInventoryDiscreteWallRailingSimple(block, subtype, modelID, renderer);
				break;
			case 911: 
				renderInventoryDiscreteWallRailingSimpleDouble(block, subtype, modelID, renderer);
				break;
			case 912: 
				renderInventoryDiscreteWallRailingSimpleTriple(block, subtype, modelID, renderer);
				break;
			case 913: 
				renderInventoryDiscreteWallRailingSquare(block, subtype, modelID, renderer);
				break;
			case 914: 
				renderInventoryDiscreteWallRailingSquareHalved(block, subtype, modelID, renderer);
				break;
			case 915: 
				renderInventoryDiscreteWallRailingSquareQuartered(block, subtype, modelID, renderer);
				break;
			case 916: 
				renderInventoryDiscreteWallDangerTape(block, subtype, modelID, renderer);
				break;
			case 950: 
				renderInventoryDiscretePanelSquare(block, subtype, modelID, renderer);
				break;
			case 951: 
				renderInventoryDiscretePanelSquareHalved(block, subtype, modelID, renderer);
				break;
			case 952: 
				renderInventoryDiscretePanelSquareQuartered(block, subtype, modelID, renderer);
				break;
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	
	private void renderInventoryDiscreteWallDangerTape(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_DANGER_TAPE_POST);
		renderer.setOverrideBlockTexture(kappafox.di.decorative.blocks.BlockHazard.diagonalIcons[0]);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.EAST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.WEST));
		renderer.clearOverrideBlockTexture();
	}

	private void renderInventoryDiscreteWall(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_EAST);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_WEST);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_POST);
	}

	private void renderInventoryDiscreteWallRailingSimple(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH.translateTo(ForgeDirection.EAST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
	}

	private void renderInventoryDiscreteWallRailingSimpleDouble(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscreteWallRailingSimple(block, subtype, modelID, renderer);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH.translateTo(ForgeDirection.EAST));
	}

	private void renderInventoryDiscreteWallRailingSimpleTriple(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscreteWallRailingSimpleDouble(block, subtype, modelID, renderer);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH.translateTo(ForgeDirection.EAST));
	}

	private void renderInventoryDiscreteWallRailingSquare(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscretePanelSquare(block, subtype, modelID, renderer);
	}

	private void renderInventoryDiscreteWallRailingSquareHalved(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscretePanelSquareHalved(block, subtype, modelID, renderer);
	}

	private void renderInventoryDiscreteWallRailingSquareQuartered(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscretePanelSquareQuartered(block, subtype, modelID, renderer);
	}

	private void renderInventoryDiscretePanelSquare(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_BAR_BOT.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_BAR_TOP.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.WEST));
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.EAST));
	}

	private void renderInventoryDiscretePanelSquareHalved(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscretePanelSquare(block, subtype, modelID, renderer);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_BAR_MID.translateTo(ForgeDirection.EAST));
	}

	private void renderInventoryDiscretePanelSquareQuartered(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderInventoryDiscretePanelSquareHalved(block, subtype, modelID, renderer);
		drh.tessellateInventoryBlock(renderer, block, subtype, SubBlockWall.PART_PANEL_POST_MID);
	}
	
	private boolean renderWorldDiscreteWallBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock t, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)t).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world, x, y + 1, z) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = adjMap.get(ForgeDirection.UP) && !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		//with 0, 1 or 3+ we draw the 'post'
		if(adjCount != 2 || topBlock || SubBlockWall.isCorner(adjMap))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
		}
		
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();
			off.setOffsetU(5, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_NORTH_EXT : SubBlockWall.PART_WALL_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();
			off.setOffsetU(5, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_SOUTH_EXT : SubBlockWall.PART_WALL_SOUTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();
			off.setOffsetU(2, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_EAST_EXT : SubBlockWall.PART_WALL_EAST);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();
			off.setOffsetU(2, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_WEST_EXT : SubBlockWall.PART_WALL_WEST);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingSimple(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world, x, y + 1, z) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{

			off = new TextureOffset();
			/*
			off.setOffsetU(5, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			*/
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(5, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			*/
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(2, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			*/
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(2, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			*/
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingDouble(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world, x, y + 1, z) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingTriple(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world, x, y + 1, z) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingSquare(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		if ((adjMap.size() == 0) || (conMap.size() == 0)) {
		return false;
		}
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_POST);
		if (((Boolean)adjMap.get(ForgeDirection.NORTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH);
		}
		if (((Boolean)adjMap.get(ForgeDirection.SOUTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.SOUTH));
		}
		if (((Boolean)adjMap.get(ForgeDirection.EAST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.EAST));
		}
		if (((Boolean)adjMap.get(ForgeDirection.WEST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.WEST));
		}
		return false;
	}

	private boolean renderWorldDiscreteWallDangerTape(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		if ((adjMap.size() == 0) || (conMap.size() == 0)) {
		return false;
		}
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		
		
		IIcon tapeIcon = kappafox.di.decorative.blocks.BlockHazard.diagonalIcons[0];
		if (tile.getSecondaryBlockTexture() != null) 
		{
			tapeIcon = tile.getSecondaryBlockTexture();
		}
		
		if ((adjCount == 1) || (adjCount == 0) || (adjCount > 2) || (SubBlockWall.isCorner(adjMap))) 
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_DANGER_TAPE_POST);
		}
		
		if (((Boolean)adjMap.get(ForgeDirection.NORTH)).booleanValue())
		{
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH);
			renderer.setOverrideBlockTexture(tapeIcon);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		}
		
		if (((Boolean)adjMap.get(ForgeDirection.SOUTH)).booleanValue())
		{
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.SOUTH));
			renderer.setOverrideBlockTexture(tapeIcon);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		}
		
		if (((Boolean)adjMap.get(ForgeDirection.EAST)).booleanValue())
		{
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.EAST));
			renderer.setOverrideBlockTexture(tapeIcon);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		}
		
		if (((Boolean)adjMap.get(ForgeDirection.WEST)).booleanValue())
		{
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.WEST));
			renderer.setOverrideBlockTexture(tapeIcon);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		}
		
		renderer.clearOverrideBlockTexture();
		
		return false;
	}

	private boolean renderWorldDiscreteWallRailingSquareHalved(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		if ((adjMap.size() == 0) || (conMap.size() == 0)) {
		return false;
		}
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_POST);
		if (((Boolean)adjMap.get(ForgeDirection.NORTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_HALVED_MID_NORTH);
		}
		if (((Boolean)adjMap.get(ForgeDirection.SOUTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_HALVED_MID_NORTH.translateTo(ForgeDirection.SOUTH));
		}
		if (((Boolean)adjMap.get(ForgeDirection.EAST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_HALVED_MID_NORTH.translateTo(ForgeDirection.EAST));
		}
		if (((Boolean)adjMap.get(ForgeDirection.WEST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_HALVED_MID_NORTH.translateTo(ForgeDirection.WEST));
		}
		return false;
	}

	private boolean renderWorldDiscreteWallRailingSquareQuartered(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		if ((adjMap.size() == 0) || (conMap.size() == 0)) {
		return false;
		}
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_POST);
		if (((Boolean)adjMap.get(ForgeDirection.NORTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_MID_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_POST_NORTH);
		}
		if (((Boolean)adjMap.get(ForgeDirection.SOUTH)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_MID_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_POST_NORTH.translateTo(ForgeDirection.SOUTH));
		}
		if (((Boolean)adjMap.get(ForgeDirection.EAST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_MID_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_POST_NORTH.translateTo(ForgeDirection.EAST));
		}
		if (((Boolean)adjMap.get(ForgeDirection.WEST)).booleanValue())
		{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_TOP_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_BOT_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_MID_NORTH.translateTo(ForgeDirection.WEST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SQUARE_QUARTERED_POST_NORTH.translateTo(ForgeDirection.WEST));
		}
		return false;
	}

	private boolean renderWorldDiscretePanelSquare(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		if (tile == null) {
		return false;
		}
		switch (tile.getDirection())
		{
		case 2: 
		case 3: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT);
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART);
			renderer.clearOverrideBlockTexture();
		}
		break;
		case 4: 
		case 5: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH).translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT.translateTo(ForgeDirection.EAST));
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART.translateTo(ForgeDirection.EAST));
			renderer.clearOverrideBlockTexture();
		}
		break;
		}
		return false;
	}

	private boolean renderWorldDiscretePanelSquareHalved(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		if (tile == null) {
		return false;
		}
		switch (tile.getDirection())
		{
		case 2: 
		case 3: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_MID);
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART);
			renderer.clearOverrideBlockTexture();
		}
		break;
		case 4: 
		case 5: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH).translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_MID.translateTo(ForgeDirection.EAST));
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART.translateTo(ForgeDirection.EAST));
			renderer.clearOverrideBlockTexture();
		}
		break;
		}
		return false;
	}

	private boolean renderWorldDiscretePanelSquareQuartered(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		if (tile == null) {
		return false;
		}
		switch (tile.getDirection())
		{
		case 2: 
		case 3: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_MID);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_MID);
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART);
			renderer.clearOverrideBlockTexture();
		}
		break;
		case 4: 
		case 5: 
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH).translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_TOP.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_BOT.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_BAR_MID.translateTo(ForgeDirection.EAST));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_POST_MID.translateTo(ForgeDirection.EAST));
		if (tile.getSecondaryBlockTexture() != null)
		{
			renderer.setOverrideBlockTexture(tile.getSecondaryBlockTexture());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_PANEL_SECONDARY_PART.translateTo(ForgeDirection.EAST));
			renderer.clearOverrideBlockTexture();
		}
		break;
		}
		return false;
	}
}
