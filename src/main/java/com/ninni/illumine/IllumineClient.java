package com.ninni.illumine;

import com.ninni.illumine.client.IllumineParticles;
import com.ninni.illumine.client.particles.FireflyParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class IllumineClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(IllumineParticles.FIREFLY, FireflyParticle.Factory::new);
    }
}
