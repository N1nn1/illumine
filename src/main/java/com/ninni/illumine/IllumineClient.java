package com.ninni.illumine;

import com.ninni.illumine.client.IllumineParticles;
import com.ninni.illumine.client.particles.FireflyParticle;
import com.ninni.illumine.client.renderer.FireflyRenderer;
import com.ninni.illumine.entity.IllumineEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class IllumineClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(IllumineParticles.FIREFLY, FireflyParticle.Factory::new);
        EntityRendererRegistry.register(IllumineEntities.FIREFLY, FireflyRenderer::new);
    }
}
