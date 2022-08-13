package com.ninni.illumine.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.illumine.Illumine.ITEM_GROUP;
import static com.ninni.illumine.Illumine.MOD_ID;

public class IllumineEntities {

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType, int[] spawnEggColors) {
        if (spawnEggColors != null)
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, id + "_spawn_egg"), new SpawnEggItem((EntityType<? extends MobEntity>) entityType, spawnEggColors[0], spawnEggColors[1], new Item.Settings().maxCount(64).group(ITEM_GROUP)));

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, id), entityType);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, int[] spawnEggColors) {
        return register(id, entityType.build(), spawnEggColors);
    }

}
