package cech12.usefulhats.helper;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class UsefulHatsRecipeSerializers {

    @SubscribeEvent
    public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CraftingHelper.register(EnabledCondition.Serializer.INSTANCE);
    }

}
