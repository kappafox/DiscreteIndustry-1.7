package kappafox.di.base.items;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Range;

import kappafox.di.base.blocks.ISubItemRangeProvider;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BaseItemDiscreteOverloaded extends ItemBlock
{
	protected final HashMap<Integer, SubItem> subs;
	protected final HashMap<Integer, String> names;
	protected final HashMap<Integer, Range> ranges;
	protected int startDamage;
	
	public BaseItemDiscreteOverloaded(Block block)
	{
		super(block);
				
		subs = new HashMap<Integer, SubItem>();
		names = new HashMap<Integer, String>();
		ranges = new HashMap<Integer, Range>();
		
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.registerItemRanges();
	}
	
	protected final boolean registerSubItem(SubItem item, int key)
	{
		if(!subs.containsKey(item))
		{
			subs.put(key, item);
			return true;
		}
		
		return false;
	}
	
	protected final boolean registerSubItemName(int damage, String name)
	{
		if(!names.containsKey(damage))
		{
			names.put(damage, name);
			return true;
		}
		
		return false;
	}
	
	protected final boolean hasSubItem(int key)
	{
		if(subs.containsKey(key))
		{
			return true;
		}
		
		return false;
	}
	
	protected final SubItem getSubItem(int key)
	{
		if(subs.containsKey(key))
		{
			return subs.get(key);
		}
		
		return null;
	}
	
	protected final void registerRange(int meta, Range range)
	{
		if(!ranges.containsKey(meta))
		{
			ranges.put(meta, range);
		}
	}
	
	@Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz)
    {
		int meta = item.getItemDamage();
		
        if(meta >= startDamage)
        {
        	return this.OnItemUseOverloaded(item, player, world, x, y, z, side, hitx, hity, hitz, meta);
        }
        
		return super.onItemUse(item, player, world, x, y, z, side, hitx, hity, hitz);
    }
	
	@Override
	public String getUnlocalizedName(ItemStack istack)
	{
		if(!names.containsKey(istack.getItemDamage()))
		{
			return "BASE_DISCRETE_ITEMBLOCK_DEFAULT_NAME:" + istack.getItemDamage();
		}
		
		return names.get(istack.getItemDamage());
	}
	
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}
	
    protected int getDirectionFromTop(int dir)
    {
    	return this.getDirectionFromBottom(dir);
    }
    
    protected int getDirectionFromBottom(int dir)
    {
        switch(dir)
        {
        	case 0:
        		return 3;
        		
        	case 1:
        		return 4;
        		
        	case 2:
        		return 2;
        		
        	case 3:
        		return 5;
        		
        	default:
        		return 0;
        }
    }
    
	protected boolean OnItemUseOverloaded(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int damage)
	{
		int meta = damage;

        int direction = 0;		
		int orient = 0;
		Block block = world.getBlock(x, y, z);
		
		this.standardItemChecks(item, player, world, x, y, z, side, hitx, hity, hitz, damage);
		int[] adjusted = this.adjustedPositioning(item, player, world, x, y, z, side, hitx, hity, hitz, damage);
		
		x = adjusted[0];
		y = adjusted[1];
		z = adjusted[2];
		orient = adjusted[3];
		direction = adjusted[4];
		
        int j1 = this.getOverloadedMeta(damage);    
        int k1 = block.onBlockPlaced(world, x, y, z, side, hitx, hity, hitz, j1);

        if(placeBlockAt(item, player, world, x, y, z, side, hitx, hity, hitz, k1))
        {
        	world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
        	--item.stackSize;
        }

        TileEntity tile = null;
       
        if(this.hasSubItem(j1))
        {
        	tile = this.getSubItem(j1).getTileEntity(meta, orient, -1, this.field_150939_a, meta, side, hitx, hity, hitz);
        }
        else
        {
        	//Default Block init stuff for non specialised subblocks
	        TileEntityDiscreteBlock t = new TileEntityDiscreteBlock(this.field_150939_a, j1, damage);
	        t.setAllTexturesFromSource(this.field_150939_a, meta);
	        
	        t.setVariable(orient);
	        t.setDirection((short)orient);
	        t.setTextureOrientation(orient);
        	t.setFullColour(true);
        	t.setOriginalBlock(this.field_150939_a);
        	t.setSubtype(meta);
	        
	        tile = t;        	
        }

        if(world.isRemote == false && tile != null)
        {
            world.setTileEntity(x, y, z, tile);
            world.markBlockForUpdate(x, y, z);
        }
     
        return true;
	}
    
	protected boolean standardItemChecks(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int damage)
	{
		Block block = world.getBlock(x, y, z);
		
        if(item.stackSize == 0)
        {
            return false;
        }
        else if(!player.canPlayerEdit(x, y, z, side, item))
        {
            return false;
        }
        else if(y == 255 && block.getMaterial().isSolid())
        {
            return false;
        }
        
        return true;
	}
	
	protected int[] adjustedPositioning(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int damage)
	{
        int direction = 0;		
		int orient = 0;
		Block block = world.getBlock(x, y, z);

        if(block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if(block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && (!block.isReplaceable(world, x, y, z)))
        {
            if(side == 0)
            {
                y--;            
                direction = this.directionFromYaw(player);
                orient = this.getDirectionFromBottom(direction);
            }

            if (side == 1)
            {
                y++;               
                direction = this.directionFromYaw(player);
                orient = this.getDirectionFromTop(direction);
            }
            
            switch(side)
            {
	            case 2:
	            {
	            	z--;
	            	orient = 3;
	            	break;
	            }
	            
	            case 3:
	            {
	            	z++;
	            	orient = 2;
	            	break;
	            }
	            
	            case 4:
	            {
	            	x--;
	            	orient = 5;
	            	break;
	            }
	            
	            case 5:
	            {
	            	x++;
	            	orient = 4;
	            	break;
	            }
            }
        }
        
        int[] result = new int[5];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        result[3] = orient;
        result[4] = direction;
        
        return result;
	}
	
	
    protected int directionFromYaw(EntityPlayer player)
    {
    	return MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    }
    
  
    /**
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
    
	@Override
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack item)
    {  	
        Block block = world.getBlock(x, y, z);
        int meta = item.getItemDamage();
        
        if(meta == 0)
        {
        	return super.func_150936_a(world, x, y, z, side, player, item);
        }
        
        int ox = x;
        int oy = y;
        int oz = z;
        
        if (block == Blocks.snow_layer)
        {
            side = 1;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush /*&& (this.field_150939_a.isReplaceable(world, x, y, z))*/)
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }
        

        return world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, side, player, item);
    }
	
	protected int getOverloadedMeta(int damage)
	{
		for(Entry<Integer, Range> entry : ranges.entrySet())
		{
			if(entry.getValue().contains(damage))
			{
				return entry.getKey();
			}
		}
		
		return 0;
	}
	
    protected void registerItemRanges( )
    {
    	ISubItemRangeProvider block = (ISubItemRangeProvider)field_150939_a;
    	HashMap<Short, Range> ranges = block.getRangeSet();
    	
    	for(Entry<Short, Range> entry : ranges.entrySet())
    	{
    		this.registerRange(entry.getKey(), entry.getValue());
    	} 	
    }
}
