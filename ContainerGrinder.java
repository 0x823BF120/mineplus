package mineplus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGrinder extends Container {
	private TileEntityGrinder highSpeed;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerGrinder(InventoryPlayer InventoryPlayer, TileEntityGrinder TileEntityHighSpeedFurnace) {
		this.highSpeed = TileEntityHighSpeedFurnace;
		this.addSlotToContainer(new Slot(TileEntityHighSpeedFurnace, 0, 56, 17));
		this.addSlotToContainer(new Slot(TileEntityHighSpeedFurnace, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(InventoryPlayer.player, TileEntityHighSpeedFurnace, 2, 116, 35));
		int var3;
		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(InventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.highSpeed.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.highSpeed.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.highSpeed.currentItemBurnTime);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting) this.crafters.get(var1);
			if (this.lastCookTime != this.highSpeed.furnaceCookTime) {
				var2.sendProgressBarUpdate(this, 0, this.highSpeed.furnaceCookTime);
			}
			if (this.lastBurnTime != this.highSpeed.furnaceBurnTime) {
				var2.sendProgressBarUpdate(this, 1, this.highSpeed.furnaceBurnTime);
			}
			if (this.lastItemBurnTime != this.highSpeed.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.highSpeed.currentItemBurnTime);
			}
		}
		this.lastCookTime = this.highSpeed.furnaceCookTime;
		this.lastBurnTime = this.highSpeed.furnaceBurnTime;
		this.lastItemBurnTime = this.highSpeed.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.highSpeed.furnaceCookTime = par2;
		}
		if (par1 == 1) {
			this.highSpeed.furnaceBurnTime = par2;
		}
		if (par1 == 2) {
			this.highSpeed.currentItemBurnTime = par2;
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.highSpeed.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if (par2 == 2) {
				if (!this.mergeItemStack(var5, 3, 39, true)) {
					return null;
				}
				var4.onSlotChange(var5, var3);
			} else if (par2 != 1 && par2 != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null) {
					if (!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if (TileEntityGrinder.isItemFuel(var5)) {
					if (!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if (par2 >= 3 && par2 < 30) {
					if (!this.mergeItemStack(var5, 30, 39, false)) {
						return null;
					}
				} else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(var5, 3, 39, false)) {
				return null;
			}
			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
			if (var5.stackSize == var3.stackSize) {
				return null;
			}
			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}
		return var3;
	}
}