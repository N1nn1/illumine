package com.ninni.illumine.client;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.illumine.Illumine.MOD_ID;

public class IllumineParticles {

    public static final DefaultParticleType FIREFLY = Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "firefly"), FabricParticleTypes.simple());

}
