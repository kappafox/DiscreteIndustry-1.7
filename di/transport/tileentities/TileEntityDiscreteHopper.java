package kappafox.di.transport.tileentities;

import net.minecraft.block.BlockHopper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.Facing;

public class TileEntityDiscreteHopper extends TileEntityHopper implements IInventory
{
	private int COOLDOWN = 20;
	private int TRANSFER_COUNT = 64;
	
	private int transferCooldown = -1;
	private boolean extractFromAbove;
	private short redstoneState = 0;
	private ItemStack[] items = new ItemStack[15];
	
	public TileEntityDiscreteHopper( )
	{
		
	}
	
	public TileEntityDiscreteHopper(int size_)
	{
		//System.out.println("Meta:" + this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		items = new ItemStack[size_];
	}
	
	
	public boolean getExtractFromAbove( )
	{
		//System.out.println(xCoord + ":" + yCoord + ":" + zCoord + "\t" + extractFromAbove);
		return extractFromAbove;
	}
	
	
	public void toggleExtractFromAbove( )
	{
		if(extractFromAbove)
		{
			extractFromAbove = false;
		}
		else
		{
			extractFromAbove = true;
		}
		//System.out.println("POST" + "\t" + xCoord + ":" + yCoord + ":" + zCoord + "\t" + extractFromAbove);
	}
	
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory( )
    {
    	return items.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int i_)
    {
    	return items[i_];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
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

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
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

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack_)
	{
		items[i] = itemstack_;
		
		if(itemstack_ != null && itemstack_.stackSize > getInventoryStackLimit())
		{
			itemstack_.stackSize = getInventoryStackLimit();
		}	
	}

    /**
     * Returns the name of the inventory.
     */
    public String getInvName( )
    {
    	return "Discrete Hopper";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized( )
    {
    	return false;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
    	return 64;
    }


    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
    	return true;
    }



    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int slot_, ItemStack item_)
    {
    	return true;
    }


    //we need to override this function so we can use the tile entity to find the target instead of the meta
    @Override
    public boolean func_145887_i()
    {
        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            if (!this.func_145888_j()/* && BlockHopper.getIsBlockNotPoweredFromMetadata(this.getBlockMetadata())*/)
            {
            	
            	if(this.redstoneState == 1)
            	{
            		this.func_145896_c(COOLDOWN);
            		return false;
            	}
            	
            	if(this.extractFromAbove == true)
            	{
            		//super.func_145884_b(this);
            	}

            	boolean flag = this.insertItemToInventory();
                if (flag)
                {
                    this.func_145896_c(COOLDOWN);
                    //this.onInventoryChanged();
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }
    

    private boolean insertItemToInventory()
    {
    	IInventory iinventory = this.getOutputInventory();
    	
    	//Does inventory exist?
        if (iinventory == null)
        {
            return false;
        }
        else
        {
        	//for each item slot we have
            for (int i = 0; i < this.getSizeInventory(); ++i)
            {
            	//something in the slot?
                if (this.getStackInSlot(i) != null)	
                {
                    ItemStack itemstack = this.getStackInSlot(i).copy();	//copy the item stack
                    //ItemStack itemstack1 = insertStack(iinventory, this.decrStackSize(i, 1), Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(this.getBlockMetadata())]);
                    ItemStack leftover = func_145889_a(iinventory, this.decrStackSize(i, TRANSFER_COUNT), Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(this.getBlockMetadata())]);

                    if (leftover == null || leftover.stackSize == 0)
                    {
                        //iinventory.onInventoryChanged();
                        return true;
                    }

                    this.setInventorySlotContents(i, leftover);
                }
            }

            return false;
        }
    }
    
    public IInventory getOutputInventory( )
    {
    	//always dump down for now
        int i = 0;
        //getInventoryAtLocation
        return func_145893_b(this.getWorldObj(), (double)(this.xCoord + Facing.offsetsXForSide[i]), (double)(this.yCoord + Facing.offsetsYForSide[i]), (double)(this.zCoord + Facing.offsetsZForSide[i]));
    }
    
  
    
    public void openChest(){}

    public void closeChest(){}
    
    
    @Override
    public void updateEntity()
    {
        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            --this.transferCooldown;

            if (!this.func_145888_j())
            {
                this.func_145896_c(0);
                this.func_145887_i();
            }
        }
    }
    
    @Override
    public void func_145896_c(int par1)
    {
        this.transferCooldown = par1;
    }
    
    @Override
    public boolean func_145888_j()
    {
        return this.transferCooldown > 0;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Inventory", tag.getId());
        items = new ItemStack[this.getSizeInventory()];

        this.transferCooldown = tag.getInteger("TransferCooldown");
        this.redstoneState = tag.getShort("redstoneState");
        this.extractFromAbove = tag.getBoolean("extractFromAbove");

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < getSizeInventory())
            {
                items[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < items.length; ++i)
        {
            if (items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                items[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Inventory", nbttaglist);
        tag.setBoolean("extractFromAbove", extractFromAbove);
        tag.setShort("redstoneState", redstoneState);
        tag.setInteger("TransferCooldown", this.transferCooldown);

    }
}
