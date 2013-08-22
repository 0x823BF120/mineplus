package mineplus;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MinePlusItem extends Item{

	public MinePlusItem(int id) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

}