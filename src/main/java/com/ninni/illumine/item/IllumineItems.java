package com.ninni.illumine.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static com.ninni.illumine.Illumine.MOD_ID;

@SuppressWarnings("unused")
public class IllumineItems {
    public static final Item ILLUMINE = register("illumine", new Item(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }
}
