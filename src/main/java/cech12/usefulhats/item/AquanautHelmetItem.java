package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.tags.FluidTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner, IGameOverlayRenderer {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = new ResourceLocation(UsefulHatsMod.MOD_ID, "textures/misc/aquanautblur.png");

    public AquanautHelmetItem() {
        super(HatArmorMaterial.AQUANAUT, rawColorFromRGB(71, 191, 74), ServerConfig.AQUANAUT_HELMET_DAMAGE_ENABLED);
    }

    private int getEffectTimeConfig(final int enchantmentLevel) {
        if (enchantmentLevel <= 0) {
            return ServerConfig.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0.get();
        }
        switch (enchantmentLevel) {
            case 1: return ServerConfig.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1.get();
            case 2: return ServerConfig.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2.get();
            default: return ServerConfig.AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3.get();
        }
    }

    private int getConduitPowerDuration(ItemStack stack) {
        return this.getEffectTimeConfig(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.RESPIRATION, stack)) * 20;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int effectTime = this.getEffectTimeConfig(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.RESPIRATION, stack));
        tooltip.add(new TranslatableComponent("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int maxDuration = this.getConduitPowerDuration(stack);
            //When Conduit Power effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.CONDUIT_POWER, maxDuration, 0))
                return;

            if (!player.isEyeInFluid(FluidTags.WATER)) {
                this.addEffect(player, MobEffects.CONDUIT_POWER, maxDuration, 0);
            } else {
                if (player.getEffect(MobEffects.CONDUIT_POWER) != null && level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.CONDUIT_POWER, this.getConduitPowerDuration(oldStack), 0);
    }

    //to support other apis do not use this method
    //@Override
    //public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {}

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRenderGameOverlay(int width, int height, float partialTicks) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AQUANAUT_GUI_TEX_PATH);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
