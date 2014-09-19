package kappafox.di.base.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketCrafter
{
	/*
	//byte  = 1 bytes
	//short = 2 bytes
	//int	= 4 bytes
	//long	= 8 bytes
	//float = 4 bytes
	//double = 8 bytes
	public static void onBlockActivated(int id, int x, int y, int z, int dimension, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(30);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		//System.out.println("SENDING 1");
		try 
		{
	        outputStream.writeInt(id);			//
	        outputStream.writeInt(x);			//4
	        outputStream.writeInt(y);			//8
	        outputStream.writeInt(z);			//12
	        outputStream.writeByte(dimension);	//13
	        outputStream.writeByte(side);		//14
	        outputStream.writeFloat(hitx);		//18
	        outputStream.writeFloat(hity);		//22
	        outputStream.writeFloat(hitz);		//26
		}
		catch (Exception e) 
		{
	        e.printStackTrace();
		}

		PacketDiscreteSync packet = new PacketDiscreteSync();
		packet.module = 0;
		packet.type = 1;
		packet.channel = "DI_GENERIC_SYNC";
		packet.data = bos.toByteArray();
		packet.length = bos.size();	
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	public static void onBlockClicked(int id, int x, int y, int z, int dimension, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(30);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try 
		{
	        outputStream.writeInt(id);			//
	        outputStream.writeInt(x);			//4
	        outputStream.writeInt(y);			//8
	        outputStream.writeInt(z);			//12
	        outputStream.writeByte(dimension);	//13
	        outputStream.writeByte(side);		//14
	        outputStream.writeFloat(hitx);		//18
	        outputStream.writeFloat(hity);		//22
	        outputStream.writeFloat(hitz);		//26
		}
		catch (Exception e) 
		{
	        e.printStackTrace();
		}

		PacketDiscreteSync packet = new PacketDiscreteSync();
		packet.module = 0;
		packet.type = 2;
		packet.channel = "DI_GENERIC_SYNC";
		packet.data = bos.toByteArray();
		packet.length = bos.size();	
		
		PacketDispatcher.sendPacketToServer(packet);
	}
	*/
}
