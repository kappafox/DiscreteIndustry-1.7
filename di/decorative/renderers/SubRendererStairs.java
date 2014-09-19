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
import kappafox.di.base.util.TextureOffset;
import kappafox.di.decorative.blocks.BlockDecor;

public class SubRendererStairs extends SubBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock t = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		int subtype = t.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_STAIRS_NORMAL:
				return this.renderWorldDiscreteStairsBlock(world, x, y, z, block, t, renderer, subtype);
			
			case BlockDecor.ID_STAIRS_SMALL:
				return this.renderWorldDiscreteSmallStairsBlock(world, x, y, z, block, t, renderer, subtype);
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
			
			case BlockDecor.ID_STAIRS_NORMAL:
			{
				this.renderInventoryDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_STAIRS_SMALL:
			{
				this.renderInventorySmallDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}

	private void renderInventoryDiscreteStairs(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(px.zero, px.zero, px.zero, px.sixteen, px.eight, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(0, px.eight, 0, px.sixteen, px.sixteen, px.eight);
		drh.tessellateInventoryBlock(renderer, block, subtype);	
	}
	
	
	private void renderInventorySmallDiscreteStairs(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		float y1 = px.zero;
		float y2 = px.four;
		float z1 = px.zero;
		float z2 = px.sixteen;
		
		for(int i = 0; i < 4; i++)
		{
			renderer.setRenderBounds(px.zero, y1, z1, px.sixteen, y2, z2);
			drh.tessellateInventoryBlock(renderer, block, subtype);
			
			y1 += px.four;
			y2 += px.four;		
			z2 -= px.four;
		}
	}
	
	private boolean renderWorldDiscreteSmallStairsBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		TileEntity n = world.getTileEntity(x, y, z - 1);
		TileEntity s = world.getTileEntity(x, y, z + 1);
		TileEntity e = world.getTileEntity(x + 1, y, z);
		TileEntity w = world.getTileEntity(x - 1, y, z);
		
		TileEntityDiscreteBlock north = null;
		TileEntityDiscreteBlock south = null;
		TileEntityDiscreteBlock east = null;
		TileEntityDiscreteBlock west = null;
		
		ForgeDirection northDirection = null;
		ForgeDirection southDirection = null;
		ForgeDirection eastDirection = null;
		ForgeDirection westDirection = null;
		
		
		if(n != null && n instanceof TileEntityDiscreteBlock)
		{
			north = (TileEntityDiscreteBlock)n;
			northDirection = north.getForgeDirection();
		}
		
		if(s != null && s instanceof TileEntityDiscreteBlock)
		{
			south = (TileEntityDiscreteBlock)s;
			southDirection = south.getForgeDirection();
		}
		
		if(e != null && e instanceof TileEntityDiscreteBlock)
		{
			east = (TileEntityDiscreteBlock)e;
			eastDirection = east.getForgeDirection();
		}
		
		if(w != null && w instanceof TileEntityDiscreteBlock)
		{
			west = (TileEntityDiscreteBlock)w;
			westDirection = west.getForgeDirection();
		}
		
		int orient = tile.getVariable();
		
		float y1 = px.zero;
		float y2 = px.four;
		
		float y3 = y1 + px.four;
		float y4 = y2 + px.four;
		
		float x1 = px.zero;
		float x2 = px.sixteen;

		float yinc = px.four;
		float vinc = px.four;
		
		if(orient == 1)
		{
			y1 = px.twelve;
			y2 = px.sixteen;
			
			y3 = px.eight;
			y4 = px.twelve;
			yinc *= -1.0F;
			
		}
		
		
		//renderer.setRenderBounds(px.zero, y1, px.zero, px.sixteen, y2, px.sixteen);
		//drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		ForgeDirection direction = ForgeDirection.getOrientation(tile.getDirection());
		
		switch(direction)
		{
			//-Z
			case NORTH:
			{
				boolean shorten = false;
				boolean noCorner = false;
				
				//this block is in a line
				if((this.isSmallStair(east) && this.isSmallStair(west)) && eastDirection == direction && westDirection == direction)
				{
					if(east.getVariable() == orient && west.getVariable() == orient)
					{
						noCorner = true;
					}
				}
				
				if(noCorner == false && this.isSmallStair(north) && north.getVariable() == orient)
				{
					//stair to the EAST
					if(northDirection == ForgeDirection.EAST)
					{
						shorten = true;
					}
					
					//stair to the WEST
					if(northDirection == ForgeDirection.WEST)
					{
						shorten = true;
					}
				}
				
				float xs = x1;
				float xe = x2;
				
				int offset = 4;
				TextureOffset off = new TextureOffset();
				
				for(int i = 0; i < 4; i++)
				{
					renderer.setRenderBounds(xs, y1  + (i * yinc), x1, xe, y2  + (i * yinc), x2 - (i * vinc));
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(5, off.getOffsetU(5) + offset);
					
					if(shorten)
					{
						if(northDirection == ForgeDirection.EAST)
						{
							xs += vinc;
						}
						
						if(northDirection == ForgeDirection.WEST)
						{
							xe -= vinc;
						}

					}
				}
				
				
				//Cornering
				if(south != null && south.getSubtype() == subtype && shorten == false && noCorner == false)
				{
					float x3 = px.zero;
					float x4 = px.twelve;

					if(southDirection == ForgeDirection.WEST && south.getVariable() == orient && westDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(5, -12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.zero, y3 + (i * yinc), px.twelve - (i * vinc), px.twelve - (i * vinc), y4 + (i * yinc), x2);
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(5, off2.getOffsetU(5) + 4);
						}
					}
					

					if(southDirection == ForgeDirection.EAST && south.getVariable() == orient && eastDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(5, -12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.four + (i * vinc), y3 + (i * yinc), px.twelve - (i * vinc), px.sixteen, y4 + (i * yinc), x2);
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(5, off2.getOffsetU(5) + 4);
						}
					}
				}
				break;
			}
			
			//+Z
			case SOUTH:
			{
				boolean shorten = false;
				boolean noCorner = false;
				
				//this block is in a line
				if((this.isSmallStair(east) && this.isSmallStair(west)) && eastDirection == direction && westDirection == direction)
				{
					if(east.getVariable() == orient && west.getVariable() == orient)
					{
						noCorner = true;
					}
				}
				
				if(noCorner == false && this.isSmallStair(south) && south.getVariable() == orient)
				{
					//stair to the EAST or WEST
					if(southDirection == ForgeDirection.EAST || southDirection == ForgeDirection.WEST)
					{
						shorten = true;
					}
				}
				
				float xs = x1;
				float xe = x2;
						
				int offset = -4;
				TextureOffset off = new TextureOffset();

				for(int i = 0; i < 4; i++)
				{
					renderer.setRenderBounds(xs, y1  + (i * yinc), x1 + (i * vinc), xe, y2  + (i * yinc), x2);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(5, off.getOffsetU(5) - 4);
					
					if(shorten)
					{
						if(southDirection == ForgeDirection.EAST)
						{
							xs += vinc;
						}
						
						if(southDirection == ForgeDirection.WEST)
						{
							xe -= vinc;
						}
					}
				}

				//Cornering
				if(north != null && north.getSubtype() == subtype && shorten == false && noCorner == false)
				{
					float x3 = px.zero;
					float x4 = px.twelve;

					if(northDirection == ForgeDirection.WEST && north.getVariable() == orient && westDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(5, 12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.zero, y3 + (i * yinc), px.zero, px.twelve - (i * vinc), y4 + (i * yinc), px.four + (i * vinc));
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(5, off2.getOffsetU(5) - 4);
						}
					}
					
					

					if(northDirection == ForgeDirection.EAST && north.getVariable() == orient && eastDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(5, 12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.four + (i * vinc), y3 + (i * yinc), px.zero, px.sixteen, y4 + (i * yinc), px.four + (i * vinc));
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(5, off2.getOffsetU(5) - 4);
						}
					}
				}
				break;
			}
			
			//-X
			case WEST:
			{
				boolean shorten = false;
				boolean noCorner = false;
				
				//this block is in a line
				if((this.isSmallStair(north) && this.isSmallStair(south)) && northDirection == direction && southDirection == direction)
				{
					if(north.getVariable() == orient && south.getVariable() == orient)
					{
						noCorner = true;
					}
				}
				
				if(noCorner == false && this.isSmallStair(west) && west.getVariable() == orient)
				{
					//stair to the EAST or WEST
					if(westDirection == ForgeDirection.NORTH || westDirection == ForgeDirection.SOUTH)
					{
						shorten = true;
					}
				}
				
				float zs = x1;
				float ze = x2;
				
				int offset = 4;
				TextureOffset off = new TextureOffset();

				for(int i = 0; i < 4; i++)
				{
					renderer.setRenderBounds(x1, y1  + (i * yinc), zs, x2 - (i * vinc), y2  + (i * yinc), ze);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					
					
					if(shorten)
					{
						if(westDirection == ForgeDirection.SOUTH)
						{
							zs += vinc;
							off.setOffsetU(5, off.getOffsetU(5) - 4);
							off.setOffsetU(2, off.getOffsetU(2) + 4);
						}
						
						if(westDirection == ForgeDirection.NORTH)
						{
							ze -= vinc;
						}
					}
					else
					{
						off.setOffsetU(2, off.getOffsetU(2) + 4);
					}
				}
				
				//Cornering
				if(east != null && east.getSubtype() == subtype && shorten == false && noCorner == false)
				{
					float x3 = px.zero;
					float x4 = px.twelve;

					if(eastDirection == ForgeDirection.NORTH && east.getVariable() == orient && northDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(2, -12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.twelve - (i * vinc), y3 + (i * yinc), px.zero, px.sixteen, y4 + (i * yinc), px.twelve - (i * vinc));
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(2, off2.getOffsetU(2) + 4);
						}
					}
					
					
					
					if(eastDirection == ForgeDirection.SOUTH && east.getVariable() == orient && southDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(2, -12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.twelve - (i * vinc), y3 + (i * yinc), px.four + (i * vinc), px.sixteen, y4 + (i * yinc), px.sixteen);
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(2, off2.getOffsetU(2) + 4);
						}
					}
					
				}
				break;
			}
			
			//+X
			case EAST:
			{
				
				boolean shorten = false;
				boolean noCorner = false;
				
				//this block is in a line
				if((this.isSmallStair(north) && this.isSmallStair(south)) && northDirection == direction && southDirection == direction)
				{
					if(north.getVariable() == orient && south.getVariable() == orient)
					{
						noCorner = true;
					}
				}
				
				if(noCorner == false && this.isSmallStair(east) && east.getVariable() == orient)
				{
					//stair to the EAST or WEST
					if(eastDirection == ForgeDirection.NORTH || eastDirection == ForgeDirection.SOUTH)
					{
						shorten = true;
					}
				}
				
				float zs = x1;
				float ze = x2;
				
				int offset = -4;
				TextureOffset off = new TextureOffset();

				for(int i = 0; i < 4; i++)
				{
					renderer.setRenderBounds(x1 + (i * vinc), y1  + (i * yinc), zs, x2, y2  + (i * yinc), ze);
					drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					
					off.setOffsetU(2, off.getOffsetU(2) - 4);
					
					if(shorten)
					{
						if(eastDirection == ForgeDirection.SOUTH)
						{
							zs += vinc;
							//off.setOffsetU(5, off.getOffsetU(5) - 4);
							//off.setOffsetU(2, off.getOffsetU(2) + 4);
						}
						
						if(eastDirection == ForgeDirection.NORTH)
						{
							ze -= vinc;
						}
					}
					
				}
				
				//Cornering
				if(west != null && west.getSubtype() == subtype && shorten == false && noCorner == false)
				{
					float x3 = px.zero;
					float x4 = px.twelve;

					if(westDirection == ForgeDirection.NORTH && west.getVariable() == orient && northDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(2, 12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.zero, y3 + (i * yinc), px.zero, px.four + (i * vinc), y4 + (i * yinc), px.twelve - (i * vinc));
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(2, off2.getOffsetU(2) - 4);
						}
					}
					
					
					
					if(westDirection == ForgeDirection.SOUTH && west.getVariable() == orient && southDirection != direction)
					{
						TextureOffset off2 = new TextureOffset();
						off2.setOffsetU(2, 12);
						
						for(int i = 0; i < 3; i++)
						{
							renderer.setRenderBounds(px.zero, y3 + (i * yinc), px.four + (i * vinc), px.four + (i * vinc), y4 + (i * yinc), px.sixteen);
							drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off2);
							off2.setOffsetU(2, off2.getOffsetU(2) - 4);
						}
					}
					
					
				}
				break;
			}
			
			default:
			{
				//UP, DOWN and UNKNOWN are not valid
				break;
			}
			
		}

        return true;	
	}


	private boolean renderWorldDiscreteStairsBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype)
	{
		
		TileEntity n = world.getTileEntity(x, y, z - 1);
		TileEntity s = world.getTileEntity(x, y, z + 1);
		TileEntity e = world.getTileEntity(x + 1, y, z);
		TileEntity w = world.getTileEntity(x - 1, y, z);
		
		TileEntityDiscreteBlock north = null;
		TileEntityDiscreteBlock south = null;
		TileEntityDiscreteBlock east = null;
		TileEntityDiscreteBlock west = null;
		
		
		if(n != null && n instanceof TileEntityDiscreteBlock)
		{
			north = (TileEntityDiscreteBlock)n;
		}
		
		if(s != null && s instanceof TileEntityDiscreteBlock)
		{
			south = (TileEntityDiscreteBlock)s;
		}
		
		if(e != null && e instanceof TileEntityDiscreteBlock)
		{
			east = (TileEntityDiscreteBlock)e;
		}
		
		if(w != null && w instanceof TileEntityDiscreteBlock)
		{
			west = (TileEntityDiscreteBlock)w;
		}
		
		
		//int dir = tile.getDirection();
		int ori = tile.getVariable();
		
		float y1 = px.zero;
		float y2 = px.eight;
		float y3 = px.eight;
		float y4 = px.sixteen;
		
		if(ori == 1)
		{
			float f = y1;
			
			y1 = y3;
			y3 = f;
			f = y2;
			y2 = y4;
			y4 = f;
		}
		
		ForgeDirection direction = ForgeDirection.getOrientation(tile.getDirection());
		
		renderer.setRenderBounds(px.zero, y1, px.zero, px.sixteen, y2, px.sixteen);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		switch(direction)
		{
			//-Z
			case NORTH:
			{

				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, 8);	//Offsetting +X
				
				double x1 = px.zero;
				double x2 = px.sixteen;
				boolean connected = false;
				
				if(north != null)
				{
					if(north.getSubtype() == subtype)
					{
						ForgeDirection ndirection = ForgeDirection.getOrientation(north.getDirection());
						
						if(ndirection == ForgeDirection.WEST)
						{
							x2 = px.eight;
							connected = true;
						}
						
						if(ndirection == ForgeDirection.EAST)
						{
							x1 = px.eight;
							connected = true;
						}
					}				
				}
				
				if(south != null && connected == false && south.getSubtype() == subtype)
				{
					ForgeDirection sdirection = ForgeDirection.getOrientation(south.getDirection());
					off.setOffsetU(5, -8);
					if(sdirection == ForgeDirection.WEST)
					{
						renderer.setRenderBounds(x1, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(sdirection == ForgeDirection.EAST)
					{
						renderer.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(5, 8);
				}
				
				renderer.setRenderBounds(x1, y3, px.zero, x2, y4, px.eight);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//+Z
			case SOUTH:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(5, -8);	//Offsetting -X

				double x1 = px.zero;
				double x2 = px.sixteen;
				boolean connected = false;
				if(south != null)
				{
					if(south.getSubtype() == subtype)
					{
						ForgeDirection dir2 = ForgeDirection.getOrientation(south.getDirection());
						
						if(dir2 == ForgeDirection.WEST)
						{
							connected = true;
							x2 = px.eight;
						}
						
						if(dir2 == ForgeDirection.EAST)
						{
							connected = true;
							x1 = px.eight;
						}
					}
					
				}
				
				if(north != null  && connected == false && north.getSubtype() == subtype)
				{
					ForgeDirection dir3 = ForgeDirection.getOrientation(north.getDirection());
					off.setOffsetU(5, 8);
					if(dir3 == ForgeDirection.WEST)
					{
						renderer.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == ForgeDirection.EAST)
					{
						renderer.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(5, -8);
				}
				
				renderer.setRenderBounds(x1, y3, px.eight, x2, y4, px.sixteen);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//-X
			case WEST:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, 8);	//Offsetting -Z
				
				double z1 = px.zero;
				double z2 = px.sixteen;
				boolean connected = false;
				
				if(west != null)
				{
					if(west.getSubtype() == subtype)
					{
						ForgeDirection wdirection = ForgeDirection.getOrientation(west.getDirection());
						
						if(wdirection == ForgeDirection.NORTH)
						{
							connected = true;
							z2 = px.eight;
						}
						
						if(wdirection == ForgeDirection.SOUTH)
						{
							connected = true;
							z1 = px.eight;
						}
					}
					
				}
				
				if(east != null && connected == false && east.getSubtype() == subtype)
				{
					ForgeDirection dir3 = ForgeDirection.getOrientation(east.getDirection());
					
					off.setOffsetU(2, -8);
					if(dir3 == ForgeDirection.NORTH)
					{
						renderer.setRenderBounds(px.eight, y3, px.zero, px.sixteen, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == ForgeDirection.SOUTH)
					{
						renderer.setRenderBounds(px.eight, y3, px.eight, px.sixteen, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					off.setOffsetU(2, 8);
				}
				
				
				renderer.setRenderBounds(px.zero, y3, z1, px.eight, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			//+X
			case EAST:
			{
				TextureOffset off = new TextureOffset();
				off.setOffsetU(2, -8);	//Offsetting -Z
				
				double z1 = px.zero;
				double z2 = px.sixteen;
				boolean connected = false;
				
				
				if(east != null)
				{
					if(east.getSubtype() == subtype)
					{
						ForgeDirection dir2 = ForgeDirection.getOrientation(east.getDirection());
						if(dir2 == ForgeDirection.NORTH)
						{
							connected = true;
							z2 = px.eight;
						}
						
						if(dir2 == ForgeDirection.SOUTH)
						{
							connected = true;
							z1 = px.eight;
						}
					}
					
				}
				
				if(west != null && connected == false && west.getSubtype() == subtype)
				{
					off.setOffsetU(2, 8);
					ForgeDirection dir3 = ForgeDirection.getOrientation(west.getDirection());
					
					if(dir3 == ForgeDirection.NORTH)
					{
						renderer.setRenderBounds(px.zero, y3, px.zero, px.eight, y4, px.eight);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					
					if(dir3 == ForgeDirection.SOUTH)
					{
						renderer.setRenderBounds(px.zero, y3, px.eight, px.eight, y4, px.sixteen);
						drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
					}
					off.setOffsetU(2, -8);
				}
				
				renderer.setRenderBounds(px.eight, y3, z1, px.sixteen, y4, z2);
				drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off);
				break;
			}
			
			default:
			{
				//UP,DOWN,UNKNOWN not needed
				break;
			}
		}

        return true;
	}
	
	
	private boolean isSmallStair(TileEntityDiscreteBlock tile)
	{
		if(tile != null && tile instanceof TileEntityDiscreteBlock && tile.getSubtype() == BlockDecor.ID_STAIRS_SMALL)
		{
			return true;
		}
		
		return false;
	}
}
