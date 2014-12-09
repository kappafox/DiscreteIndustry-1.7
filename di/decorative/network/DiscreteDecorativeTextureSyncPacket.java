package kappafox.di.decorative.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class DiscreteDecorativeTextureSyncPacket implements IMessageHandler<DiscreteDecorativeTextureSyncPacket.DiscreteDecorativeTextureSyncMessage, IMessage>
{
  public IMessage onMessage(DiscreteDecorativeTextureSyncMessage message, MessageContext ctx)
  {
    if (ctx.side.isServer())
    {
      String texture = message.texture;
      int x = message.x;
      int y = message.y;
      int z = message.z;
      
      TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)DimensionManager.getWorld(message.dimension).getTileEntity(x, y, z);
      
      if (tile != null) 
      {
        tile.setSecondaryTextureSource(texture, message.meta, message.side);
      }
    }
    return null;
  }
  
  public static class DiscreteDecorativeTextureSyncMessage implements IMessage
  {
    private String texture;
    private int x;
    private int y;
    private int z;
    private int meta;
    private int side;
    private int dimension;
    
    public DiscreteDecorativeTextureSyncMessage() {}
    
    public DiscreteDecorativeTextureSyncMessage(String texture, int x, int y, int z, int meta, int side, int dimension)
    {
      this.texture = texture;
      this.x = x;
      this.y = y;
      this.z = z;
      this.meta = meta;
      this.side = side;
      this.dimension = dimension;
    }
    
    public void fromBytes(ByteBuf buf)
    {
      this.texture = ByteBufUtils.readUTF8String(buf);
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
      this.meta = buf.readInt();
      this.side = buf.readInt();
      this.dimension = buf.readInt();
    }
    
    public void toBytes(ByteBuf buf)
    {
      ByteBufUtils.writeUTF8String(buf, this.texture);
      buf.writeInt(this.x);
      buf.writeInt(this.y);
      buf.writeInt(this.z);
      buf.writeInt(this.meta);
      buf.writeInt(this.side);
      buf.writeInt(this.dimension);
    }
  }
}
