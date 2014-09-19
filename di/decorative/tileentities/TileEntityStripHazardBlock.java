package kappafox.di.decorative.tileentities;

import ic2.api.tile.IWrenchable;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityStripHazardBlock extends TileEntityDiscreteBlock implements IWrenchable
{
	private int stripIterations;
	private short facing = 0;
	private short strips[];
	
	
	
	public TileEntityStripHazardBlock( )
	{
		this(6);
	}
	
	public TileEntityStripHazardBlock(int stripCount)
	{
		stripIterations = stripCount;
		strips = new short[6];
		
		for(int i = 0; i < strips.length; i++)
		{
			strips[i] = 0;
		}
	}
	
	public boolean isStripHidden(int side)
	{
		if(strips[side] == stripIterations)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public short getFacing( )
	{
		return facing;
	}
	
	@Override
	public void setFacing(short facing)
	{
		this.incrementStripPosition(this.getOppositeSide(facing));
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}	

	public short getStripPosition(int side)
	{
		return strips[side];
	}
	
	public void setStripPosition(short side, short pos)
	{
		strips[side] = pos;
	}
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer player, int side) 
	{	
		if(player.isSneaking() == true)
		{
			return true;
		}
		return false;
	}
	
	private void incrementStripPosition(short side)
	{
		short pos = getStripPosition(side);
		
		if(pos == stripIterations)
		{
			this.setStripPosition(side, (short)0);
		}
		else
		{
			pos++;
			this.setStripPosition(side, pos);
		}
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) 
	{
		return false;
	}

	@Override
	public float getWrenchDropRate() 
	{
		return 0.0F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) 
	{
		return null;
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
    	//super.readFromNBT(tag);
    	readFromNBT(tag);
    }
	
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		int[] send = new int[6];
		for(int i = 0; i < strips.length; i++)
		{
			send[i] = strips[i];
		}
		
		nbt.setInteger("iterations", stripIterations);
		nbt.setIntArray("strips", send);
		nbt.setShort("facing", facing);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		stripIterations = nbt.getInteger("iterations");
		
		int[] send = nbt.getIntArray("strips");
		
		for(int i = 0; i < send.length; i++)
		{
			strips[i] = (short)send[i];
		}
		
		facing = nbt.getShort("facing");
		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private short getOppositeSide(short side)
	{
		switch(side)
		{
			case 0:
				return 1;
			
			case 1:
				return 0;
			
			case 2:
				return 3;
			
			case 3:
				return 2;
			
			case 4:
				return 5;
			
			case 5:
				return 4;
				
			default:
				return side;
		}
	}
}
