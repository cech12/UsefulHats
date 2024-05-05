package de.cech12.usefulhats.client;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;

public class UsefulHatsFabricClientMod implements ClientModInitializer {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(CommonLoader.id("usefulhat_layer"), "main");

    private static UsefulHatModel<LivingEntity> usefulHatModel = null;

    @Override
    public void onInitializeClient() {
        //register entity model layer
        EntityModelLayerRegistry.registerModelLayer(USEFUL_HAT_LAYER, () -> UsefulHatModel.createLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0));
        ModItems.ALL_HATS.forEach(item -> {
            //register item colors
            ColorProviderRegistry.ITEM.register((itemStack, layer) -> layer > 0 ? -1 : ((DyeableLeatherItem)(itemStack.getItem())).getColor(itemStack), item);
            //register armor renderer
            if (item instanceof IUsefulHatModelOwner) {
                ArmorRenderer.register(new UsefulHatsArmorRenderer(), item);
            }
        });
    }

    public static void resetUsefulHatModel() {
        usefulHatModel = new UsefulHatModel<>(net.minecraft.client.Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
    }

    public static UsefulHatModel<LivingEntity> getUsefulHatModel() {
        if (usefulHatModel == null) {
            resetUsefulHatModel();
        }
        return usefulHatModel;
    }

}
