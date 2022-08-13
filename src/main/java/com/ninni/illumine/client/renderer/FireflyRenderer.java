package com.ninni.illumine.client.renderer;

import com.ninni.illumine.Illumine;
import com.ninni.illumine.entity.FireflyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FireflyRenderer<T extends FireflyEntity> extends EntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Illumine.MOD_ID, "textures/particle/firefly_lit.png");

    public FireflyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowOpacity = 0.0F;
        this.shadowRadius = 0.0F;
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
