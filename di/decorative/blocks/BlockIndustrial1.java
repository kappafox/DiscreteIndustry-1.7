package kappafox.di.decorative.blocks;

import java.util.List;

import javax.swing.Icon;

import kappafox.di.DiscreteIndustry;
import kappafox.di.decorative.tileentities.TileEntityHazardBlock;
import kappafox.di.decorative.tileentities.TileEntityStripHazardBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIndustrial1 extends Block
{
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] icons;

	
	public BlockIndustrial1(Material mat)
	{
		super(mat);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("industrialBlock1");
		this.setHardness(1.0F);
		this.setResistance(150.0F);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			 icons = new IIcon[16];
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{	
		super.registerBlockIcons(ireg);
		icons[0] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_0");
		icons[1] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_1");
		icons[2] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_2");
		icons[3] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_3");
		icons[4] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_4");
		icons[5] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_5");
		icons[6] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_6");
		icons[7] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_7");
		icons[8] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_8");
		icons[9] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_9");
		icons[10] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_10");
		icons[11] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_11");
		icons[12] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_12");
		icons[13] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_13");
		icons[14] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_14");
		icons[15] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_15");
		
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// block id, count, meta	
		
		for(int i = 0; i < 16; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}
}
