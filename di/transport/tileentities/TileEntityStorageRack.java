package kappafox.di.transport.tileentities;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.oredict.OreDictionary;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.SideHelper;

public class TileEntityStorageRack extends TileEntityDiscreteBlock implements ISidedInventory
{
	private ItemStack[] storageUnits;
	private ItemStack[] insertionSlots;
	private ItemStack[] extractionSlots;
	
	private int[] amounts;
	
	private long clicked[];
	private boolean canUpdate = false;
	
	private static final int DOUBLE_CLICK_DELAY = 1000;	
	private static short MAX_STACK_SIZE = 64;
		
	private boolean unifyContents = true;
	private boolean sticky = false;
	private boolean leaveLast = true;
	
	private ItemStack lastItemAdded = null;
	
	//size in STACKS
	private static final short MAX_CAPACITY_WOOD = 64;
	private static final short MAX_CAPACITY_STONE = 128;
	private static final short MAX_CAPACITY_IRON = 256;
	private static final short MAX_CAPACITY_REDSTONE = 512;
	private static final short MAX_CAPACITY_OBSIDIAN = 1024;
	private static final short MAX_CAPACITY_GOLD = 2048;
	private static final short MAX_CAPACITY_DIAMOND = 4096;
	private static final short MAX_CAPACITY_EMERALD= 8192;
	private static final short MAX_CAPACITY_IRIDIUM = 16364;
	

	public TileEntityStorageRack( )
	{
		this(1);
	}
	
	public static int getMaxCapacity(int damage)
	{
		switch(damage)
		{
			case 0:
				return MAX_CAPACITY_WOOD * MAX_STACK_SIZE;
			case 2:
				return MAX_CAPACITY_STONE * MAX_STACK_SIZE;
			case 4:
				return MAX_CAPACITY_IRON * MAX_STACK_SIZE;
			case 6:
				return MAX_CAPACITY_REDSTONE * MAX_STACK_SIZE;
			case 8:
				return MAX_CAPACITY_OBSIDIAN * MAX_STACK_SIZE;
			case 10:
				return MAX_CAPACITY_GOLD * MAX_STACK_SIZE;
			case 12:
				return MAX_CAPACITY_DIAMOND * MAX_STACK_SIZE;
			case 14:
				return MAX_CAPACITY_EMERALD * MAX_STACK_SIZE;
			case 16:
				return MAX_CAPACITY_IRIDIUM * MAX_STACK_SIZE;
		}
		
		return 0;
	}
	
	public int getMax(int slot)
	{
		if(this.hasContainer(slot))
		{
			return getMaxCapacity(storageUnits[slot].getItemDamage());
		}
		
		return 0;
	}
	
	public TileEntityStorageRack(int slots)
	{
		super();
		storageUnits = new ItemStack[slots];
		amounts = new int[slots];
		insertionSlots = new ItemStack[slots];
		extractionSlots = new ItemStack[slots];
		clicked = new long[slots];
	}
	
	@Override
	public boolean canUpdate( )
	{
		return true;
	}
	
	@Override
	public void updateEntity( )
	{
		
	}
	
	private boolean updateInsertionSlot(int slot)
	{
		if(!this.hasContainer(slot) || this.getInsertionSlotCount(slot) <= 0)
		{
			return false;
		}
		
		int amount = amounts[slot] + this.getExtractionSlotCount(slot);
		int max = this.getMaxCapacity(storageUnits[slot].getItemDamage());
		int adjustedMax =  max - insertionSlots[slot].getMaxStackSize();
		
		//shifting down into the proper space
		if(amount < adjustedMax)
		{
			int sim = amount + insertionSlots[slot].stackSize;
			
			if(sim <= adjustedMax)
			{
				amounts[slot] += insertionSlots[slot].stackSize;
				insertionSlots[slot].stackSize = 0;
			}
			else
			{
				int space = adjustedMax - amount;
				insertionSlots[slot].stackSize -= space;
				amounts[slot] += space;
			}
		}
		
		return true;
	}
	
	private boolean updateExtractionSlot(int slot)
	{
		//some basic criteria for not progressing
		if(!this.hasContainer(slot) || this.getContainerContentCount(slot) == 0 || this.isContainerEmpty(slot))
		{
			return false;
		}
		
		//space in the extraction slot
		if(this.getExtractionSlotCount(slot) >= storageUnits[slot].getMaxStackSize())
		{
			return false;
		}
		
		//the amount in reserve
		int reserve = amounts[slot] + this.getInsertionSlotCount(slot);
		
		//items in reserve?
		if(reserve <= 0)
		{
			return false;
		}
		
		//space in the extraction slot
		int space = this.getContainerContent(slot).getMaxStackSize() - this.getExtractionSlotCount(slot);
		

		if(extractionSlots[slot] == null)
		{
			extractionSlots[slot] = this.getContainerContent(slot).copy();
			extractionSlots[slot].stackSize = 0;
		}

		if(space <= reserve)
		{
			amounts[slot] -= space;
			extractionSlots[slot].stackSize = this.getContainerContent(slot).getMaxStackSize();
		}
		else
		{
			extractionSlots[slot].stackSize += reserve;
			amounts[slot] -= reserve;
		}
		return true;
	}
	
	
	private int getFreeSpace(int slot)
	{
		int count = this.getContainerContentCount(slot);
		int max = this.getMaxCapacity(storageUnits[slot].getItemDamage());
		
		return max - count;
	}
	
	public boolean isWaitingForDoubleClick(int slot)
	{
		if(slot < storageUnits.length)
		{
			if(clicked[slot] != 0)
			{
				return true;
			}
		}
		return false;
	}

	public boolean shouldTakeAllFromInventory(int slot)
	{
		if(clicked[slot] == 0)
		{
			clicked[slot] = System.currentTimeMillis();
		}
		else
		{
			long currentTime = System.currentTimeMillis();
			
			if(currentTime - clicked[slot] < DOUBLE_CLICK_DELAY)
			{
				clicked[slot] = 0;
				return true;
			}
			
			clicked[slot] = 0;
		}
		
		return false;
	}
	
	public int getSize( )
	{
		return storageUnits.length;
	}
	
	private int getInsertionSlotCount(int slot)
	{
		if(insertionSlots[slot] != null)
		{
			return insertionSlots[slot].stackSize;
		}
		
		return 0;
	}
	
	private int getExtractionSlotCount(int slot)
	{
		if(extractionSlots[slot] != null)
		{
			return extractionSlots[slot].stackSize;
		}
		
		return 0;
	}
	
	public int getContainerContentCount(int slot)
	{
		if(slot < storageUnits.length)
		{
			if(storageUnits[slot] != null && storageUnits[slot].hasTagCompound())
			{
				return amounts[slot] + this.getInsertionSlotCount(slot) + this.getExtractionSlotCount(slot);
			}
		}
		
		return 0;
	}
	
	public ItemStack getContainerContent(int slot)
	{
		if(slot < storageUnits.length)
		{
			if(storageUnits[slot] != null && storageUnits[slot].hasTagCompound())
			{
				ItemStack t = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
				
				if(t != null)
				{
					ItemStack t2 = ItemStack.copyItemStack(t);
					t2.stackSize = 1;
					
					return t2;
				}
				else
				{
					//busted itemstack? clear it?
					this.emptyContainer(slot);
				}
			}
		}
		
		return null;
	}
	
	public boolean addContainer(int slot, ItemStack container)
	{
		if(slot < storageUnits.length)
		{
			if(storageUnits[slot] == null)
			{
				storageUnits[slot] = container;
				
				if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
				{
					if(container.hasTagCompound())
					{
						if(container.getTagCompound().hasKey("ContainerAmount"))
						{
							amounts[slot] = container.getTagCompound().getInteger("ContainerAmount");
							ItemStack content = ItemStack.loadItemStackFromNBT(container.getTagCompound());
							
							//the last stack goes in the fake slot
							if(amounts[slot] > (this.getMaxCapacity(container.getItemDamage()) - content.getMaxStackSize()))
							{
								int overflow = amounts[slot] - (this.getMaxCapacity(container.getItemDamage()) - content.getMaxStackSize());
								amounts[slot] -= overflow;
								
								insertionSlots[slot] = content.copy();
								insertionSlots[slot].stackSize = overflow;
								
								ItemStack e = content.copy();
								e.stackSize = 0;
								extractionSlots[slot] = e;
							}
							else
							{
								//not near full container
								ItemStack dummy = content.copy();
								dummy.stackSize = 0;
								insertionSlots[slot] = dummy;
								extractionSlots[slot] = dummy.copy();
							}
						
							container.getTagCompound().removeTag("ContainerAmount");
						}
					}
				}
				
				this.markDirty();
				this.updateExtraSlots(slot);
				this.updateTileEntity();
				return true;
			}
		}
		
		return false;
	}
	
	public ItemStack removeContainer(int slot)
	{
		if(SideHelper.onServer() == true)
		{
			if(slot < storageUnits.length)
			{
				if(storageUnits[slot] != null)
				{
					ItemStack con = storageUnits[slot];
					con.stackSize = 1;
					
					int total = amounts[slot] + this.getInsertionSlotCount(slot) + this.getExtractionSlotCount(slot);
					if(con.hasTagCompound() == true)
					{
						if(this.getContainerContentCount(slot) > 0)
						{
							con.getTagCompound().setInteger("ContainerAmount", total);
						}
						else
						{
							con.setTagCompound(null);
						}
					}
					
					storageUnits[slot] = null;
					insertionSlots[slot] = null;
					extractionSlots[slot] = null;
					amounts[slot] = 0;

					this.updateTileEntity();
					return con;
				}
			}
		}
		return null;
	}
	
	public boolean hasContainer(int slot)
	{
		if(slot < storageUnits.length && slot >= 0)
		{
			if(storageUnits[slot] != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isContainerEmpty(int slot)
	{
		if(slot < storageUnits.length)
		{
			if(storageUnits[slot] != null)
			{
				if(storageUnits[slot].hasTagCompound())
				{
					return false;
				}
			}
		}
		
		return true;
	}

	public ItemStack removeItemFromContainer(int slot, boolean fullStack)
	{
		if(fullStack)
		{
			int stackSize = 64;
			if(this.hasContainer(slot) && this.isContainerEmpty(slot) == false)
			{
				if(this.getContainerContentCount(slot) > 0)
				{
					ItemStack content = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
					stackSize = content.getMaxStackSize();
				}
				else
				{
					this.emptyContainer(slot);
					return null;
				}
			}
			else
			{
				//slot is invalid or empty
				return null;
			}

			return this.removeItemFromContainer(slot, stackSize);
		}
		else
		{
			return this.removeItemFromContainer(slot, 1);
		}
	}
	
	public ItemStack removeItemFromContainer(int slot, int count)
	{	
		//this.dumpDebugStats(slot);
		if(slot < storageUnits.length)
		{
			if(this.hasContainer(slot) && this.getContainerContentCount(slot) > 0 && this.isContainerEmpty(slot) == false)
			{
				ItemStack content = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
				
				if(content != null)
				{
					int stackSize = count;
					
					//take from amounts first
					if(amounts[slot] > 0)
					{
						if(amounts[slot] > stackSize)
						{
							ItemStack result = content.copy();
							result.stackSize = stackSize;
							
							amounts[slot] = amounts[slot] - stackSize;
							
							this.updateExtraSlots(slot);
							this.updateTileEntity();						
							return result;
						}
						
						if(amounts[slot] == stackSize)
						{
							ItemStack result = content.copy();
							result.stackSize = stackSize;
							
							amounts[slot] = 0;
							
							this.updateExtraSlots(slot);
							this.updateTileEntity();
							return result;
						}
						
						if(amounts[slot] < stackSize)
						{
							ItemStack result = content.copy();
							result.stackSize = amounts[slot];
							
							amounts[slot] = 0;
							
							if(result.stackSize < result.getMaxStackSize())
							{
								if(this.getExtractionSlotCount(slot) > 0)
								{
									int spaceLeft = result.getMaxStackSize() - result.stackSize;
									
									if(this.getExtractionSlotCount(slot) > spaceLeft)
									{
										extractionSlots[slot].stackSize -= spaceLeft;
										result.stackSize += spaceLeft;
									}
									else
									{
										if(this.getExtractionSlotCount(slot) <= spaceLeft)
										{
											extractionSlots[slot].stackSize -= spaceLeft;
											result.stackSize += spaceLeft;
											
											if(this.getContainerContentCount(slot) < 1)
											{
												this.emptyContainer(slot);
											}
										}
									}
								}
							}
							
							this.updateExtraSlots(slot);
							this.updateTileEntity();
							return result;
						}
					}
					else
					{
						if(this.getExtractionSlotCount(slot) > 0)
						{
							int eAmount = this.getExtractionSlotCount(slot);
							
							if(count < eAmount)
							{
								ItemStack result = content.copy();
								result.stackSize = count;
								
								extractionSlots[slot].stackSize -= count;
								this.updateExtraSlots(slot);
								this.updateTileEntity();	
								return result;
							}
							
							if(count >= eAmount)
							{
								ItemStack result = content.copy();
								result.stackSize = extractionSlots[slot].stackSize;
								
								extractionSlots[slot].stackSize = 0;
								this.emptyContainer(slot);
								return result;
							}
						}
						else
						{
							//negative or no items
							this.emptyContainer(slot);
						}
					}
					
				}
			}
		}
		return null;
	}
	
	private void emptyContainer(int slot)
	{
		if(slot < storageUnits.length)
		{
			if(sticky == false)
			{
				storageUnits[slot].setTagCompound(null);
				extractionSlots[slot] = null;
				insertionSlots[slot] = null;
				amounts[slot] = 0;
			}
			else
			{
				amounts[slot] = 0;
				insertionSlots[slot].stackSize = 0;
				extractionSlots[slot].stackSize = 0;
			}
			
			this.updateTileEntity();
		}			
	}
	
	public ItemStack addItemToContainer(int slot, ItemStack istack, boolean fullStack)
	{
		if(SideHelper.onServer())
		{
			if(slot < storageUnits.length)
			{
				if(this.hasContainer(slot) && istack != null)
				{
					if(this.isContainerEmpty(slot) == false)
					{
						ItemStack contents = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
						
						//equal items, just add on
						if(this.areItemStacksEqual(contents, istack) && this.getFreeSpace(slot) > 0)
						{
							
							//full insertion slot
							if((this.getInsertionSlotCount(slot) == contents.getMaxStackSize()))
							{
								if(this.getFreeSpace(slot) > 0)
								{
									int fspace = this.getFreeSpace(slot);
									
									if(fspace >= istack.stackSize)
									{
										amounts[slot] += istack.stackSize;
										istack.stackSize = 0;
										
										this.updateExtraSlots(slot);
										this.updateTileEntity();
										return istack;
									}
									
									if(fspace < istack.stackSize)
									{
										amounts[slot] += fspace;
										istack.stackSize -= fspace;
										
										this.updateExtraSlots(slot);
										this.updateTileEntity();
										return istack;
									}	
								}
								else
								{
									return istack;
								}
							}
							
							if(insertionSlots[slot] != null)
							{
								ItemStack aslot = insertionSlots[slot];
								//slot is already full
								if(insertionSlots[slot].stackSize == insertionSlots[slot].getMaxStackSize())
								{
									return istack;
								}
								
								int space = aslot.getMaxStackSize() - aslot.stackSize;
								
								//plenty of space
								if(space >= istack.stackSize)
								{								
									aslot.stackSize += istack.stackSize;
									istack.stackSize = 0;
									
									this.updateExtraSlots(slot);
									this.updateTileEntity();
									return istack;
								}
								
								if(space < istack.stackSize)
								{
									int leftover = istack.stackSize - space;
									
									aslot.stackSize += space;
									istack.stackSize = leftover;
									
									this.updateExtraSlots(slot);
									this.updateTileEntity();
									return istack;
								}
							}
							else
							{
								insertionSlots[slot] = istack.copy();
								istack.stackSize = 0;
								
								this.updateExtraSlots(slot);
								this.updateTileEntity();
								return istack;
							}
						}
						else	//itemstacks aren't equal, so just return what you put in
						{
							return istack;
						}
					}
					else	//no items in here yet
					{	
						storageUnits[slot].stackTagCompound = null;
						NBTTagCompound contents = new NBTTagCompound();
						istack.writeToNBT(contents);
						storageUnits[slot].stackTagCompound = contents;
						
						amounts[slot] = istack.stackSize;
						
						ItemStack dummy = istack.copy();
						dummy.stackSize = 0;
						
						extractionSlots[slot] = dummy;
						insertionSlots[slot] = dummy.copy();
								
						istack.stackSize = 0;
						
						this.updateExtraSlots(slot);
						this.updateTileEntity();
						
						return istack;						
					}
				}
			
			}
		}
		return istack;
	}

	public boolean willContainerAccept(int slot, ItemStack istack)
	{
		if(slot < storageUnits.length)
		{
			if(this.hasContainer(slot) && istack != null)
			{
				if(this.isContainerEmpty(slot) == false)
				{
					ItemStack contents = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
					
					if(this.areItemStacksEqual(contents, istack) == true)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean areItemStacksEqual(ItemStack a, ItemStack b)
	{
		if(a.isItemEqual(b) && ItemStack.areItemStackTagsEqual(a, b))
		{
			return true;
		}
		
		
		/*
		int oid = OreDictionary.getOreID(a);
		ArrayList<ItemStack> matches = OreDictionary.getOres(oid);
		
		for(ItemStack t: matches)
		{
			if(t.isItemEqual(b) && ItemStack.areItemStackTagsEqual(t, b))
			{
				return true;
			}
		}
		*/
		
		return false;
	}
	
	private int maxCapacity(int slot)
	{
		if(slot > storageUnits.length)
		{
			return 0;
		}
		
		int damage = storageUnits[slot].getItemDamage();
		
		switch(damage)
		{
			case 0:
				return MAX_CAPACITY_WOOD * MAX_STACK_SIZE;
			case 2:
				return MAX_CAPACITY_STONE * MAX_STACK_SIZE;
			case 4:
				return MAX_CAPACITY_IRON * MAX_STACK_SIZE;
			case 6:
				return MAX_CAPACITY_REDSTONE * MAX_STACK_SIZE;
			case 8:
				return MAX_CAPACITY_OBSIDIAN * MAX_STACK_SIZE;
			case 10:
				return MAX_CAPACITY_GOLD * MAX_STACK_SIZE;
			case 12:
				return MAX_CAPACITY_DIAMOND * MAX_STACK_SIZE;
			case 14:
				return MAX_CAPACITY_EMERALD * MAX_STACK_SIZE;
			case 16:
				return MAX_CAPACITY_IRIDIUM * MAX_STACK_SIZE;
		}
		
		return 0;
	}
	
	private void updateTileEntity( )
	{	
		if(SideHelper.onServer() && this.worldObj != null)
		{
			this.markDirty();
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}		
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
    	this.readFromNBT(tag);
    	
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
	
	
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList containers = tag.getTagList("Containers", tag.getId());
               
        int size = tag.getInteger("slots");
        storageUnits = null;
        storageUnits = new ItemStack[size];
        
        int[] t = tag.getIntArray("amounts");
        amounts = null;
        amounts = t;
        

        for (int i = 0; i < containers.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)containers.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size)
            {
                storageUnits[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        NBTTagList aslots = tag.getTagList("accessSlots", tag.getId());
        
        insertionSlots = null;
        insertionSlots = new ItemStack[size];
        
        for (int i = 0; i < aslots.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)aslots.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size)
            {
            	insertionSlots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

       
        extractionSlots = null;
        extractionSlots = new ItemStack[size];
        
        
        NBTTagList eslots = tag.getTagList("extractionSlots", tag.getId());
        
        for (int i = 0; i < eslots.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)eslots.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < size)
            {
            	extractionSlots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        unifyContents = tag.getBoolean("unifyContents");
        sticky = tag.getBoolean("sticky");
        leaveLast = tag.getBoolean("leaveLast");
        
        clicked = new long[size];
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < storageUnits.length; ++i)
        {
            if (storageUnits[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                storageUnits[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        NBTTagList nbttaglist2 = new NBTTagList();
        
        for (int i = 0; i < insertionSlots.length; ++i)
        {
            if (insertionSlots[i] != null)
            {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)i);
                insertionSlots[i].writeToNBT(nbttagcompound2);
                nbttaglist2.appendTag(nbttagcompound2);
            }
        }
        
        NBTTagList eslots = new NBTTagList();
        
        for (int i = 0; i < extractionSlots.length; ++i)
        {
            if (extractionSlots[i] != null)
            {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)i);
                extractionSlots[i].writeToNBT(nbttagcompound2);
                eslots.appendTag(nbttagcompound2);
            }
        }
        
        tag.setTag("extractionSlots", eslots);
        tag.setTag("accessSlots", nbttaglist2);
        tag.setTag("Containers", nbttaglist);
        tag.setBoolean("unifyContents", unifyContents);
        tag.setBoolean("sticky", sticky);
        tag.setBoolean("leaveLast", leaveLast);
        tag.setInteger("slots", storageUnits.length);
        tag.setIntArray("amounts", amounts);
    }
    
    private boolean updateExtraSlots(int slot)
    {
    	boolean a = this.updateInsertionSlot(slot);
    	boolean b = this.updateExtractionSlot(slot);
    	
    	return (a || b);
    }

	@Override
	public int getSizeInventory()
	{
		return storageUnits.length * 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		this.updateExtraSlots(slot);
		if(slot < storageUnits.length)
		{
			return insertionSlots[slot];
		}

		return extractionSlots[(slot - storageUnits.length)];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		ItemStack i = null;
		if(slot < storageUnits.length)
		{
			i = this.removeItemFromContainer(slot, count);
		}
		else
		{
			i = this.removeItemFromContainer(slot - storageUnits.length, count);
		}
		
		this.updateTileEntity();
		return i;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return null;
	}

	//this function works on modifying the original itemstack, be warned
	@Override
	public void setInventorySlotContents(int slot, ItemStack istack)
	{
		if(SideHelper.onServer())
		{
			if(slot < storageUnits.length)
			{
				if(istack != null && this.hasContainer(slot))
				{
					if(this.isContainerEmpty(slot))
					{
						storageUnits[slot].stackTagCompound = null;
						NBTTagCompound contents = new NBTTagCompound();
						istack.writeToNBT(contents);
						storageUnits[slot].stackTagCompound = contents;
						
						amounts[slot] = 0;
						
						ItemStack dummy = istack.copy();
						dummy.stackSize = 0;
						
						extractionSlots[slot] = dummy;						
						insertionSlots[slot] = istack;
					}
					else
					{
						insertionSlots[slot] = istack;
					}
					
					this.updateExtraSlots(slot);
					this.updateTileEntity();
				}
			}
			else
			{
				if(slot >= storageUnits.length)
				{
					if(this.hasContainer(slot - storageUnits.length))
					{
						if(istack == null)
						{
							extractionSlots[slot - storageUnits.length].stackSize = 0;
							
							if(this.getContainerContentCount(slot - storageUnits.length) <= 0)
							{
								this.emptyContainer(slot);
							}
						}
						else
						{
							extractionSlots[slot - storageUnits.length] = istack;	
						}
					}
					this.updateExtraSlots(slot);
					this.updateTileEntity();		
				}
			}
		}
	}
	
	@Override
	public String getInventoryName()
	{
		return "Discrete Storage Rack";
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
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack istack)
	{
		//nothing is valid for extraction slots
		if(slot >= storageUnits.length)
		{
			return false;
		}
		
		if(this.hasContainer(slot))
		{
			//already item in container
			if(this.isContainerEmpty(slot) == false)
			{
				ItemStack contents = ItemStack.loadItemStackFromNBT(storageUnits[slot].getTagCompound());
				if(this.areItemStacksEqual(contents, istack))
				{
					return true;

				}
			}
			else
			{
				return true;
			}
		}

		return false;
	}

	public void dumpDebugStats(int slot)
	{
		int amount = amounts[slot];
		int ASamount = this.getInsertionSlotCount(slot);
		int total = amount + ASamount + this.getExtractionSlotCount(slot);
		
		System.out.println("Total:" + total + "\tAmounts:" + amount + "\tIS:" + ASamount + "\tES:" + this.getExtractionSlotCount(slot));
	}

		
	@Override
	public void markDirty( )
	{
		super.markDirty();
		if(SideHelper.onServer() && this.worldObj != null)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{		
		switch(storageUnits.length)
		{
			case 1:
				return new int[]{0,1};
				
			case 2:
				return new int[]{0,1,2,3};
				
			case 4:
				return new int[]{0,1,2,3,4,5,6,7};
		}
		
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack istack, int side)
	{
		//I-Slots
		if(slot < storageUnits.length && this.hasContainer(slot))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack istack, int side)
	{
		return true;
	}

	public boolean getSticky()
	{
		return sticky;
	}

	public void toggleSticky( )
	{
		sticky = (!sticky);
		this.updateTileEntity();
	}
}
