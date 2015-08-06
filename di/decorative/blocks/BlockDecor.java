package kappafox.di.decorative.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;

import kappafox.di.base.blocks.BaseBlockDiscreteSubtype;
import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.base.blocks.ISubItemRangeProvider;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.blocks.SubBlockDummy;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BaseBlockDiscreteSubtype implements ISubItemRangeProvider
{
	public static final short META_LADDER = 2;
	public static final short META_SWORDRACK = 4;
	public static final short META_STAIRS = 5;
	public static final short META_STRUT = 6;
	public static final short META_SHAPE = 7;
	public static final short META_WALL = 8;
	public static final short META_BRIDGE = 9;
	
	public static final short ID_LADDER_FOOTHOLD = 800;
	public static final short ID_LADDER_POLE = 801;
	public static final short ID_LADDER_SIMPLE = 802;
	public static final short ID_LADDER_ROPE = 803;
	public static final short ID_LADDER_FIXED = 804;
	public static final short ID_LADDER_CLASSIC = 806;
	public static final short ID_LADDER_INDUSTRIAL = 807;
	
	public static final short ID_RACK_SWORDREST = 821;
	public static final short ID_RACK_SWORDRACK = 822;
	
	public static final short ID_STAIRS_NORMAL = 861;
	public static final short ID_STAIRS_SMALL = 862;
	
	public static final short ID_STRUT_2X2 = 871;
	public static final short ID_STRUT_4X4 = 872;
	public static final short ID_STRUT_6X6 = 873;
	
	public static final short ID_SHAPE_SLAB = 881;
	
	public static final short ID_WALL_DISCRETE = 900;
	public static final short ID_WALL_RAILING_SIMPLE = 910;
	public static final short ID_WALL_RAILING_DOUBLE = 911;
	public static final short ID_WALL_RAILING_TRIPLE = 912;
	public static final short ID_WALL_RAILING_SQUARE = 913;
	public static final short ID_WALL_RAILING_SQUARE_HALVED = 914;
	public static final short ID_WALL_RAILING_SQUARE_QUARTERED = 915;
	public static final short ID_WALL_DANGER_TAPE = 916;
	public static final short ID_WALL_PANEL_SQUARE = 950;
	public static final short ID_WALL_PANEL_SQUARE_HALVED = 951;
	public static final short ID_WALL_PANEL_SQUARE_QUARTERED = 952;
	
	public static final short ID_BRIDGE_CATWALK_SIMPLE = 1000;

	public static final Range<Integer> RANGE_LADDER = Range.closed(800, 820);
	public static final Range<Integer> RANGE_RACK = Range.closed(821, 840);
	public static final Range<Integer> RANGE_STAIRS = Range.closed(861, 870);
	public static final Range<Integer> RANGE_STRUT = Range.closed(871, 880);
	public static final Range<Integer> RANGE_SHAPE = Range.closed(881, 890);
	public static final Range<Integer> RANGE_WALL = Range.closed(900, 999);
	public static final Range<Integer> RANGE_BRIDGE = Range.closed(1000, 1100);
	
	@SideOnly(Side.CLIENT)
	private int rid;
	
	public BlockDecor(Material mat, int renderID)
	{
		super(mat, renderID);
		
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("decorBlock");
		this.setHardness(3.0F);

		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			rid = renderID;
		}
		
		super.registerSubBlock((int)META_LADDER, RANGE_LADDER, new SubBlockLadder());
		super.registerSubBlock((int)META_SWORDRACK, RANGE_RACK, new SubBlockSwordRack());
		super.registerSubBlock((int)META_STAIRS, RANGE_STAIRS, new SubBlockStairs());
		super.registerSubBlock((int)META_STRUT, RANGE_STRUT, new SubBlockStrut());
		super.registerSubBlock((int)META_SHAPE, RANGE_SHAPE, new SubBlockShape());
		super.registerSubBlock((int)META_WALL, RANGE_WALL, new SubBlockWall());
		super.registerSubBlock(META_BRIDGE, RANGE_BRIDGE, new SubBlockBridge());
	}
	
    @Override
    public boolean isNormalCube( )
    {
    	return false;
    }
}
