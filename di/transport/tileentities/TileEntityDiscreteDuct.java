package kappafox.di.transport.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityDiscreteDuct extends TileEntity
{
	
	private boolean[] connections = new boolean[6];
	//private int connectionCount;
	private boolean checked = false;
	
	public TileEntityDiscreteDuct( )
	{
		for(int i = 0; i < connections.length; i++)
		{
			connections[i] = false;
		}
	}
	
	@Override
	public void updateEntity( )
	{
		if(checked == false)
		{
			checked = true;
			checkConnections(0);
		}		
		super.updateEntity();
	}
	
	@Override
	public boolean canUpdate( )
	{
		return true;
	}
	
	public boolean hasConnection(int side_)
	{
		return connections[side_];
	}
	
	public void setConnection(int side_, boolean state_)
	{
		connections[side_] = state_;
	}
	
	public int getConnectionCount( )
	{
		int count = 0;
		
		for(int i = 0; i < connections.length; i++)
		{
			if(connections[i] == true)
			{
				count++;
			}
		}
		
		return count;
	}

	public void checkConnections(int id_)
	{	
		//North
		if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileEntityDiscreteDuct)
		{
			//this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord + 1);
			connections[2] = true;
		}
		else
		{
			connections[2] = false;
		}
		
		//East
		if(this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileEntityDiscreteDuct)
		{
			//this.worldObj.markBlockForUpdate(xCoord - 1, yCoord, zCoord);
			connections[5] = true;
		}
		else
		{
			connections[5] = false;
		}
		
		//South
		if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileEntityDiscreteDuct)
		{
			//this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord - 1);
			connections[3] = true;
		}
		else
		{
			connections[3] = false;
		}
		
		//West
		if(this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileEntityDiscreteDuct)
		{
			//this.worldObj.markBlockForUpdate(xCoord + 1, yCoord, zCoord);
			connections[4] = true;
		}
		else
		{
			connections[4] = false;
		}
		
		//Down
		
		//Up
		
		if(worldObj.isRemote == false)
		{
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}

		
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);

	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt_)
	{
		super.writeToNBT(nbt_);
		
		int[] cons = new int[6];
		for(int i = 0; i < 6; i++)
		{
			if(connections[i] == true)
			{
				cons[i] = 1;
			}
			else
			{
				cons[i] = 0;
			}
		}
		nbt_.setIntArray("cons", cons);
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);
		
		int[] t = nbt_.getIntArray("cons");
		
		for(int i = 0; i < 6; i++)
		{
			if(t[i] == 1)
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
	//@SideOnly(Side.SERVER)
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
	//@SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
    	NBTTagCompound tag = packet.func_148857_g();
    	readFromNBT(tag);
    }
	
	
}
