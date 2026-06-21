package de.cech12.usefulhats.client.compat;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
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
        public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
            this.render(stack, (HumanoidRenderState)state, poseStack, submit, light, (EntityModel<HumanoidRenderState>)contextModel);
        }
    }

}
 */
