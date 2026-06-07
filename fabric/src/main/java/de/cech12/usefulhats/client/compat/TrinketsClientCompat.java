package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TrinketsClientCompat {

    private TrinketsClientCompat() {}

    public static void register() {
        UsefulHatsTrinketRenderer renderer = new UsefulHatsTrinketRenderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            TrinketRendererRegistry.registerRenderer(item, renderer);
        }
    }

    private static class UsefulHatsTrinketRenderer extends AbstractUsefulHatsRenderer implements TrinketRenderer {

        @Override
        public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntityRenderState> entityModel, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, LivingEntityRenderState livingEntityRenderState, float v, float v1) {
            this.render(itemStack, (HumanoidRenderState)livingEntityRenderState, poseStack, submitNodeCollector, i, (EntityModel<HumanoidRenderState>)entityModel);
        }
    }

}
