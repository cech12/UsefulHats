package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner, IGameOverlayRenderer {

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

    private int getConduitPowerDuration(ItemStack stack) {
        return this.getEffectTimeConfig(EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack)) * 20;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int effectTime = this.getEffectTimeConfig(EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack));
        tooltip.add(new TranslationTextComponent("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int maxDuration = this.getConduitPowerDuration(stack);
            //When Conduit Power effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, Effects.CONDUIT_POWER, maxDuration, 0))
                return;

            if (!player.areEyesInFluid(FluidTags.WATER)) {
                this.addEffect(player, Effects.CONDUIT_POWER, maxDuration, 0);
            } else {
                if (player.getActivePotionEffect(Effects.CONDUIT_POWER) != null && random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, Effects.CONDUIT_POWER, this.getConduitPowerDuration(oldStack), 0);
    }

    //to support other apis do not use this method
    //@Override
    //public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRenderGameOverlay(int width, int height, float partialTicks) {
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
