package kappafox.di.transport.blocks.items;

import java.util.List;

import com.google.common.collect.Range;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDiscreteTransportBlock extends ItemBlock
{
	
	private static SubItem[] subs = new SubItem[3];
	
	public ItemDiscreteTransportBlock(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabAllSearch);

		subs[0] = new SubItemStorageRack();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack istack)
	{
		String name = "Unset";
		switch(istack.getItemDamage())
		{		
			case 0:
			{
				name = "DBLOCKNAME0";
				break;
			}
			
			case 1:
			{
				name = "DBLOCKNAME1";
				break;
			}
			case 2:
			{
				name = "DBLOCKNAME2";
				break;
			}
			case 3:
			{
				name = "DBLOCKNAME3";
				break;
			}
			case 4:
			{
				name = "DBLOCKNAME4";
				break;
			}
			case 5:
			{
				name = "DBLOCKNAME5";
				break;
			}
			case 6:
			{
				name = "DBLOCKNAME6";
				break;
			}
			case 7:
			{
				name = "DBLOCKNAME7";
				break;
			}
			case 8:
			{
				name = "DBLOCKNAME8";
				break;
			}
			case 9:
			{
				name = "DBLOCKNAME9";
				break;
			}
			case 10:
			{
				name = "DBLOCKNAME10";
				break;
			}
			case 11:
			{
				name = "DBLOCKNAME11";
				break;
			}
			case 12:
			{
				name = "DBLOCKNAME12";
				break;
			}
			case 13:
			{
				name = "DBLOCKNAME13";
				break;
			}
			case 14:
			{
				name = "DBLOCKNAME14";
				break;
			}
			case 15:
			{
				name = "DBLOCKNAME15";
				break;
			}
			
			case 100:
			{
				return "Storage Rack";
			}
			
			case 101:
			{
				return "Dual Storage Rack";
			}
			
			case 102:
			{
				return "Quad Storage Rack";
			}
			
			default:
			{
				return "how did you get this?";
			}
		}
		
		return getUnlocalizedName() + "." + name;
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
        //Block.blocksList[id].getSubBlocks(item, tab, itemList);
        

        //Ladders
        itemList.add(new ItemStack(item, 1, 100));
        itemList.add(new ItemStack(item, 1, 101));
        itemList.add(new ItemStack(item, 1, 102));
    }
	
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}
	
	
	@Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz)
    {
		int meta = item.getItemDamage();
        Block block = world.getBlock(x, y, z);

        if(meta > 15)
        {
        	return this.OnItemUseOverloaded(item, player, world, x, y, z, side, hitx, hity, hitz, meta);
        }
        
        return super.onItemUse(item, player, world, x, y, z, side, hitx, hity, hitz);
    }
	
	
	//Method just for overloaded damages 800+
	public boolean OnItemUseOverloaded(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int damage)
	{
		int meta = damage;
		Block block = world.getBlock(x, y, z);
       
        int direction;		
		int orient = 0;
		int ox = x;
		int oy = y;
		int oz = z;
		
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

        int j1 = this.getOverloadedMeta(damage);    
        int k1 = block.onBlockPlaced(world, x, y, z, side, hitx, hity, hitz, j1);

        if(placeBlockAt(item, player, world, x, y, z, side, hitx, hity, hitz, k1))
        {
        	world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
        	--item.stackSize;
        }

        
        
        TileEntity tile = null;
        int subindex = this.getSubIndex(meta);
        
        
        if(subindex != -1)
        {
        	tile = subs[this.getSubIndex(meta)].getTileEntity(meta, orient, -1, this.field_150939_a, meta, side, hitx, hity, hitz);
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

    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List tooltip, boolean par4)
    {
    	int meta = item.getItemDamage();
    	
    	switch(meta)
    	{
    		case 100:
    		{
    			tooltip.add("Accepts 1 Storage Unit");
    			tooltip.add("Obscurator Compatible");
    			break;
    		}
    		
    		case 101:
    		{
    			tooltip.add("Accepts 2 Storage Units");
    			tooltip.add("Obscurator Compatible");
    			break;
    		}
    		
    		case 102:
    		{
    			tooltip.add("Accepts 4 Storage Units");
    			tooltip.add("Obscurator Compatible");
    			break;
    		}
    	}
        //tooltip.add("WIP");

    }
    
    private int directionFromYaw(EntityPlayer player)
    {
    	return MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    }
    
    private int getSubIndex(int type)
    {
        if(BlockDiscreteTransport.RANGE_STORAGE_RACK.contains(type))
        {
        	return 0;
        }
        
        return -1;
    }
    
    private int getOverloadedMeta(int damage)
    {
        if(BlockDiscreteTransport.RANGE_STORAGE_RACK.contains(damage))
        {
        	return 0;
        }
        return -1;
    }
    
    private int getDirectionFromTop(int dir)
    {
    	return this.getDirectionFromBottom(dir);
    }
    
    private int getDirectionFromBottom(int dir)
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
}
