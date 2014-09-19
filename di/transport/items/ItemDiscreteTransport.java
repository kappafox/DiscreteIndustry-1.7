package kappafox.di.transport.items;

import java.util.HashMap;
import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDiscreteTransport extends Item
{
	
	private static HashMap<Integer, String> names = new HashMap<Integer, String>();
	private static HashMap<Integer, IIcon> icons = new HashMap<Integer, IIcon>();
	
	private static IIcon ICON_STORAGERACK_WOOD;
	private static IIcon ICON_STORAGERACK_STONE;
	private static IIcon ICON_STORAGERACK_IRON;
	private static IIcon ICON_STORAGERACK_REDSTONE;
	private static IIcon ICON_STORAGERACK_OBSIDIAN;
	private static IIcon ICON_STORAGERACK_GOLD;
	private static IIcon ICON_STORAGERACK_DIAMOND;
	private static IIcon ICON_STORAGERACK_EMERALD;
	private static IIcon ICON_STORAGERACK_IRIDIUM;
	
	public ItemDiscreteTransport()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setUnlocalizedName("BaseDiscreteTransportItem");
		
		names.put(0, "Wooden Storage Unit");
		names.put(2, "Stone Storage Unit");
		names.put(4, "Iron Storage Unit");
		names.put(6, "Redstone Storage Unit");
		names.put(8, "Obsidian Storage Unit");
		names.put(10, "Gold Storage Unit");
		names.put(12, "Diamond Storage Unit");
		names.put(14, "Emerald Storage Unit");
		names.put(16, "Iridium Storage Unit");
	}
	
	@Override
	public void registerIcons(IIconRegister ireg)
	{
		this.itemIcon = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitWood");
		
		ICON_STORAGERACK_WOOD =  this.itemIcon;
		ICON_STORAGERACK_STONE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitStone");
		ICON_STORAGERACK_IRON = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitIron");
		ICON_STORAGERACK_REDSTONE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitRedstone");
		ICON_STORAGERACK_OBSIDIAN = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitObsidian");
		ICON_STORAGERACK_GOLD = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitGold");
		ICON_STORAGERACK_DIAMOND = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitDiamond");
		ICON_STORAGERACK_EMERALD = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitEmerald");
		ICON_STORAGERACK_IRIDIUM = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "itemStorageUnitIridium");
		
		icons.put(0, ICON_STORAGERACK_WOOD);
		icons.put(2, ICON_STORAGERACK_STONE);
		icons.put(4, ICON_STORAGERACK_IRON);
		icons.put(6, ICON_STORAGERACK_REDSTONE);
		icons.put(8, ICON_STORAGERACK_OBSIDIAN);
		icons.put(10, ICON_STORAGERACK_GOLD);
		icons.put(12, ICON_STORAGERACK_DIAMOND);
		icons.put(14, ICON_STORAGERACK_EMERALD);
		icons.put(16, ICON_STORAGERACK_IRIDIUM);	
	}
	@Override
    public IIcon getIconFromDamage(int damage)
    {
        return icons.get(damage);
    }
	
	@Override
    public String getUnlocalizedName(ItemStack istack)
    {
        String name = names.get(istack.getItemDamage());
        
        if(name != null)
        {
        	return name;
        }

        return "ERROR";
        
    }
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) 
	{
		
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 4));
		list.add(new ItemStack(this, 1, 6));
		list.add(new ItemStack(this, 1, 8));
		list.add(new ItemStack(this, 1, 10));
		list.add(new ItemStack(this, 1, 12));
		list.add(new ItemStack(this, 1, 14));
		list.add(new ItemStack(this, 1, 16));
		
	}
	
	public String getName(int damage)
	{
        String name = names.get(damage);
        
        if(name != null)
        {
        	return name;
        }
        
        return "ERROR";
	}
	
	@Override
	public void addInformation(ItemStack istack, EntityPlayer player, List tooltip, boolean par4) 
	{
		//Storage Units
		if(istack.getItemDamage() >= 0 && istack.getItemDamage() <= 16)
		{
			if(istack.hasTagCompound())
			{
				if(istack.getTagCompound().hasKey("ContainerAmount"))
				{
					ItemStack contents = ItemStack.loadItemStackFromNBT(istack.getTagCompound());
					if(contents != null)
					{
						int amount = istack.getTagCompound().getInteger("ContainerAmount");
						
						tooltip.add(amount + " Units of " + contents.getDisplayName());
					}
				}
			}
			else
			{
				tooltip.add(TileEntityStorageRack.getMaxCapacity(istack.getItemDamage()) + " Unit Capacity");
				tooltip.add("Empty");
			}
		}
		
	}

}
