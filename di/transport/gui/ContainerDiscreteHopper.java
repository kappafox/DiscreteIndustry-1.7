package kappafox.di.transport.gui;


import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDiscreteHopper extends Container
{
	
	
	public ContainerDiscreteHopper(InventoryPlayer pinv_, TileEntityDiscreteHopper tile_)
	{
		//Setting up the internal inventory
		int xoffset = 44;
		int yoffset = 16;
		int rows = getRows(tile_.getBlockMetadata());
		int cols = 5;
		
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
		
		//setup the player inventory part
		bindPlayerInventory(pinv_);
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
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

	
	//slots 0 - 14 are in the machine
	//15 - 41 are the player inventory
	//42 - 50 are the player's hot bar


	/** 
	 * @param itemStack ItemStack to merge into inventory
	 * @param start minimum slot to attempt fill
	 * @param end maximum slot to attempt fill
	 * @param backwards go backwards
	 * @return true if stacks merged successfully
	 */
	//public boolean mergeItemStack(itemStack, start, end, backwards)
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
    {
    		
		int machineEnd = 0;
		int max = 0;
		
		switch(this.inventorySlots.size())
		{
			case 51:
				machineEnd = 15;
				max = 51;
				break;
			
			case 46:
				machineEnd = 10;
				max = 46;
				break;
			
			case 41:
				machineEnd = 5;
				max = 41;
				break;
		}
		
	
        ItemStack stack = null;

        Slot slotObject = (Slot)inventorySlots.get(slot);
        
        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) 
        {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //is this slot in the machine?
                if (slot < machineEnd) 
                {
                	if (this.mergeItemStack(stackInSlot, machineEnd, max, false) == false) 
                	{
                		return null;
                	}
                }    
                else //slot is either inventory or hotbar
                {
                	
                	if (this.mergeItemStack(stackInSlot, 0, machineEnd, false) == false)
                	{
                		return null;
                	}
                        
                }

                if (stackInSlot.stackSize == 0) 
                {
                        slotObject.putStack(null);
                } 
                else 
                {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) 
                {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }
    
    private int getRows(int meta_)
    {
    	switch(meta_)
    	{
    		case 0:
    			return 3;
    		
    		case 1:
    			return 2;
    		
    		case 2:
    			return 1;
    			
    		default:
    			return 0;
    	}
    }

}
