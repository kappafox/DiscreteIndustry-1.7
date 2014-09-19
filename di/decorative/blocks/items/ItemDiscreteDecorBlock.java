package kappafox.di.decorative.blocks.items;

import java.util.List;

import com.google.common.collect.Range;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.blocks.BlockDecor;
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






public class ItemDiscreteDecorBlock extends ItemBlock
{
	
	private static SubItem[] subs = new SubItem[4];
	
	public ItemDiscreteDecorBlock(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		
		subs[0] = new SubItemSwordRack();
		subs[1] = new SubItemDiscreteStairs();
		subs[2] = new SubItemFixture();
		subs[3] = new SubItemShape();
	}
	
	public String getUnlocalizedName(ItemStack istack)
	{
		String name = "Unset";
		
		switch(istack.getItemDamage())
		{
			case 0:
			{
				name = "Overhead Fixture";
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
			
			case 800:
			{
				return "Foothold Ladder";
			}
			
			case 801:
			{
				return "Pole Ladder";
			}
			
			case 802:
			{
				return "Simple Ladder";
			}
			
			case 803:
			{
				return "Rope Ladder";
			}
			
			case 804:
			{
				return "XM1 Ladder";
			}
			
			case 805:
			{
				return "XM2 Ladder";
			}
			
			case 806:
			{
				return "Classic Ladder";
			}
			
			case 807:
			{
				return "Industrial Ladder";
			}
			
			case 821:
			{
				return "Sword Rest";
			}
			
			case 822:
			{
				return "Sword Rack";
			}
			
			case 861:
			{
				return "Discrete Stairs";
			}
			
			case 862:
			{
				return "Discrete Small Stairs";
			}
			
			case 871:
			{
				return "2x2 Strut";
			}
			
			case 872:
			{
				return "4x4 Strut";
			}
			
			case 873:
			{
				return "6x6 Strut";
			}
			
			case 881:
			{
				return "Discrete Slab";
			}
			
			case 888:
			{
				return "Test Item";
			}
			
			default:
			{
				name = "how did you get this?";
				break;
			}
		}
		
		return getUnlocalizedName() + "." + name;
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
        //Block.blocksList[this.getBlockID()].getSubBlocks(item, tab, itemList);
        
		Block b = Block.getBlockFromItem(item);
		
		if(b != null)
		{
			b.getSubBlocks(item, tab, itemList);
		}

        //Ladders
        itemList.add(new ItemStack(item, 1, 800));
        itemList.add(new ItemStack(item, 1, 801));
        itemList.add(new ItemStack(item, 1, 802));
        itemList.add(new ItemStack(item, 1, 803));
        itemList.add(new ItemStack(item, 1, 804));
        itemList.add(new ItemStack(item, 1, 805));
        itemList.add(new ItemStack(item, 1, 806));
        itemList.add(new ItemStack(item, 1, 807));
        
        //Racks/Rests
        itemList.add(new ItemStack(item, 1, 821));
        itemList.add(new ItemStack(item, 1, 822));
        
        //Discrete Stairs
        itemList.add(new ItemStack(item, 1, 861));
        itemList.add(new ItemStack(item, 1, 862));
        
        //StrutS
        itemList.add(new ItemStack(item, 1, 871));	//2x2
        itemList.add(new ItemStack(item, 1, 872));	//4x4
        itemList.add(new ItemStack(item, 1, 873));	//6x6
        
        //Shapes
        itemList.add(new ItemStack(item, 1, 881));	//Slab
        
        //itemList.add(new ItemStack(id, 1, 888));
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
        //int id = world.getBlockId(x, y, z);
       
        int direction;
        
        if(meta >= 800)
        {
        	return this.OnItemUseOverloaded(item, player, world, x, y, z, side, hitx, hity, hitz, meta);
        }
        
		return super.onItemUse(item, player, world, x, y, z, side, hitx, hity, hitz);
    }
	
	
	//Method just for overloaded damages 800+
	public boolean OnItemUseOverloaded(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int damage)
	{
		int meta = damage;
        //int block = world.getBlockId(x, y, z);
		
		Block block = world.getBlock(x, y, z);
       
        int direction;		
		int orient = 0;

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
            //world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
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
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List tooltip, boolean par4)
    {
    	int meta = item.getItemDamage();
    	
    	if(meta == 0)
    	{
        	tooltip.add("Craft me to update to the new version!");
    	}
    	else
    	{
        	tooltip.add("Obscurator Compatible");
    		//struts
    		if(meta >= 871 && meta <= 880)
    		{
            	tooltip.add("Wrench to toggle connections");
    		}

    	}

    }
    
    private int directionFromYaw(EntityPlayer player)
    {
    	return MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    }
    
    private int getSubIndex(int type)
    {
        if(type >= 821 && type <= 840)
        {
        	return 0;
        }
        
        if(type >= 861 && type <= 870)
        {
        	return 1;
        }
        
    	//Stairs
    	if(type >= 871 && type <= 880)
    	{
    		return 2;
    	}
    	
    	if(type >= BlockDecor.RANGE_SHAPE.lowerEndpoint() && type <= BlockDecor.RANGE_SHAPE.upperEndpoint())
    	{
    		return 3;
    	}
    	
        
        return -1;
    }
    
    private int getOverloadedMeta(int damage)
    {
    	//ladder
    	if(damage >= 800 && damage <= 820)
    	{
    		return 2;
    	}
    	
    	//Sword Racks
    	if(damage >= 821 && damage <= 840)
    	{
    		return 4;
    	}
    	
    	//Stairs
    	if(damage >= 861 && damage <= 870)
    	{
    		return 5;
    	}
    	
    	//Stairs
    	if(damage >= 871 && damage <= 880)
    	{
    		return 6;
    	}
    	
    	if(damage >= 881 && damage <= 882)
    	{
    		return 7;
    	}
    	
    	
    	
    	return 0;
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
