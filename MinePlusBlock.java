package mineplus;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class MinePlusBlock extends Block {
	public MinePlusBlock(int id, Material material) {
		super(id, material);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3F);
	}
}
