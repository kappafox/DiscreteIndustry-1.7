package kappafox.di.decorative.blocks.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Range;

import kappafox.di.base.blocks.ISubItemRangeProvider;
import kappafox.di.base.items.BaseItemDiscreteOverloaded;
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

public class ItemDiscreteDecorBlock extends BaseItemDiscreteOverloaded
{
	public ItemDiscreteDecorBlock(Block block)
	{
		super(block);
		
		super.registerSubItem(new SubItemSwordRack(), BlockDecor.META_SWORDRACK);
		super.registerSubItem(new SubItemDiscreteStairs(), BlockDecor.META_STAIRS);
		super.registerSubItem(new SubItemFixture(), BlockDecor.META_STRUT);
		super.registerSubItem(new SubItemShape(), BlockDecor.META_SHAPE);	
		super.registerSubItem(new SubItemWall(), BlockDecor.META_WALL);
		
		startDamage = 800;
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
		Block b = Block.getBlockFromItem(item);
		
		if(b != null)
		{
			b.getSubBlocks(item, tab, itemList);
		}

        //Ladders
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_FOOTHOLD));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_POLE));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_SIMPLE));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_ROPE));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_FIXED));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_CLASSIC));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_LADDER_INDUSTRIAL));
     
        //Racks/Rests
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_RACK_SWORDREST));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_RACK_SWORDRACK));
        
        //Discrete Stairs
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_STAIRS_NORMAL));
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_STAIRS_SMALL));
        
        //StrutS
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_STRUT_2X2));	//2x2
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_STRUT_4X4));	//4x4
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_STRUT_6X6));	//6x6
        
        //Shapes
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_SHAPE_SLAB));	//Slab
        
        //Fences
        itemList.add(new ItemStack(item, 1, BlockDecor.ID_WALL_DISCRETE));	//Standard Discrete Wall
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
}
