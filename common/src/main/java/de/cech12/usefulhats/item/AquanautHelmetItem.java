package de.cech12.usefulhats.item;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener, IGameOverlayRenderer {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = new ResourceLocation(Constants.MOD_ID, "textures/misc/aquanautblur.png");

    public AquanautHelmetItem() {
        super(HatArmorMaterials.AQUANAUT, rawColorFromRGB(71, 191, 74), Services.CONFIG::getAquanautHelmetDurability, Services.CONFIG::isAquanautHelmetDamageEnabled);
    }

    private int getConduitPowerDuration(ItemStack stack) {
        return Services.CONFIG.getAquanautHelmetEffectTimeWithEfficiency(Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.EFFICIENCY)) * 20;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        int effectTime = Services.CONFIG.getAquanautHelmetEffectTimeWithEfficiency(Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.EFFICIENCY));
        tooltip.add(Component.translatable("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
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

    @Override
    public void onRenderGameOverlay(GuiGraphics guiGraphics, float partialTicks) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(AQUANAUT_GUI_TEX_PATH, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
