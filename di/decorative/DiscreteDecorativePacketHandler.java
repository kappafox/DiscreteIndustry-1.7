package kappafox.di.decorative;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import kappafox.di.decorative.network.DiscreteDecorativeTextureSyncPacket;
import kappafox.di.decorative.network.DiscreteDecorativeTextureSyncPacket.DiscreteDecorativeTextureSyncMessage;

public class DiscreteDecorativePacketHandler
{
	public static SimpleNetworkWrapper net;
	
	public static void initPackets()
	{
		net = NetworkRegistry.INSTANCE.newSimpleChannel("DiscreteDecorative");
		registerMessage(DiscreteDecorativeTextureSyncPacket.class, DiscreteDecorativeTextureSyncPacket.DiscreteDecorativeTextureSyncMessage.class);
	}
	
	private static int nextPacketId = 0;
	
	private static void registerMessage(Class packet, Class message)
	{
		net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
		net.registerMessage(packet, message, nextPacketId, Side.SERVER);
		nextPacketId += 1;
	}
}
