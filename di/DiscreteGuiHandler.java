package kappafox.di;

import kappafox.di.decorative.gui.ContainerLoom;
import kappafox.di.decorative.gui.ContainerSwordRack;
import kappafox.di.decorative.gui.GuiLoom;
import kappafox.di.decorative.gui.GuiSwordRack;
import kappafox.di.decorative.gui.GuiSwordRest;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import kappafox.di.transport.gui.ContainerDiscreteHopper;
import kappafox.di.transport.gui.ContainerDustUnifier;
import kappafox.di.transport.gui.GuiDustUnifier;
import kappafox.di.transport.gui.GuiFullDiscreteHopper;
import kappafox.di.transport.gui.GuiHalfDiscreteHopper;
import kappafox.di.transport.gui.GuiQuarterDiscreteHopper;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import kappafox.di.transport.tileentities.TileEntityDustUnifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class DiscreteGuiHandler implements IGuiHandler
{
    //returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
    {
    	if(id == DiscreteIndustry.GID_DISCRETE_HOPPER)
    	{
	        TileEntityDiscreteHopper tile = (TileEntityDiscreteHopper)world.getTileEntity(x, y, z);
	        
	        if(tile != null)
	        {
	        	return new ContainerDiscreteHopper(player.inventory, tile);              
	        }
    	}
    	
    	//Loom
    	if(id == 1)
    	{
	    	TileEntityLoomBlock tile = (TileEntityLoomBlock)world.getTileEntity(x, y, z);
    		return new ContainerLoom(player.inventory, tile);
    	}
    	
    	//Sword Rack
    	if(id == DiscreteIndustry.GID_SWORD_RACK)
    	{
	    	TileEntitySwordRack tile = (TileEntitySwordRack)world.getTileEntity(x, y, z);
    		return new ContainerSwordRack(player.inventory, tile);
    	}
    	
    	//Sword Rack
    	if(id == DiscreteIndustry.GID_DUST_UNIFIER)
    	{
	    	TileEntityDustUnifier tile = (TileEntityDustUnifier)world.getTileEntity(x, y, z);
    		return new ContainerDustUnifier(player.inventory, tile);
    	}
    	
        return null;
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id_, EntityPlayer player_, World world_, int x_, int y_, int z_) 
    {
    	
    	if(id_ == DiscreteIndustry.GID_DISCRETE_HOPPER)
    	{
	    	TileEntityDiscreteHopper tile = (TileEntityDiscreteHopper)world_.getTileEntity(x_, y_, z_);
			int meta = tile.getBlockMetadata();
			
	        if(tile != null)
	        {
	        		if(tile instanceof TileEntityDiscreteHopper)
	        		{
	        			switch(meta)
	        			{
		        			case 0:
		        				return new GuiFullDiscreteHopper(player_.inventory, tile);
		        				
		        			case 1:
		        				return new GuiHalfDiscreteHopper(player_.inventory, tile);
		        				
		        			case 2:
		        				return new GuiQuarterDiscreteHopper(player_.inventory, tile);
		        				
		        			default:
		        				return null;
	        			}
	        		}
	                
	        }
    	}
        
        //Loom
        if(id_ == 1)
        {
        	if(world_.getTileEntity(x_, y_, z_) instanceof TileEntityLoomBlock)
        	{
		    	TileEntityLoomBlock tile = (TileEntityLoomBlock)world_.getTileEntity(x_, y_, z_);
	        	return new GuiLoom(player_.inventory, tile);
        	}
        }	
        
        //Sword Rack
        if(id_ == DiscreteIndustry.GID_SWORD_RACK)
        {
        	TileEntitySwordRack tile = (TileEntitySwordRack)world_.getTileEntity(x_, y_, z_);
        	
        	if(tile != null)
        	{
        		int type = tile.getSubtype();
        		
        		switch(type)
        		{
        			case 821:
        	        	return new GuiSwordRest(player_.inventory, tile);
        				
        			case 822:
        	        	return new GuiSwordRack(player_.inventory, tile);
        		}
        		
        	}

        }
        
        // Dust Unifier
        if(id_ == DiscreteIndustry.GID_DUST_UNIFIER)
        {
        	TileEntityDustUnifier tile = (TileEntityDustUnifier)world_.getTileEntity(x_, y_, z_);
        	
        	if(tile != null)
        	{
	        	return new GuiDustUnifier(player_.inventory, tile);
        	}

        }	
        
        return null;

    }
}
