package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.config.Config;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import java.util.List;

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = new ResourceLocation(UsefulHatsMod.MOD_ID, "textures/misc/aquanautblur.png");

    public AquanautHelmetItem() {
        super("aquanaut_helmet", HatArmorMaterial.AQUANAUT, rawColorFromRGB(71, 191, 74), Config.AQUANAUT_HELMET_ENABLED, Config.AQUANAUT_HELMET_DAMAGE_ENABLED);
    }

    private int getEffectTimeConfig(final int enchantmentLevel) {
        if (enchantmentLevel <= 0) {
            return Config.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0.getValue();
        }
        switch (enchantmentLevel) {
            case 1: return Config.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1.getValue();
            case 2: return Config.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2.getValue();
            default: return Config.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3.getValue();
        }
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int effectTime = this.getEffectTimeConfig(EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack));
        //tooltip.add(new TranslationTextComponent("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).func_240699_a_(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        int maxDuration = this.getEffectTimeConfig(EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack)) * 20;
        // only get damage, when the effect is active and duration is below the max duration
        // (other sources can produce this effect with higher duration)
        // TODO detect effect from other source (also for onItemRemoved)
        EffectInstance conduitPowerEffect = player.getActivePotionEffect(Effects.CONDUIT_POWER);
        if (conduitPowerEffect != null && (conduitPowerEffect.isAmbient() || conduitPowerEffect.getDuration() >= maxDuration)) return;
        if (!player.areEyesInFluid(FluidTags.WATER)) {
            player.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, maxDuration, 0, false, false, true));
        } else {
            if (random.nextInt(20) == 0) {
                this.damageHatItemByOne(stack, player);
            }
        }
    }

    @Override
    public void onEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            // disable effects when hat is removed from slot
            ItemStack oldItemStack = event.getFrom();
            ItemStack newItemStack = event.getTo();
            if (oldItemStack.getItem() == this && newItemStack.getItem() != this) {
                int maxDuration = (EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, oldItemStack) + 1) * 1200;
                EffectInstance conduitPowerEffect = player.getActivePotionEffect(Effects.CONDUIT_POWER);
                if (conduitPowerEffect != null && !conduitPowerEffect.isAmbient() && conduitPowerEffect.getDuration() <= maxDuration) {
                    player.removePotionEffect(Effects.CONDUIT_POWER);
                }
            }
        }
    }

    @Override
    public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {
        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //GlStateManager.disableAlphaTest();
        Minecraft.getInstance().getTextureManager().bindTexture(AQUANAUT_GUI_TEX_PATH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, height, -90.0D).tex(0.0f, 1.0f).endVertex();
        bufferbuilder.pos(width, height, -90.0D).tex(1.0f, 1.0f).endVertex();
        bufferbuilder.pos(width, 0.0D, -90.0D).tex(1.0f, 0.0f).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0f, 0.0f).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        //GlStateManager.enableAlphaTest();
        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
