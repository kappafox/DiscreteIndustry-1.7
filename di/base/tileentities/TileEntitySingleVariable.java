package kappafox.di.base.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySingleVariable extends TileEntity
{
	private int variable = 0;
	
	public void writeToNBT(NBTTagCompound nbt_)
	{		
		super.writeToNBT(nbt_);
		nbt_.setInteger("variable", variable);

		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void readFromNBT(NBTTagCompound nbt_)
	{
		super.readFromNBT(nbt_);

		variable = nbt_.getInteger("variable");
	
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public int getVariable( )
	{
		return variable;
	}
	
	public void setVariable(int var_)
	{
		variable = var_;
	}
	
	@Override
    public S35PacketUpdateTileEntity getDescriptionPacket()
    {
		//System.out.println("GET PACKET:" + this.xCoord + ":" + this.yCoord + ":" + this.zCoord);
		//System.out.println("send packet!");
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
		//System.out.println("got packet!");
		//System.out.println(this.xCoord + ":" + this.yCoord + ":" + this.zCoord);
		
    	NBTTagCompound tag = packet.func_148857_g();
    	readFromNBT(tag);
    }
}


