package kappafox.di.electrics.items;

import kappafox.di.DiscreteIndustry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHorseInspector extends Item
{
	public ItemHorseInspector(String name, int maxStack)
	{
		super();
		this.setUnlocalizedName(name);
		this.setMaxStackSize(maxStack);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register_)
    {	
        this.itemIcon = register_.registerIcon(DiscreteIndustry.MODID + ":" + "itemHorseInspector");
    }

	
	

}
