package kappafox.di.decorative.blocks;

import ic2.api.item.IC2Items;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.compat.ToolHelper;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.SideHelper;
import kappafox.di.decorative.DiscreteDecorativePacketHandler;
import kappafox.di.decorative.network.DiscreteDecorativeTextureSyncPacket;

public class SubBlockWall extends SubBlock
{
	
	private static final float TOLERANCE = 0.3F;
	public static final float collisionHeight = px.sixteen + px.eight;
	
	// Discrete Wall
	public static final PointSet PART_WALL_POST = new PointSet(px.four, px.zero, px.four, px.twelve, px.sixteen, px.twelve);
	public static final PointSet PART_WALL_NORTH = new PointSet(px.five, px.zero, px.zero, px.eleven, px.thirteen, px.eight);
	public static final PointSet PART_WALL_SOUTH = new PointSet(px.five, px.zero, px.eight, px.eleven, px.thirteen, px.sixteen);
	public static final PointSet PART_WALL_EAST = new PointSet(px.eight, px.zero, px.five, px.sixteen, px.thirteen, px.eleven);
	public static final PointSet PART_WALL_WEST = new PointSet(px.zero, px.zero, px.five, px.eight, px.thirteen, px.eleven);
	
	public static final PointSet PART_WALL_POST_TALL = new PointSet(px.four, px.zero, px.four, px.twelve, collisionHeight, px.twelve);
	public static final PointSet PART_WALL_NORTH_TALL = new PointSet(px.five, px.zero, px.zero, px.eleven, collisionHeight, px.eight);
	public static final PointSet PART_WALL_SOUTH_TALL = new PointSet(px.five, px.zero, px.eight, px.eleven, collisionHeight, px.sixteen);
	public static final PointSet PART_WALL_EAST_TALL = new PointSet(px.eight, px.zero, px.five, px.sixteen, collisionHeight, px.eleven);
	public static final PointSet PART_WALL_WEST_TALL = new PointSet(px.zero, px.zero, px.five, px.eight, collisionHeight, px.eleven);
	
	public static final PointSet PART_WALL_POST_EXT = new PointSet(px.four, -px.three, px.four, px.twelve, px.sixteen, px.twelve);
	public static final PointSet PART_WALL_NORTH_EXT = new PointSet(px.five, -px.three, px.zero, px.eleven, px.thirteen, px.eight);
	public static final PointSet PART_WALL_SOUTH_EXT = new PointSet(px.five, -px.three, px.eight, px.eleven, px.thirteen, px.sixteen);
	public static final PointSet PART_WALL_EAST_EXT = new PointSet(px.eight, -px.three, px.five, px.sixteen, px.thirteen, px.eleven);
	public static final PointSet PART_WALL_WEST_EXT = new PointSet(px.zero, -px.three, px.five, px.eight, px.thirteen, px.eleven);
	
	public static final PointSet PART_WALL_NORTH_SHORT = new PointSet(px.five, px.fifteen, px.zero, px.eleven, collisionHeight, px.eight);
	
	// Simple Rail
	public static final PointSet PART_WALL_RAILING_SIMPLE_POST = new PointSet(px.seven + px.half, px.zero, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half);
	public static final PointSet PART_WALL_RAILING_SIMPLE_POST_BASE = new PointSet(px.six + px.half, px.zero, px.six + px.half, px.nine + px.half, px.one, px.nine + px.half);
	public static final PointSet PART_WALL_RAILING_SIMPLE_NORTH = new PointSet(px.seven + px.half, px.fifteen, px.zero, px.eight + px.half, px.sixteen, px.seven + px.half);
	
	public static final PointSet PART_WALL_RAILING_SIMPLE_POST_SHORT = new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half);
	
	// Double Rail
	public static final PointSet PART_WALL_RAILING_DOUBLE_NORTH = new PointSet(px.seven + px.half, px.nine, px.zero, px.eight + px.half, px.ten, px.seven + px.half);
	
	// Triple Rail
	public static final PointSet PART_WALL_RAILING_TRIPLE_NORTH = new PointSet(px.seven + px.half, px.three, px.zero, px.eight + px.half, px.four, px.seven + px.half);
	
	public static final PointSet PART_WALL_RAILING_SQUARE_POST = new PointSet(px.seven + px.half, px.zero, px.seven + px.half, px.nine - px.half, px.sixteen, px.nine - px.half);
	public static final PointSet PART_WALL_RAILING_SQUARE_TOP_NORTH = new PointSet(px.seven + px.half, px.fifteen, px.zero, px.nine - px.half, px.sixteen, px.seven + px.half);
	public static final PointSet PART_WALL_RAILING_SQUARE_BOT_NORTH = new PointSet(px.seven + px.half, px.zero, px.zero, px.nine - px.half, px.one, px.seven + px.half);
	public static final PointSet PART_WALL_RAILING_SQUARE_HALVED_MID_NORTH = new PointSet(px.seven + px.half, px.seven + px.half, px.zero, px.nine - px.half, px.nine - px.half, px.seven + px.half);
	public static final PointSet PART_WALL_RAILING_SQUARE_QUARTERED_POST_NORTH = new PointSet(px.seven + px.half, px.zero, px.zero, px.nine - px.half, px.sixteen, px.half);
	public static final PointSet PART_WALL_RAILING_SQUARE_QUARTERED_MID_NORTH = new PointSet(px.seven + px.half, px.seven + px.half, px.half, px.nine - px.half, px.nine - px.half, px.seven + px.half);
	public static final PointSet PART_WALL_DANGER_TAPE_POST = new PointSet(px.six + px.half, px.zero, px.six + px.half, px.ten - px.half, px.sixteen, px.ten - px.half);
	public static final PointSet PART_WALL_DANGER_TAPE_NORTH = new PointSet(px.eight, px.twelve, px.zero, px.eight, px.fifteen, px.eight);
	public static final PointSet PART_PANEL_POST_NORTH = new PointSet(px.seven + px.half, px.zero, px.zero, px.nine - px.half, px.sixteen, px.one);
	public static final PointSet PART_PANEL_BAR_TOP = new PointSet(px.seven + px.half, px.fifteen, px.one, px.nine - px.half, px.sixteen, px.fifteen);
	public static final PointSet PART_PANEL_BAR_BOT = new PointSet(px.seven + px.half, px.zero, px.one, px.nine - px.half, px.one, px.fifteen);
	public static final PointSet PART_PANEL_META_BOX = new PointSet(px.seven + px.half, px.zero, px.zero, px.nine - px.half, px.sixteen, px.sixteen);
	public static final PointSet PART_PANEL_SECONDARY_PART = new PointSet(px.eight, px.one, px.one, px.eight, px.fifteen, px.fifteen);
	public static final PointSet PART_PANEL_BAR_MID = new PointSet(px.seven + px.half, px.seven + px.half, px.one, px.nine - px.half, px.nine - px.half, px.fifteen);
	public static final PointSet PART_PANEL_POST_MID = new PointSet(px.seven + px.half, px.one, px.seven + px.half, px.nine - px.half, px.fifteen, px.nine - px.half);

	
	@Override
	public IIcon getIcon(int side, int meta) 
	{
		return DEFAULT_ICON;
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		return DEFAULT_ICON;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntitySidedConnector();
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	@Override
    public boolean isDiscrete( )
    {
    	return true;
    }
    
    @Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) 
	{
		return true;
	}
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
    {
    	TileEntitySidedConnector tile = (TileEntitySidedConnector)world.getTileEntity(x, y, z);
    	
	    if (tile == null) return false;
	    
	    ItemStack inhand = player.getCurrentEquippedItem();

	    boolean clickHandled = false;
	    if (inhand != null) 
	    {
	    	if (SideHelper.onServer())
	    	{
	    		if (ToolHelper.isWrench(inhand))
	    		{
	    			switch (side)
				    {
					    case 1:
					    {
						    if (hitz <= 0.3F)
						    {
							    tile.toggleConnection((short)2);
							    clickHandled = true;
						    }
						    
						    if (hitz >= 0.7F)
						    {
							    tile.toggleConnection((short)3);
							    clickHandled = true;
						    }
						    
						    if (hitx <= 0.3F)
						    {
							    tile.toggleConnection((short)4);
							    clickHandled = true;
						    }
						    
						    if (hitx >= 0.7F)
						    {
							    tile.toggleConnection((short)5);
							    clickHandled = true;
						    }
						    
						    break;
					    } 
					    
					    case 4: 
					    case 5: 
					    {
						    if (hitz <= 0.3F)
						    {
							    tile.toggleConnection((short)2);
							    clickHandled = true;
						    }
						    
						    if (hitz >= 0.7F)
						    {
							    tile.toggleConnection((short)3);
							    clickHandled = true;
						    }
						    break;
					    }
					    case 2: 
					    case 3: 
					    {
						    if (hitx <= 0.3F)
						    {
						    tile.toggleConnection((short)4);
						    clickHandled = true;
						    }
						    if (hitx >= 0.7F)
						    {
						    tile.toggleConnection((short)5);
						    clickHandled = true;
						    }
						    break;
					    }
			    	}
	    			
    				if (!clickHandled) 
    				{
					    tile.toggleConnection((short)side);
				    }
    				
				    world.markBlockForUpdate(x, y, z);
				    return true;
	    		}
	    	}
	    	else if (SideHelper.onClient())
	    	{
	    		if(inhand == null || inhand.getItem() == null) return false;
	    		
			    int damage = inhand.getItemDamage();
			    
			    if (((inhand.getItem() instanceof ItemBlock)) && (!BlockDecor.RANGE_WALL.contains(Integer.valueOf(damage))))
			    {
				    ItemBlock itemBlock = (ItemBlock)inhand.getItem();
				    Block handBlock = itemBlock.field_150939_a;
				    
				    if (handBlock != null)
				    {
					    IIcon ico = handBlock.getIcon(2, damage);
					    if (ico != null)
					    {
						    tile.setSecondaryTextureSource(handBlock, inhand.getItem().getDamage(inhand), 2);
						    
						    IMessage message = new DiscreteDecorativeTextureSyncPacket.DiscreteDecorativeTextureSyncMessage(tile.getSecondaryTextureSource(), x, y, z, inhand.getItem().getDamage(inhand), 2, player.dimension);
						    DiscreteDecorativePacketHandler.net.sendToServer(message);
						
						    world.markBlockForUpdate(x, y, z);
						    return true;
				    	}
				    }
			    }
	    	}
	    }
	    
	    return false;
    
    }
	
    @Override
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	AxisAlignedBB aabb = this.getWireframeBox(world, x, y, z);
    	
    	return new PointSet(aabb.minX - x, aabb.minY - y, aabb.minZ - z, aabb.maxX - x, aabb.maxY - y, aabb.maxZ - z);
    }
    
    @Override
	public AxisAlignedBB getWireframeBox(IBlockAccess world, int x, int y, int z) 
	{
    	TileEntitySidedConnector tile = (TileEntitySidedConnector)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
			HashMap<ForgeDirection, Boolean> adjMap = getAdjacencyMap(world, x, y, z);
			HashMap<ForgeDirection, Boolean> conMap = tile.getConnectionMap();
			SubBlockWall.mergeMaps(adjMap, conMap);
			
			if(adjMap.size() == 0 || conMap.size() == 0) return null;
			
			int type = tile.getSubtype();
					
	    	
			switch(type)
			{
				case BlockDecor.ID_WALL_DISCRETE:
				{
					PointSet result = new PointSet(PART_WALL_POST);
					
			    	if(getAdjacencyCount(adjMap) == 0)
			    	{
			    		return PART_WALL_POST.toAABB(x, y, z);
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.NORTH))
			    	{
			    		result.combine(PART_WALL_NORTH);
			    	}

			    	if(adjMap.get(ForgeDirection.SOUTH))
			    	{
			    		result.combine(PART_WALL_SOUTH);
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.EAST))
			    	{
			    		result.combine(PART_WALL_EAST);
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.WEST))
			    	{
			    		result.combine(PART_WALL_WEST);
			    	}
			    	
			    	return result.toAABB(x, y, z);
				}
				
				case BlockDecor.ID_WALL_RAILING_SIMPLE:
				case BlockDecor.ID_WALL_RAILING_DOUBLE:
				case BlockDecor.ID_WALL_RAILING_TRIPLE:
				case BlockDecor.ID_WALL_RAILING_SQUARE:
				case BlockDecor.ID_WALL_RAILING_SQUARE_HALVED:
				case BlockDecor.ID_WALL_RAILING_SQUARE_QUARTERED: 
				{

					PointSet result = new PointSet(PART_WALL_RAILING_SIMPLE_POST);
					
			    	if(getAdjacencyCount(adjMap) == 0)
			    	{
			    		return result.toAABB(x, y, z);
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.NORTH))
			    	{
			    		result.combine(PART_WALL_RAILING_SIMPLE_NORTH);
			    	}

			    	if(adjMap.get(ForgeDirection.SOUTH))
			    	{
			    		result.combine(PART_WALL_RAILING_SIMPLE_NORTH.translateTo(ForgeDirection.SOUTH));
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.EAST))
			    	{
			    		result.combine(PART_WALL_RAILING_SIMPLE_NORTH.translateTo(ForgeDirection.EAST));
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.WEST))
			    	{
			    		result.combine(PART_WALL_RAILING_SIMPLE_NORTH.translateTo(ForgeDirection.WEST));
			    	}
			    	
					if(!conMap.get(ForgeDirection.DOWN))
					{
						result.y1 = px.fifteen;
					}
					
			    	return result.toAABB(x, y, z);
				}

				case BlockDecor.ID_WALL_DANGER_TAPE:
				{
					PointSet result = new PointSet(PART_WALL_DANGER_TAPE_POST);
					
					if (getAdjacencyCount(adjMap) == 0) 
					{
						return result.toAABB(x, y, z);
					}
					
					if (((Boolean)adjMap.get(ForgeDirection.NORTH)).booleanValue()) 
					{
						result.combine(PART_WALL_DANGER_TAPE_NORTH);
					}
					
					if (((Boolean)adjMap.get(ForgeDirection.SOUTH)).booleanValue()) 
					{
						result.combine(PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.SOUTH));
					}
					
					if (((Boolean)adjMap.get(ForgeDirection.EAST)).booleanValue()) 
					{
						result.combine(PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.EAST));
					}
					
					if (((Boolean)adjMap.get(ForgeDirection.WEST)).booleanValue()) 
					{
						result.combine(PART_WALL_DANGER_TAPE_NORTH.translateTo(ForgeDirection.WEST));
					}
					
					return result.toAABB(x, y, z);
				}
				
				case BlockDecor.ID_WALL_PANEL_SQUARE: 
				case BlockDecor.ID_WALL_PANEL_SQUARE_HALVED: 
				case BlockDecor.ID_WALL_PANEL_SQUARE_QUARTERED: 
				{
					PointSet result = new PointSet(PART_PANEL_POST_NORTH);
					
					result.combine(PART_PANEL_POST_NORTH.translateTo(ForgeDirection.SOUTH));
					
					if ((tile.getForgeDirection() == ForgeDirection.EAST) || (tile.getForgeDirection() == ForgeDirection.WEST)) 
					{
						result = result.translateTo(ForgeDirection.EAST);
					}
					
					return result.toAABB(x, y, z);
				}
			}
    	}
    	return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
	}
    
    
	@Override
	public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	TileEntitySidedConnector tile = (TileEntitySidedConnector)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
			HashMap<ForgeDirection, Boolean> adjMap = getAdjacencyMap(world, x, y, z);
			HashMap<ForgeDirection, Boolean> conMap = tile.getConnectionMap();
			SubBlockWall.mergeMaps(adjMap, conMap);

			int type = tile.getSubtype();
			int adjCount = getAdjacencyCount(adjMap);
			AxisAlignedBB box = null;
			
			switch(type)
			{
				case BlockDecor.ID_WALL_DISCRETE:
				{
			    	if(adjCount == 0 || adjCount > 2)
			    	{
			    		box = new PointSet(PART_WALL_POST_TALL).toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.NORTH))
			    	{
			    		box = PART_WALL_NORTH_TALL.toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}

			    	if(adjMap.get(ForgeDirection.SOUTH))
			    	{
			    		box = PART_WALL_SOUTH_TALL.toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.EAST))
			    	{
			    		box = PART_WALL_EAST_TALL.toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.WEST))
			    	{
			    		box = PART_WALL_WEST_TALL.toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
				}
				
				case BlockDecor.ID_WALL_RAILING_SIMPLE:
				case BlockDecor.ID_WALL_RAILING_DOUBLE:
				case BlockDecor.ID_WALL_RAILING_TRIPLE:
				case BlockDecor.ID_WALL_RAILING_SQUARE:
				case BlockDecor.ID_WALL_RAILING_SQUARE_HALVED:
				case BlockDecor.ID_WALL_RAILING_SQUARE_QUARTERED:
				case BlockDecor.ID_WALL_DANGER_TAPE:
				{
		    		PointSet piece = PART_WALL_NORTH_TALL;
		    		boolean shortened = false;
		    		
		    		if(!conMap.get(ForgeDirection.DOWN))
		    		{
		    			piece = PART_WALL_NORTH_SHORT;
		    			shortened = true;
		    		}
		    		
		    		
			    	if(adjCount == 0 || adjCount > 2)
			    	{
			    		if(!shortened)
			    		{
			    			box = new PointSet(PART_WALL_RAILING_SIMPLE_POST).toAABB(x, y, z);
			    		}
			    		else
			    		{
			    			box = new PointSet(PART_WALL_RAILING_SIMPLE_POST_SHORT).toAABB(x, y, z);
			    		}

			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.NORTH))
			    	{
			    		box = piece.toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}

			    	if(adjMap.get(ForgeDirection.SOUTH))
			    	{
			    		box = piece.translateTo(ForgeDirection.SOUTH).toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.EAST))
			    	{
			    		box = piece.translateTo(ForgeDirection.EAST).toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
			    	if(adjMap.get(ForgeDirection.WEST))
			    	{
			    		box = piece.translateTo(ForgeDirection.WEST).toAABB(x, y, z);
			    		
			        	if(mask.intersectsWith(box) == true)
			        	{
			        		boxlist.add(box);
			        	}
			    	}
			    	
					break;
				}
				
				case 950: 
				case 951: 
				case 952: 
				{
					PointSet t = PART_PANEL_META_BOX;
					
					if ((tile.getForgeDirection() == ForgeDirection.EAST) || (tile.getForgeDirection() == ForgeDirection.WEST)) 
					{
						t = t.translateTo(ForgeDirection.EAST);
					}
					
					if (mask.intersectsWith(t.toAABB(x, y, z)) == true) 
					{
						boxlist.add(t.toAABB(x, y, z));
					}
					
					break;
				}
			}
    	}
	}  	
    
    
	public static HashMap<ForgeDirection, Boolean> getAdjacencyMap(IBlockAccess world, int x, int y, int z)
	{
		TileEntity north = world.getTileEntity(x, y, z - 1);
		TileEntity south = world.getTileEntity(x, y, z + 1);
		TileEntity east = world.getTileEntity(x + 1, y, z);
		TileEntity west = world.getTileEntity(x - 1, y, z);
		TileEntity bottom = world.getTileEntity(x, y - 1, z);
		TileEntity top = world.getTileEntity(x, y + 1, z);
		
		HashMap<ForgeDirection, Boolean> adj = new HashMap<ForgeDirection, Boolean>();
		
		if(isWall(north) || shouldWallConnect(world, x, y, z - 1))
		{
			adj.put(ForgeDirection.NORTH, true);
		}
		else
		{
			adj.put(ForgeDirection.NORTH, false);			
		}
		
		if(isWall(south) || shouldWallConnect(world, x, y, z + 1))
		{
			adj.put(ForgeDirection.SOUTH, true);
		}
		else
		{
			adj.put(ForgeDirection.SOUTH, false);			
		}
		
		if(isWall(east) || shouldWallConnect(world, x + 1, y, z))
		{
			adj.put(ForgeDirection.EAST, true);
		}
		else
		{
			adj.put(ForgeDirection.EAST, false);			
		}
		
		if(isWall(west) || shouldWallConnect(world, x - 1, y, z))
		{
			adj.put(ForgeDirection.WEST, true);
		}
		else
		{
			adj.put(ForgeDirection.WEST, false);			
		}
		
		if(isWall(bottom))
		{
			adj.put(ForgeDirection.DOWN, true);
		}
		else
		{
			adj.put(ForgeDirection.DOWN, false);
		}
		
		if(isWall(top))
		{
			adj.put(ForgeDirection.UP, true);	
		}
		else
		{
			adj.put(ForgeDirection.UP, false);
		}

		return adj;
	}
	
	public static boolean isWall(TileEntity tile)
	{
		if(tile instanceof TileEntitySidedConnector)
		{
			TileEntitySidedConnector te = (TileEntitySidedConnector)tile;
			
			if(BlockDecor.RANGE_WALL.contains(te.getSubtype()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean shouldWallConnect(IBlockAccess world, int x, int y, int z)
	{
		if(world == null) return false;
		
		Block connect = world.getBlock(x, y, z);
		
		if (((connect instanceof BlockAir)) || ((connect instanceof BlockDynamicLiquid)) || ((connect instanceof BlockStaticLiquid)) || ((connect instanceof BlockSnow)) || (connect == null) || (!(connect instanceof Block)) || (world.isAirBlock(x, y, z)) || (connect.isAir(world, x, y, z))) 
		{
			return false;
		}
		
		return true;
	}
	
	public static int getAdjacencyCount(HashMap<ForgeDirection, Boolean> map)
	{
		if(map == null) return 0;
			
		int count = 0;
		
		for(Entry<ForgeDirection, Boolean> entry : map.entrySet())
		{
			if(entry.getValue() && entry.getKey() != ForgeDirection.UP && entry.getKey() != ForgeDirection.DOWN)
			{
				count++;
			}
		}
		
		return count;
	}
	
	
	public static void mergeMaps(HashMap<ForgeDirection, Boolean> copyTo, HashMap<ForgeDirection, Boolean> copyFrom)
	{
		if(copyTo == null || copyFrom == null) return;
		
		for(Entry<ForgeDirection, Boolean> entry : copyTo.entrySet())
		{
			if(entry != null)
			{
				copyTo.put(entry.getKey(), entry.getValue() && copyFrom.get(entry.getKey()));
			}
		}
	}
	
	public static boolean isCorner(HashMap<ForgeDirection, Boolean> adjMap)
	{
		if(adjMap == null) return false;
		
		if(adjMap.get(ForgeDirection.NORTH) && adjMap.get(ForgeDirection.EAST))
		{
			return true;
		}
		
		if(adjMap.get(ForgeDirection.NORTH) && adjMap.get(ForgeDirection.WEST))
		{
			return true;
		}
		
		if(adjMap.get(ForgeDirection.SOUTH) && adjMap.get(ForgeDirection.EAST))
		{
			return true;
		}
		
		if(adjMap.get(ForgeDirection.SOUTH) && adjMap.get(ForgeDirection.WEST))
		{
			return true;
		}
		
		return false;
	}
}
