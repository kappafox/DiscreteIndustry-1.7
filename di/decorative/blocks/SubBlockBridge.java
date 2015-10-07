package kappafox.di.decorative.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.compat.ToolHelper;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.SideHelper;
import kappafox.di.decorative.DiscreteDecorativePacketHandler;
import kappafox.di.decorative.network.DiscreteDecorativeTextureSyncPacket;
import kappafox.di.decorative.tileentities.TileEntityBridgeBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SubBlockBridge extends SubBlock 
{

	public static final PointSet RAIL_COLLISION_BOX_NORTH = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.sixteen, px.zero);
	
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
	public AxisAlignedBB getWireframeBox(IBlockAccess world, int x, int y, int z) 
	{
		return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
	}
	
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	return new PointSet(0,0,0,1,1,1);
    }
	
	@Override
	public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	TileEntityBridgeBlock tile = (TileEntityBridgeBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
			HashMap<ForgeDirection, Boolean> adjMap = getAdjacencyMap(world, x, y, z);
			HashMap<ForgeDirection, Boolean> conMap = tile.getConnectionMap();
			SubBlockBridge.mergeMaps(adjMap, conMap);

			int type = tile.getSubtype();
			//int adjCount = getAdjacencyCount(adjMap);
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox((double)x + px.zero, (double)y + px.zero, (double)z + px.zero, (double)x + px.sixteen, (double)y + px.zero, (double)z + px.sixteen);
			
			// Floor
        	if(mask.intersectsWith(box) == true)
        	{
        		boxlist.add(box);
        	}
			
	    	if(!adjMap.get(ForgeDirection.NORTH))
	    	{
	    		box = RAIL_COLLISION_BOX_NORTH.toAABB(x, y, z);
	    		
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
	    	}

	    	if(!adjMap.get(ForgeDirection.SOUTH))
	    	{
	    		box = RAIL_COLLISION_BOX_NORTH.translateTo(ForgeDirection.SOUTH).toAABB(x, y, z);
	    		
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
	    	}
	    	
	    	if(!adjMap.get(ForgeDirection.EAST))
	    	{
	    		box = RAIL_COLLISION_BOX_NORTH.translateTo(ForgeDirection.EAST).toAABB(x, y, z);
	    		
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
	    	}
	    	
	    	if(!adjMap.get(ForgeDirection.WEST))
	    	{
	    		box = RAIL_COLLISION_BOX_NORTH.translateTo(ForgeDirection.WEST).toAABB(x, y, z);
	    		
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
	    	}
    	}
	}  	
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityBridgeBlock();
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
		

		adj.put(ForgeDirection.NORTH, isBridge(north));
		adj.put(ForgeDirection.SOUTH, isBridge(south));
		adj.put(ForgeDirection.EAST, isBridge(east));
		adj.put(ForgeDirection.WEST, isBridge(west));		
		adj.put(ForgeDirection.DOWN, false);
		adj.put(ForgeDirection.UP, false);

		return adj;
	}
	
	public static boolean isBridge(TileEntity tile)
	{
		if(tile instanceof TileEntityBridgeBlock)
		{
			return true;
		}
		
		return false;
	}
	
	public static void mergeMaps(HashMap<ForgeDirection, Boolean> copyTo, HashMap<ForgeDirection, Boolean> copyFrom)
	{
		if(copyTo == null || copyFrom == null) return;
		
		for(Entry<ForgeDirection, Boolean> entry : copyTo.entrySet())
		{
			if(entry != null)
			{
				copyTo.put(entry.getKey(), !(!entry.getValue() && copyFrom.get(entry.getKey())));
			}
		}
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
    {
    	TileEntityBridgeBlock tile = (TileEntityBridgeBlock)world.getTileEntity(x, y, z);
    	
	    if (tile == null) return false;
	    
	    ItemStack inhand = player.getCurrentEquippedItem();

	    boolean clickHandled = false;
	    if (inhand != null) 
	    {
	    	if (SideHelper.onServer())
	    	{
	    		if (ToolHelper.isWrench(inhand))
	    		{
	    			if(side != 0 && side != 1)
	    			{
					    tile.toggleConnection((short)side);
	    			}
	    			
				    world.markBlockForUpdate(x, y, z);
				    return true;
	    		}
	    	}
	    	else if (SideHelper.onClient())
	    	{
//	    		if(inhand == null || inhand.getItem() == null) return false;
//	    		
//			    int damage = inhand.getItemDamage();
//			    
//			    if (((inhand.getItem() instanceof ItemBlock)) && (!BlockDecor.RANGE_WALL.contains(Integer.valueOf(damage))))
//			    {
//				    ItemBlock itemBlock = (ItemBlock)inhand.getItem();
//				    Block handBlock = itemBlock.field_150939_a;
//				    
//				    if (handBlock != null)
//				    {
//					    IIcon ico = handBlock.getIcon(2, damage);
//					    if (ico != null)
//					    {
//						    tile.setSecondaryTextureSource(handBlock, inhand.getItem().getDamage(inhand), 2);
//						    
//						    IMessage message = new DiscreteDecorativeTextureSyncPacket.DiscreteDecorativeTextureSyncMessage(tile.getSecondaryTextureSource(), x, y, z, inhand.getItem().getDamage(inhand), 2, player.dimension);
//						    DiscreteDecorativePacketHandler.net.sendToServer(message);
//						
//						    world.markBlockForUpdate(x, y, z);
//						    return true;
//				    	}
//				    }
//			    }
	    	}
	    }
	    
	    return false;
    
    }

}
