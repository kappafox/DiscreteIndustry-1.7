





package kappafox.di.base.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteBlock extends Block
{
	protected boolean isTileProvider = true;
	protected int renderType;
	protected boolean lockRefresh;
	
	protected IIcon[] textures = new IIcon[6];
	protected IIcon[] icons = new IIcon[6];
	
	protected IIcon defaultIcon;
	
	
	public BlockDiscreteBlock(Material mat, int renderID)
	{
		super(mat);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("discreteCable");
		this.setHardness(3.0F);
		this.setResistance(150.0F);
		this.renderType = renderID;
		this.isTileProvider = true;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{	
		
		defaultIcon = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	
		for(int i = 0; i < 6; i++)
		{
			textures[i] = defaultIcon;
			icons[i] = defaultIcon;
		}
		
		this.blockIcon = icons[0];
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	//public void getSubBlocks(int id, CreativeTabs tabs, List list)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta]; 
	}
	
	
	/*
	//@SideOnly(Side.CLIENT)
	private boolean refreshTextures(IBlockAccess ibaccess, int xcoord, int ycoord, int zcoord, int side)
	{
		
		if(lockRefresh == true)
		{
			return true;
		}
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)ibaccess.getTileEntity(xcoord, ycoord, zcoord);
		
		
		if(tile != null)
		{
			int face = tile.getFace();
					
			//higher blockids are reserved for items
			Block inHand;
			int item = tile.getTextureSource(side);
			try
			{
				inHand = Block.blocksList[item];
			}
			catch(Exception e)
			{
				return true;
			}
			

			//grab all the textures fresh;
			
			//if(inHand != null)
			//{
			
			if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
			{
				for(int i = 0; i < 6; i++)
				{
					textures[i] = inHand.getIcon(i, tile.getTextureSourceMeta(side));
				}
				
				Icon oldFace = textures[3];
				textures[3] = textures[face];
				textures[face] = oldFace;
				
			}
			
		}
		
		return true;
	}
	*/
	
	@Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
		/*
    	refreshTextures(ibaccess, xcoord, ycoord, zcoord, side);
    	
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)ibaccess.getTileEntity(xcoord, ycoord, zcoord);
    	
    	if(tile != null && tile.getTextureSource() == this.blockID)
    	{
    		return icons[0];
    	}
    	else
    	{
    		return textures[side];
    	}
    	*/
		
    	//refreshTextures(ibaccess, xcoord, ycoord, zcoord, side);
    	//int id = world.getBlock(x, y, z);
    	//int meta = world.getBlockMetadata(x, y, z);
		
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		
    		/*
	    	if(tile.getTextureSource(side) == id)
	    	{
	    		
	    		System.out.println("match!" + id + "\t" + Block.blocksList[this.blockID].getIcon(side, meta).getIconName());
	    		//return textures[0];
	    		return Block.blocksList[this.blockID].getIcon(side, meta);
	    		
	    	}
	    	else
	    	{
	    	*/
	    		Block target = tile.getTextureSourceBlock(side);
	    		
	    		if(target != null && tile.getBlockTexture(side) != null)
	    		{
	    			return tile.getBlockTexture(side);
	    		}


	    	//}
    	}
    	//return this.getIcon(side, ibaccess.getBlockMetadata(x, y, z));
    	return textures[0];
    }
	
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack istack)
    {
		//thankyou mojang <3
		int magic = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		short dir = 3;
	
		if(tile != null)
		{
			switch(magic)
			{
				case 0:
				{
					dir = 2;
					break;
				}
				
				case 2:
				{
					dir = 3;
					break;
				}
				
				case 3:
				{
					dir = 4;
					break;
				}
				
				case 1:
				{
					dir = 5;
					break;
				}
			}
		
		tile.setFace(dir);
		tile.setTextureOrientation(dir);
		}
    }
    
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return isTileProvider;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
    	
        if(isTileProvider)
        {
        	TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock(this, metadata);
            return tile;
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock( )
    {
    	return true;
    }
    
    @Override
    public boolean isOpaqueCube( )
    {
    	return true;
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType( )
    {
    	return renderType;
    }

    /*
	@Override
    public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
    {	
		
		
		//change in 1.0.2 to make blocks only change when sneaking
		if(player.isSneaking() == false)
		{
			return false;
		}
		
		
		//System.out.println("ENTRY");
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(xcoord, ycoord, zcoord);

		
		if(tile == null)
		{
			//System.out.println("NULL TILE");
			return false;
		}
		
		ItemStack item = player.inventory.getCurrentItem();
		Block inHand = null;
		
		//higher blockids are reserved for items
		try
		{
			inHand = Block.blocksList[item.itemID];
		}
		catch(Exception e)
		{
			//System.out.println("EXCEPTION");
			return false;
		}
		
		if(item != null && canUseTexture(inHand) == true)
		{			
			
			//client only for .getIcon()
			if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
			{
				for(int i = 0; i < 6; i++)
				{
					//System.out.println("TEXTURE SET");
					textures[i] = inHand.getIcon(i, item.getItemDamage());
				}
			}
			
			tile.setTextureSource(item.itemID);
			tile.setTextureSourceMeta(item.getItemDamage());
			tile.setFace((short)side);
			world.markBlockForUpdate(xcoord, ycoord, zcoord);
			return true;
		}
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			System.out.println("Clicked!");
		}
        return false;
        
    }
    */
    
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
    	return new ItemStack(Items.apple);
    }
	
	
	/*
	private boolean canUseTexture(Block target)
	{
		if(target != null)
		{
			if(target.renderAsNormalBlock() && target.blockID != this.blockID)
			{
				return true;
			}
		}
			
			//item != null && inHand.isNormalCube(item.itemID) == true && item.itemID != this.blockID
		
		return false;
	}
	*/
	
	
	/*
    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
		if(tile == null)
		{
			return 16777215;
		}
		else
		{
			if(tile.getTextureSource())
			return Block.blocksList[tile.getTextureSource(0)].colorMultiplier(world, x, y, z);
		}		
        
    }
    */

}
