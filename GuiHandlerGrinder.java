package mineplus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerGrinder implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new ContainerGrinder(player.inventory, (TileEntityGrinder) tile_entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
		switch (id) {
		case 0:
			return new GuiGrinder(player.inventory, (TileEntityGrinder) tile_entity);
		}
		return null;
	}
}