package de.cech12.usefulhats.item;

import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractHatItem extends ArmorItem {

    private final int initColor;
    private final Supplier<Integer> durabilityConfig;
    protected final Supplier<Boolean> enabledDamageConfig;

    public AbstractHatItem(Holder<ArmorMaterial> material, int initColor, Supplier<Integer> durabilityConfig, Supplier<Boolean> enabledDamageConfig) {
        super(material, Type.HELMET, new Properties());
        this.initColor = initColor;
        this.durabilityConfig = durabilityConfig;
        this.enabledDamageConfig = enabledDamageConfig;
    }

    protected static int rawColorFromRGB(int red, int green, int blue) {
        return FastColor.ARGB32.color(red, green, blue);
    }

    public int getDurabilityFromConfig() {
        return this.durabilityConfig.get();
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    protected boolean isEffectCausedByOtherSource(LivingEntity entity, Holder<MobEffect> effect, int maxDuration, int amplifier) {
        //Effect source is not detected correctly here. Maybe later there is another possibility to detect it.
        MobEffectInstance effectInstance = entity.getEffect(effect);
        return (effectInstance != null && (effectInstance.isAmbient() || effectInstance.getDuration() >= maxDuration || effectInstance.getAmplifier() != amplifier));
    }

    protected void addEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier) {
        this.addEffect(entity, effect, duration, amplifier, false);
    }

    protected void addEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier, boolean showParticles) {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, showParticles, true));
    }

    /**
     * This is a helper method to remove an effect of a living entity with a maximal duration and a specific amplifier.
     * @param entity entity
     * @param effect effect object
     * @param maxDuration maximal effect duration
     * @param amplifier effect amplifier
     */
    protected void removeEffect(LivingEntity entity, Holder<MobEffect> effect, int maxDuration, int amplifier) {
        MobEffectInstance effectInstance = entity.getEffect(effect);
        if (effectInstance != null && !effectInstance.isAmbient() && effectInstance.getDuration() <= maxDuration && effectInstance.getAmplifier() == amplifier) {
            entity.removeEffect(effect);
        }
    }

    /**
     * Copy of {@link ItemStack#hurtAndBreak(int, ServerLevel, ServerPlayer, Consumer)} to enable own damaging of hat items.
     * Added config value to disable damage.
     */
    protected void damageHatItemByOne(ItemStack stack, LivingEntity entity) {
        if (!this.enabledDamageConfig.get()) return;

        if (!entity.level().isClientSide
                && entity.level() instanceof ServerLevel serverLevel
                && !(entity instanceof ServerPlayer player && player.getAbilities().instabuild)
                && stack.isDamageableItem()
        ) {
            stack.hurtAndBreak(1, serverLevel, entity instanceof ServerPlayer ? (ServerPlayer)entity : null, (item) ->  {
                entity.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                stack.shrink(1);
                if (entity instanceof ServerPlayer player) {
                    player.awardStat(Stats.ITEM_BROKEN.get(this));
                }
                stack.setDamageValue(0);
            });
        }
    }

    /**
     * Disables "When on head" line of ArmorItem Tooltip
     */
    @Override
    @NotNull
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return ItemAttributeModifiers.EMPTY;
    }

    /**
     * Adds "When on head" line to end of tooltip.
     * When hat item has no effect, override this method with an empty method.
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        //tooltip.add(new TextComponent("Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()).withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal(""));
        tooltip.add((Component.translatable("item.modifiers." + EquipmentSlot.HEAD.getName())).withStyle(ChatFormatting.GRAY));
    }

    public int getDefaultColor() {
        return initColor;
    }

    /**
     * @return true, if this hat has another texture at christmastime.
     */
    public boolean hasChristmasVariant() {
        return false;
    }

    /*
     * FORGE & NEOFORGE SPECIFIC METHODS
     */

    //@Override //overrides interface method of Neoforge
    public int getMaxDamage(ItemStack stack) {
        return this.getDurabilityFromConfig();
    }

    //@Override //overrides interface method of Forge & Neoforge //Fabric has its own renderer
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean inner) {
        return AbstractUsefulHatsRenderer.getArmorTexture((ArmorItem) stack.getItem(), layer);
    }

}
