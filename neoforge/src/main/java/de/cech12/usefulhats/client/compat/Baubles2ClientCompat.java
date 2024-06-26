package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tld.unknown.baubles.api.BaubleType;
import tld.unknown.baubles.api.IBaubleRenderer;
import tld.unknown.baubles.client.BaublesClient;

public class Baubles2ClientCompat {

    private Baubles2ClientCompat() {}

    public static void register() {
        UsefulHatsBaubles2Renderer renderer = new UsefulHatsBaubles2Renderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            BaublesClient.RENDERERS.registerRenderer(BuiltInRegistries.ITEM.getKey(item), renderer);
        }
    }

    private static class UsefulHatsBaubles2Renderer extends AbstractUsefulHatsRenderer implements IBaubleRenderer {

        @Override
        public void renderHead(PoseStack matrices, MultiBufferSource vertexConsumers, int light, float deltaTick, Player entity, ItemStack stack, BaubleType slot) {
            if (slot != BaubleType.HEAD) {
                return;
            }
            matrices.popPose(); //pop head translated pose
            this.render(stack, matrices, vertexConsumers, light, entity);
            matrices.pushPose(); //push empty pose to be compatible with calling context
        }

        @Override
        protected void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
            if (Minecraft.getInstance().player != null) {
                ((HumanoidModel)((RenderLayerParent)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity)).getModel()).copyPropertiesTo(humanoidModel);
            }
        }
    }

}
