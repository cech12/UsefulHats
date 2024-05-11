package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.client.UsefulHatItemExtension;
import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

@Mixin(value = Item.class, remap = false)
public class ItemMixin {

    @Inject(at = @At("RETURN"), method = "initializeClient")
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer, CallbackInfo ci) {
        if (this instanceof IUsefulHatModelOwner) {
            consumer.accept(UsefulHatItemExtension.INSTANCE);
        }
    }

}
