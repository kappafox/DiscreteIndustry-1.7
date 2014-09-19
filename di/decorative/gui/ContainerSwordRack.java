package kappafox.di.decorative.gui;

import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSwordRack extends Container
{
	
	private TileEntitySwordRack tile;
	public ContainerSwordRack(InventoryPlayer invp_, TileEntitySwordRack tile_)
	{
		
		tile = tile_;
		int size = tile_.getSubtype();
		int xoffset = 36;
		int yoffset = 16;
		int rows = 1;
		int cols = 6;
		
		switch(size)
		{
			case 821:
				cols = 1;
				xoffset += 44;
				break;
				
		}
		//Setting up the internal inventory
		
		
		//rows
		for(int i = 0; i < rows; i++)
		{
			//columns
			for(int j = 0; j < cols; j++)
			{
				//tile, index, xdisplay, ydisplay
				addSlotToContainer(new Slot(tile_, j + (i * cols), xoffset + j * 18, yoffset + i * 18));
			}
		}
		
		
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
		int machineEnd = 6;
		int max = 42;
		
		switch(this.inventorySlots.size())
		{
			case 37:
			{
				machineEnd = 1;
				max = 37;
			}
		}

	
        ItemStack stack = null;

        Slot slotObject = (Slot)inventorySlots.get(slot);
        
        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if(slotObject != null && slotObject.getHasStack()) 
        {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //is this slot in the machine?
                if(slot < machineEnd) 
                {
                	if(this.mergeItemStack(stackInSlot, machineEnd, max, false) == false) 
                	{
                		return null;
                	}
                }    
                else //slot is either inventory or hotbar
                {            	
                	if(this.mergeItemStack(stackInSlot, 0, machineEnd, false) == false)
                	{
                		return null;
                	}
                        
                }

                if(stackInSlot.stackSize == 0) 
                {
                	slotObject.putStack(null);
                } 
                else 
                {
                	slotObject.onSlotChanged();
                }

                if(stackInSlot.stackSize == stack.stackSize) 
                {
                	return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        //super.detectAndSendChanges();
        return stack;
    }
	
	protected void bindPlayerInventory(InventoryPlayer pinv_)
	{
		
		//offsets determine where the players inventory slots are on the texture
		int xoffset = 8;
		int yoffset = 84;	//8, 84 is for standard inventory frames
		
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
