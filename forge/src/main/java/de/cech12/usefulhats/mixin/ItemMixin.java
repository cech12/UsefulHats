package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.client.UsefulHatItemExtension;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = Item.class, remap = false)
public class ItemMixin {

    @Inject(at = @At("RETURN"), method = "initializeClient")
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer, CallbackInfo ci) {
        if ((Object) this instanceof AbstractHatItem) {
            consumer.accept(UsefulHatItemExtension.INSTANCE);
        }
    }

}
