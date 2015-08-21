package kappafox.di.base.items;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.event.RetextureEvent;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.init.InternalName;
import ic2.core.item.tool.ItemObscurator;
import ic2.core.network.NetworkManager;
import ic2.core.util.StackUtil;

public class Ghettobscurator extends ItemObscurator 
{
	public Ghettobscurator(InternalName internalName) 
	{
		super(internalName);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack)
	{
		return 420.0D;
    }
	
	@Override
	  public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	  {
	    if ((!entityPlayer.isSneaking()) && (ElectricItem.manager.canUse(itemStack, 0.0D)))
	    {
	      NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
	      String referencedBlockName = nbtData.getString("referencedBlock");
	      Block referencedBlock = referencedBlockName.isEmpty() ? null : (Block)GameData.getBlockRegistry().getRaw(referencedBlockName);
	      if ((referencedBlock == null) || (!isBlockSuitable(referencedBlock))) {
	        return false;
	      }
	      if (IC2.platform.isSimulating())
	      {
	        RetextureEvent event = new RetextureEvent(world, x, y, z, side, referencedBlock, nbtData.getInteger("referencedMeta"), nbtData.getInteger("referencedSide"));
	        
	        MinecraftForge.EVENT_BUS.post(event);
	        if (event.applied)
	        {
	          ElectricItem.manager.use(itemStack, 5000.0D, entityPlayer);
	          
	          return true;
	        }
	        return false;
	      }
	    }
	    else if ((entityPlayer.isSneaking()) && (IC2.platform.isRendering()) && (ElectricItem.manager.canUse(itemStack, 0.0D)))
	    {
	      Block block = world.getBlock(x, y, z);
	      if ((!block.isAir(world, x, y, z)) && (isBlockSuitable(block)))
	      {
	        int meta = world.getBlockMetadata(x, y, z);
	        try
	        {
	          IIcon texture = block.getIcon(side, meta);
	          IIcon textureWorld = block.getIcon(world, x, y, z, side);
	          if ((texture == null) || (texture != textureWorld)) {
	            return false;
	          }
	        }
	        catch (Exception e)
	        {
	          return false;
	        }
	        String referencedBlockName = GameData.getBlockRegistry().getNameForObject(block);
	        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
	        if ((!nbtData.getString("referencedBlock").equals(referencedBlockName)) || (nbtData.getInteger("referencedMeta") != meta) || (nbtData.getInteger("referencedSide") != side))
	        {
	          ((NetworkManager)IC2.network.get()).sendPlayerItemData(entityPlayer, entityPlayer.inventory.currentItem, new Object[] { referencedBlockName, Integer.valueOf(meta), Integer.valueOf(side) });
	          
	          return true;
	        }
	      }
	    }
	    return false;
	  }
	  
	  @Override
	  public void onPlayerItemNetworkData(EntityPlayer entityPlayer, int slot, Object... data)
	  {
	    ItemStack itemStack = entityPlayer.inventory.mainInventory[slot];
	    if (ElectricItem.manager.use(itemStack, 0.0D, entityPlayer))
	    {
	      NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
	      
	      nbtData.setString("referencedBlock", (String)data[0]);
	      nbtData.setInteger("referencedMeta", ((Integer)data[1]).intValue());
	      nbtData.setInteger("referencedSide", ((Integer)data[2]).intValue());
	    }
	  }
	  
	  private static boolean isBlockSuitable(Block block)
	  {
	    return block.renderAsNormalBlock();
	  }

}
