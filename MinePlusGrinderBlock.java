package mineplus;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MinePlusGrinderBlock extends BlockContainer {
	private Random highSpeedRand = new Random();
	private final boolean isActive;
	private static boolean keephighSpeedInventory = false;
	@SideOnly(Side.CLIENT)
	private Icon furnaceIconTop;
	@SideOnly(Side.CLIENT)
	private Icon furnaceIconFront;

	public MinePlusGrinderBlock(int id, boolean active) {
		super(id, Material.rock);
		this.isActive = active;
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public int getRenderType() {
		return 2105;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3) {
		return MinePlus.grinder.blockID;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}

	/**
	 * set a blocks direction
	 */
	private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			int l = par1World.getBlockId(par2, par3, par4 - 1);
			int i1 = par1World.getBlockId(par2, par3, par4 + 1);
			int j1 = par1World.getBlockId(par2 - 1, par3, par4);
			int k1 = par1World.getBlockId(par2 + 1, par3, par4);
			byte b0 = 3;
			if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) {
				b0 = 3;
			}
			if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) {
				b0 = 2;
			}
			if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) {
				b0 = 5;
			}
			if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) {
				b0 = 4;
			}
			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2) {
		return par1 == 1 ? this.furnaceIconTop : (par1 == 0 ? this.furnaceIconTop : (par1 != par2 ? this.blockIcon : this.furnaceIconFront));
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("HighSpeed:extruderBack");
		this.furnaceIconFront = par1IconRegister.registerIcon(this.isActive ? "HighSpeed:extruderActive" : "HighSpeed:extruderIdle");
		this.furnaceIconTop = par1IconRegister.registerIcon("HighSpeed:extruderTop");
	}

	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (this.isActive) {
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			float var7 = (float) par2 + 0.5F;
			float var8 = (float) par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float var9 = (float) par4 + 0.5F;
			float var10 = 0.52F;
			float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
			if (var6 == 4) {
				par1World.spawnParticle("smoke", (double) (var7 - var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 - var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 5) {
				par1World.spawnParticle("smoke", (double) (var7 + var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 2) {
				par1World.spawnParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 - var10), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 - var10), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 3) {
				par1World.spawnParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 + var10), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 + var10), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if (par1World.isRemote) {
			return true;
		} else if (!par5EntityPlayer.isSneaking()) {
			TileEntityGrinder var10 = (TileEntityGrinder) par1World.getBlockTileEntity(par2, par3, par4);
			if (var10 != null) {
				par5EntityPlayer.openGui(MinePlus.instance, 0, par1World, par2, par3, par4);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Update which block ID the furnace is using depending on whether or not it is burning
	 */
	public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4) {
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
		keephighSpeedInventory = true;
		if (par0) {
			par1World.setBlock(par2, par3, par4, MinePlus.grinderActive.blockID);
		} else {
			par1World.setBlock(par2, par3, par4, MinePlus.grinder.blockID);
		}
		keephighSpeedInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);

		if (var6 != null) {
			var6.validate();
			par1World.setBlockTileEntity(par2, par3, par4, var6);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntityGrinder();
	}

	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int l = MathHelper.floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (l == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}
		if (l == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}
		if (l == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}
		if (l == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
		if (par6ItemStack.hasDisplayName()) {
			((TileEntityGrinder) par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
		}
	}

	/**
	 * ejects contained items into the world, and notifies neighbors of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		if (!keephighSpeedInventory) {
			TileEntityGrinder var7 = (TileEntityGrinder) par1World.getBlockTileEntity(par2, par3, par4);
			if (var7 != null) {
				for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
					ItemStack itemstack = var7.getStackInSlot(var8);
					if (itemstack != null) {
						float f = this.highSpeedRand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.highSpeedRand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.highSpeedRand.nextFloat() * 0.8F + 0.1F;
						while (itemstack.stackSize > 0) {
							int k1 = this.highSpeedRand.nextInt(21) + 10;
							if (k1 > itemstack.stackSize) {
								k1 = itemstack.stackSize;
							}
							itemstack.stackSize -= k1;
							EntityItem entityitem = new EntityItem(par1World, (double) ((float) par2 + f), (double) ((float) par3 + f1), (double) ((float) par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
							if (itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							float f3 = 0.05F;
							entityitem.motionX = (double) ((float) this.highSpeedRand.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.highSpeedRand.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double) ((float) this.highSpeedRand.nextGaussian() * f3);
							par1World.spawnEntityInWorld(entityitem);
						}
					}
				}
				par1World.func_96440_m(par2, par3, par4, par5);
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public boolean hasComparatorInputOverride() {
		return true;
	}

	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
		return Container.calcRedstoneFromInventory((IInventory) par1World.getBlockTileEntity(par2, par3, par4));
	}

}