package de.cech12.usefulhats.item;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = Constants.id("misc/aquanautblur");

    public AquanautHelmetItem(String name) {
        super(name, new Properties().humanoidArmor(HatArmorMaterials.AQUANAUT, ArmorType.HELMET)
                        .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setEquipSound(HatArmorMaterials.AQUANAUT.equipSound()).setAsset(HatArmorMaterials.AQUANAUT.assetId()).setDamageOnHurt(false).setCameraOverlay(AQUANAUT_GUI_TEX_PATH).build())
                        .component(DataComponents.TOOLTIP_DISPLAY, createTooltipDisplay()),
                Services.CONFIG::getAquanautHelmetDurability, Services.CONFIG::isAquanautHelmetDamageEnabled);
    }

    private int getConduitPowerDuration(ItemStack stack) {
        return Services.CONFIG.getAquanautHelmetEffectTimeWithEfficiency(CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY)) * 20;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, display, tooltip, flagIn);
        int effectTime = Services.CONFIG.getAquanautHelmetEffectTimeWithEfficiency(CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY));
        tooltip.accept(Component.translatable("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int maxDuration = this.getConduitPowerDuration(stack);
            //When Conduit Power effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(livingEntity, MobEffects.CONDUIT_POWER, maxDuration, 0))
                return;

            if (!Services.REGISTRY.areEntityEyesInDrownableFluid(livingEntity)) {
                this.addEffect(livingEntity, MobEffects.CONDUIT_POWER, maxDuration, 0);
            } else {
                if (livingEntity.getEffect(MobEffects.CONDUIT_POWER) != null && livingEntity.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, livingEntity);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        if (!entity.level().isClientSide) {
            if (Services.REGISTRY.getEquippedHatItemStacks(entity).stream().anyMatch(stack -> stack.getItem() == this)) return;
            // disable effects when hat is removed from slot
            this.removeEffect(entity, MobEffects.CONDUIT_POWER, this.getConduitPowerDuration(oldStack), 0);
        }
    }

}
