package kappafox.di.decorative.tileentities;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySwordRack extends TileEntityDiscreteBlock implements IInventory
{
	private ItemStack[] items = new ItemStack[6];
	
	public TileEntitySwordRack( )
	{
		this(6);

	}
	
	public TileEntitySwordRack(int itemCount_)
	{
		super();
		this.setFullColour(true);
		
		if(itemCount_ > 0)
		{
			items = new ItemStack[itemCount_];
		}
		else
		{
			items = new ItemStack[1];
		}
		
	}
	
	
	public void writeToNBT(NBTTagCompound nbt_)
	{		
		super.writeToNBT(nbt_);
        NBTTagList itemTag = new NBTTagList();

        for(int i = 0; i < items.length; i++)
        {
            if(items[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)i);
                items[i].writeToNBT(tag);
                itemTag.appendTag(tag);
            }
        }
		
        nbt_.setTag("Items", itemTag);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
        NBTTagList nbttaglist = nbt_.getTagList("Items", 10);
        
        items = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.items.length)
            {
                this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        

	}

	public ItemStack getItem(int slot_)
	{
		if(this.isValidSlot(slot_))
		{
			return items[slot_];
		}
		
		return null;
	}
	
	public void setItem(ItemStack istack_, int slot_)
	{
		if(this.isValidSlot(slot_))
		{
			if(istack_ != null)
			{
				items[slot_] = istack_;
			}	
		}

	}
	

	private boolean isValidSlot(int slot_)
	{
		if(slot_ < items.length && slot_ >= 0)
		{
			return true;
		}
		
		return false;
	}
	
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
    	NBTTagCompound tag = packet.func_148857_g();
    	readFromNBT(tag);
    	
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
    

	@Override
	public int getSizeInventory() 
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot_) 
	{
		return items[slot_];
	}

	@Override
    public ItemStack decrStackSize(int slot_, int amount_)
    {
		ItemStack item = getStackInSlot(slot_);
		
		if(item != null)
		{
			if(item.stackSize <= amount_)
			{
				setInventorySlotContents(slot_, null);
			}
			else
			{
				item = item.splitStack(amount_);
				
				if(item.stackSize == 0)
				{
					setInventorySlotContents(slot_, null);
				}
			}
		}
		
		return item;    	
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int slot_)
    {
        ItemStack stack = getStackInSlot(slot_);
        if (stack != null) 
        {
        	setInventorySlotContents(slot_, null);
        }
        return stack;
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack_)
	{
		items[i] = itemstack_;
		
		if(itemstack_ != null && itemstack_.stackSize > getInventoryStackLimit())
		{
			itemstack_.stackSize = getInventoryStackLimit();
		}	
	}

	@Override
	public String getInventoryName() 
	{
		return "Sword Rack";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player_) 
	{
		return true;
	}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}


	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
	
}
