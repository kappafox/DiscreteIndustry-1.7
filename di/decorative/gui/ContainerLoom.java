package kappafox.di.decorative.gui;

import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLoom extends Container
{
	
	public ContainerLoom(InventoryPlayer invp_, TileEntityLoomBlock tile_)
	{
		super();
		
		this.bindPlayerInventory(invp_);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player_) 
	{
		return true;
	}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
    {
    	return null;
    }
	
	protected void bindPlayerInventory(InventoryPlayer pinv_)
	{
		
		//offsets determine where the players inventory slots are on the texture
		int xoffset = 8;
		int yoffset = 145;	//8, 84 is for standard inventory frames
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(pinv_, j + i * 9 + 9, xoffset + j * 18, yoffset + i * 18));
			}
		}
		
		//inventory bar
		yoffset = yoffset + (18 * 3) + 4;
		
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(pinv_, i, 8 + i * 18, yoffset));
		}

	}

}
