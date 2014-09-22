package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySidedConnector extends TileEntityDiscreteBlock implements ISidedConnection
{
	private boolean[] connections = new boolean[6];
	
	@Override
	public boolean getConnection(short direction) 
	{
		if(direction >= 0 && direction < connections.length)
		{
			return connections[direction];
		}
		
		return false;
	}

	@Override
	public void setConnection(short direction, boolean state) 
	{
		if(direction >= 0 && direction < connections.length)
		{
			connections[direction] = state;
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
	public void writeToNBT(NBTTagCompound nbt)
	{		
		super.writeToNBT(nbt);
		
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
		
		nbt.setIntArray("cons", send);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		int[] send = nbt.getIntArray("cons");
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
	public void toggleConnection(short direction) 
	{
		if(direction >= 0 && direction < connections.length)
		{
			connections[direction] = !connections[direction];
		}
	}

	@Override
	public void setAllConnections(boolean state) 
	{
		for(int i = 0; i < connections.length; i++)
		{
			connections[i] = state;
		}
	}
}
