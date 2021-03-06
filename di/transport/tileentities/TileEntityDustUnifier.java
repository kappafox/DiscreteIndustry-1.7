package kappafox.di.transport.tileentities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLCommonHandler;
import kappafox.di.transport.gui.ContainerDustUnifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityDustUnifier extends TileEntity  implements ISidedInventory
{
	private ItemStack[] items = new ItemStack[96];
	private static int[] accessibleSlots = new int[96];
	private final InventoryCrafting crafter = new InventoryCrafting(new ContainerDustUnifier(null, null), 3, 3);
	
	public TileEntityDustUnifier( )
	{
		if(accessibleSlots[1] != 1)
		{
			for(int i = 0; i < 96; i++)
			{
				accessibleSlots[i] = i;
			}
		}
	}
	
    public boolean canUpdate()
    {
        return true;
    }
    
    private int cooldown = 40;
    
    public void updateEntity()
    {
    	if(cooldown < 0)
    	{
    		cooldown = 40;
    		
    		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
    		{
        		boolean result = unify();
        		
        		if(!result) cooldown = 100;
    		}
    	}
    	else
    	{
    		cooldown--;
    	}
    }
    
    private void Sort()
    {
		for(int i = 0; i < 88; i++)
		{
			ItemStack item = items[i];
			
			if(item == null || item.stackSize == 0) continue;
			
			if(item.stackSize == item.getMaxStackSize()) continue;
			
			// Find any other stacks of this item past this point
			
			for(int j = i + 1; j < 88; j++)
			{
				ItemStack istack2 = items[j];
				
				if(istack2 == null) continue;
				
				if(istack2.isItemEqual(item))
				{
					int space = item.getMaxStackSize() - item.stackSize;
					
					// Available space greater than found stacksize
					if(space >= istack2.stackSize)
					{
						item.stackSize += istack2.stackSize;
						istack2.stackSize = 0;
						items[j] = null;
					}
					else
					{
						// Available space less than found stack size					
						item.stackSize = item.getMaxStackSize();
						istack2.stackSize -= space;
						break;
					}
				}
			}
		}		    	
    }
	
	private boolean unify()
	{
		boolean didChange = false;
		
		if(items == null) return false;

		for(int i = 0; i < 88; i++)
		{
			ItemStack item = items[i];
			
			if(item == null || item.stackSize == 0 || item.stackSize < 4) continue;
			
			String sdust = isSmallDust(item);
			String tdust = isTinyDust(item);
			String nugget = isNugget(item);
			
			int comboSize = 9;
			ItemStack result = null;

			if(sdust != null)
			{
				result = getSmallDustCraftingResult(item);
				comboSize = 4;	
			}
			
			if(nugget != null)
			{
				result = getNuggetCraftingResult(item);
				comboSize = 9;
			}
			
			if(tdust != null)
			{
				result = getTinyDustCraftingResult(item);
				comboSize = 9;
			}
			
			if(result == null) continue;
			
			result.stackSize = 1;
			
			int outputSlot = getOutputSlot(result);
			
			if(outputSlot == -1) continue;
			
			while(item.stackSize >= comboSize)
			{	
				if(items[outputSlot] != null && items[outputSlot].stackSize >= items[outputSlot].getMaxDamage())
				{
					outputSlot = getOutputSlot(result);				
					if(outputSlot == -1) break;							
				}
				
				if(items[outputSlot] == null)
				{						
					items[outputSlot] = result.copy();
				}
				else
				{
					items[outputSlot].stackSize += 1;		
				}
				
				items[i].stackSize -= comboSize;

				didChange = true;
								
				if(items[i].stackSize <= 0)
				{
					items[i] = null;
				}
			}
		}
		
		if(didChange)
		{
			Sort();
			this.markDirty();
		}
		
		return didChange;
	}
	
	private int getOutputSlot(ItemStack istack)
	{
		if(istack == null) return -1;
		
		// First try and add to an existing stack
		for(int i = 88; i < 96; i++)
		{
			// No empty slots of full stacks;
			if(items[i] == null || items[i].stackSize == items[i].getMaxStackSize()) continue;			
			
			if(OreDictionary.itemMatches(items[i], istack, false))
			{
				return i;
			}
		}
		
		// Now try empty
		for(int i = 88; i < 96; i++)
		{
			// Only empty slots
			if(items[i] != null) continue;
			
			return i;
		}
		
		return -1;
	}
	
	private String isSmallDust(ItemStack istack)
	{
		if(istack == null) return null;
		
		List<String> names = getOreDictionaryNames(istack);
		
		for(String name : names)
		{
			if(name.startsWith("dustSmall")) return name;
		}
		
		return null;
	}
	
	private String isTinyDust(ItemStack istack)
	{
		if(istack == null) return null;
		
		List<String> names = getOreDictionaryNames(istack);
		
		for(String name : names)
		{
			if(name.startsWith("dustTiny")) return name;
		}
		
		return null;
	}
	
	private String isNugget(ItemStack istack)
	{
		if(istack == null) return null;
		
		List<String> names = getOreDictionaryNames(istack);
		
		for(String name : names)
		{
			if(name.startsWith("nugget")) return name;
		}
		
		return null;
	}

	private List<String> getOreDictionaryNames(ItemStack istack)
	{
		if(istack == null) return new LinkedList<String>();
		
		List<String> names = new LinkedList<String>();
		
		int[] itemIds = OreDictionary.getOreIDs(istack);
		
		for(int id : itemIds)
		{
			names.add(OreDictionary.getOreName(id));
		}
		
		return names;
	}
	
	private ItemStack getSmallDustCraftingResult(ItemStack stack)
	{
		crafter.setInventorySlotContents(0, null);
		crafter.setInventorySlotContents(1, null);
		crafter.setInventorySlotContents(2, null);
		crafter.setInventorySlotContents(3, null);
		crafter.setInventorySlotContents(4, null);
		crafter.setInventorySlotContents(5, null);
		crafter.setInventorySlotContents(6, null);
		crafter.setInventorySlotContents(7, null);
		crafter.setInventorySlotContents(8, null);
		
		ItemStack single = stack.copy();
		single.stackSize = 1;
		
		crafter.setInventorySlotContents(0, stack.copy());
		crafter.setInventorySlotContents(1, stack.copy());
		crafter.setInventorySlotContents(3, stack.copy());
		crafter.setInventorySlotContents(4, stack.copy());
		
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafter, this.worldObj);
		
		if(result != null)
		{
			return result.copy();
		}
		else
		{
			return null;
		}
	}
	
	private ItemStack getTinyDustCraftingResult(ItemStack stack)
	{	
		ItemStack single = stack.copy();
		single.stackSize = 1;
		
		for(int i = 0; i < 9; i++)
		{
			crafter.setInventorySlotContents(i, null);
			crafter.setInventorySlotContents(i, stack.copy());
		}
		
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafter, this.worldObj);
		
		if(result != null)
		{
			return result.copy();
		}
		else
		{
			return null;
		}
	}
	
	private ItemStack getNuggetCraftingResult(ItemStack stack)
	{	
		ItemStack single = stack.copy();
		single.stackSize = 1;
		
		for(int i = 0; i < 9; i++)
		{
			crafter.setInventorySlotContents(i, null);
			crafter.setInventorySlotContents(i, stack.copy());
		}
		
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafter, this.worldObj);
		
		if(result != null)
		{
			return result.copy();
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public int getSizeInventory() 
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return items[slot];
	}

	@Override
    public ItemStack decrStackSize(int slot, int amount)
    {
		ItemStack item = getStackInSlot(slot);
		
		if(item != null)
		{
			if(item.stackSize <= amount)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				item = item.splitStack(amount);
				
				if(item.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
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
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		items[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() 
	{
		return "Dust Unifier";
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
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return true;
	}

	@Override
	public void openInventory() 
	{
		
	}

	@Override
	public void closeInventory() 
	{

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return true;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack istack, int side)
	{
		System.out.println("CIS:" + slot);
		return (slot >= 0 && slot <= 87);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack istack, int side)
	{
		System.out.println("CES:" + slot);
		return slot > 87 && slot < 96;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return accessibleSlots;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{		
		super.writeToNBT(nbt);
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
		
        nbt.setTag("Items", itemTag);
        nbt.setInteger("cooldown", cooldown);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        
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
        
        cooldown = nbt.getInteger("cooldown");
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
    }
}
