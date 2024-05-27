package de.cech12.usefulhats.item;

import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class ShulkerHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int LEVITATION_DURATION = 219;

    public ShulkerHelmetItem() {
        super(HatArmorMaterials.SHULKER, rawColorFromRGB(150, 105, 150), Services.CONFIG::getShulkerHelmetDurability, Services.CONFIG::isShulkerHelmetDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        int enchantmentLevel = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) + 1;
        tooltip.add(Component.translatable("item.usefulhats.shulker_helmet.desc.levitation", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int levitationAmplifier = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            if (!this.isEffectCausedByOtherSource(livingEntity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier)) {
                if (livingEntity.getEffect(MobEffects.LEVITATION) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
                }
                //calculate damage if levitation is caused by this item
                if (livingEntity.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, livingEntity);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int levitationAmplifier = Services.PLATFORM.getEnchantmentLevel(oldStack, Enchantments.EFFICIENCY);
        this.removeEffect(entity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
    }

}
