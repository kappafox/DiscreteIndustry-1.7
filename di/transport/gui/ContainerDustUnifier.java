package kappafox.di.transport.gui;

import kappafox.di.base.util.DustSlot;
import kappafox.di.base.util.OutputSlot;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import kappafox.di.transport.tileentities.TileEntityDustUnifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDustUnifier extends Container
{
	private TileEntityDustUnifier tileEntity;
	public ContainerDustUnifier(InventoryPlayer invp, TileEntityDustUnifier tile)
	{
		if(invp == null || tile == null) return;
		
		tileEntity = tile;
		int xoffset = 8;
		int yoffset = 12;
		
		int rows = 8;
		int cols = 11;
		//Setting up the internal inventory
		
		
		// Input Slots
		//rows
		for(int i = 0; i < rows; i++)
		{
			//columns
			for(int j = 0; j < cols; j++)
			{
				//tile, index, xdisplay, ydisplay
				addSlotToContainer(new DustSlot(tile, j + (i * cols), xoffset + j * 18, yoffset + i * 18));
			}
		}
		
		// Output Slots
		
		xoffset = 224;
		int slotIndex = 88;
		
		for(int k = 0; k < 8; k++)
		{
			//tile, index, xdisplay, ydisplay
			addSlotToContainer(new OutputSlot(tile, slotIndex + k, xoffset, yoffset + k * 18));
		}
		
		this.bindPlayerInventory(invp);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player_) 
	{
		return true;
	}
	
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
    {
		int machineEnd = 96;
		int max = 96 + (4 * 9);	// slots in machine + inventory + hotbar
	
        ItemStack stack = null;

        Slot slotObject = (Slot)inventorySlots.get(slot);
        
        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if(slotObject != null && slotObject.getHasStack()) 
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
        	//if(!DustSlot.isItemValidForSlot(stackInSlot)) return stack;


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
        
        tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        //super.detectAndSendChanges();
        return stack;
    }
	
	protected void bindPlayerInventory(InventoryPlayer pinv_)
	{
		
		//offsets determine where the players inventory slots are on the texture
		int xoffset = 62;
		int yoffset = 160;	//8, 84 is for standard inventory frames
		
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
			addSlotToContainer(new Slot(pinv_, i, xoffset + i * 18, yoffset));
		}

	}

}
