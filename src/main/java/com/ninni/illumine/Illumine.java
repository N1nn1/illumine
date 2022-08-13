package com.ninni.illumine;

import com.google.common.reflect.Reflection;
import com.ninni.illumine.item.IllumineItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Illumine implements ModInitializer {
	public static final String MOD_ID = "illumine";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(IllumineItems.ILLUMINE));

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Reflection.initialize(
				IllumineItems.class
		);
	}
}
