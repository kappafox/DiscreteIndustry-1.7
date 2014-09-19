package kappafox.di;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;

import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.base.network.ServerSideBlockFunctions;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import kappafox.di.transport.DiscreteTransportPacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

/*
public class DiscretePacketHandler implements IPacketHandler
{
	
	public static final short MODID_BASE = 0;
	public static final short MODID_DECORATIVE = 1;
	public static final short MODID_ELECTRICS = 2;
	public static final short MODID_TRANSPORT = 3;
	
	private HashMap<Short, IPacketHandler> subHandler;
	
	public DiscretePacketHandler( )
	{
		subHandler = new HashMap<Short, IPacketHandler>();
		
		subHandler.put((short)3, new DiscreteTransportPacketHandler());
	}
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		if(packet instanceof PacketDiscreteSync && packet.channel.equals("DI_GENERIC_SYNC"))
		{
			PacketDiscreteSync p = (PacketDiscreteSync)packet;
			
			if(p.module == 0)
			{
				this.handlePacket(player, p);
			}
			else
			{
				subHandler.get(p.module).onPacketData(manager, packet, player);
			}
		}
		else
		{
			//"DD_FLAG_SYNC"
			if(packet.channel.equals("DD_FLAG_SYNC"))
			{
				this.handleLoomSync(manager, packet, player);
			}
		}
	}
	
	private void handlePacket(Player player, PacketDiscreteSync packet)
	{

	}
	private void handleLoomSync(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
	    DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		byte type = 15;
		int colour = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		int d = 0;
		
		try
		{
			type = inputStream.readByte();
			colour = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			d = inputStream.readInt();
			
		}
		catch(Exception e_)
		{
			System.out.println(e_);
		}
		
		//System.out.println(type + "/" + colour + "/(" + x + "," + y + "," + z + ")" + "D:" + d);
		//Primary colour updated
		if(type == 0)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{
				World w = MinecraftServer.getServer().worldServerForDimension(d);
				
				TileEntityLoomBlock tile = (TileEntityLoomBlock)w.getTileEntity(x, y, z);
				tile.setPrimaryPaintColour(colour);
				w.markBlockForUpdate(x, y, z);
			}
		}		
	}

}
*/
