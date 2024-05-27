package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class MushroomHatItem extends AbstractHatItem {

    public MushroomHatItem() {
        super(HatArmorMaterials.MUSHROOM, rawColorFromRGB(197, 35, 35), Services.CONFIG::getMushroomHatDurability, Services.CONFIG::isMushroomHatDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.mushroom_hat.desc.feeding").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //eat every 3 seconds until no food is needed
            FoodData foodStats = player.getFoodData();
            if (foodStats.needsFood() && player.tickCount % Services.CONFIG.getMushroomHatEatInterval() == 0) {
                foodStats.eat(1, 0.5F);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                this.damageHatItemByOne(stack, player);
            }
        }
    }
}
