package kappafox.di.transport.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kappafox.di.DiscreteIndustry;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import kappafox.di.transport.tileentities.TileEntityDustUnifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDustUnifier extends BlockContainer
{
	
	private static IIcon top;
	private static IIcon side;
	private static final Random rnd = new Random();
	
	public BlockDustUnifier(Material mat) 
	{
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ireg)
    {		
		this.side = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "dustUnifier_side");
        this.top = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "BlockIndustrial1/industrialBlock1_6");     
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side == 1 || side == 0)
		{
			return top;
		}
		
        return this.side;
    }
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
		return new TileEntityDustUnifier();
    }
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
    {
		TileEntityDustUnifier tile = (TileEntityDustUnifier)world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			player.openGui(DiscreteIndustry.instance, DiscreteIndustry.GID_DUST_UNIFIER, world, x, y, z);
		}
		
		return true;
    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        TileEntityDustUnifier tileentityhopper = (TileEntityDustUnifier)par1World.getTileEntity(par2, par3, par4);

        if (tileentityhopper != null)
        {
            for (int j1 = 0; j1 < tileentityhopper.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = tileentityhopper.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.rnd.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rnd.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rnd.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int k1 = this.rnd.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.rnd.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.rnd.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.rnd.nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }

            par1World.func_147453_f(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
