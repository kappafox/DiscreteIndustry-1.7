package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySidedConnector extends TileEntityDiscreteBlock implements ISidedConnection
{
	private boolean[] connections = new boolean[6];

	@Override
	public boolean getConnection(short direction_) 
	{
		if(direction_ >= 0 && direction_ < connections.length)
		{
			return connections[direction_];
		}
		
		return false;
	}

	@Override
	public void setConnection(short direction_, boolean state_) 
	{
		if(direction_ >= 0 && direction_ < connections.length)
		{
			connections[direction_] = state_;
		}
	}

	@Override
	public boolean[] getAllConnections() 
	{
		boolean[] copy = new boolean[connections.length];
		
		for(int i = 0; i < connections.length; i++)
		{
			copy[i] = connections[i];
		}
		
		return copy;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt_)
	{		
		super.writeToNBT(nbt_);
		
		int[] send = new int[connections.length];
		
		for(int i = 0; i < connections.length; i++)
		{
			if(connections[i])
			{
				send[i] = 1;
			}
			else
			{
				send[i] = 0;			
			}
		}
		
		nbt_.setIntArray("cons", send);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
		
		int[] send = nbt_.getIntArray("cons");
		connections = new boolean[send.length];
		
		for(int i = 0; i < send.length; i++)
		{
			if(send[i] > 0)
			{
				connections[i] = true;
			}
			else
			{
				connections[i] = false;			
			}
		}
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
    }
	

	@Override
	public void toggleConnection(short direction_) 
	{
		if(direction_ >= 0 && direction_ < connections.length)
		{
			connections[direction_] = !connections[direction_];
		}
		
		//this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void setAllConnections(boolean state_) 
	{
		for(int i = 0; i < connections.length; i++)
		{
			connections[i] = state_;
		}
	}
}
