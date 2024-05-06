package de.cech12.usefulhats.item;

import de.cech12.usefulhats.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AquanautHelmetItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner, IGameOverlayRenderer {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = new ResourceLocation(Constants.MOD_ID, "textures/misc/aquanautblur.png");

    public AquanautHelmetItem() {
        super(HatArmorMaterial.AQUANAUT, rawColorFromRGB(71, 191, 74), Services.CONFIG::isAquanautHelmetDamageEnabled);
    }

    private int getConduitPowerDuration(ItemStack stack) {
        return Services.CONFIG.getAquanautHelmetEffectTimeWithRespiration(Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.RESPIRATION)) * 20;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int effectTime = Services.CONFIG.getAquanautHelmetEffectTimeWithRespiration(Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.RESPIRATION));
        tooltip.add(Component.translatable("item.usefulhats.aquanaut_helmet.desc.conduit_power", effectTime).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int maxDuration = this.getConduitPowerDuration(stack);
            //When Conduit Power effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.CONDUIT_POWER, maxDuration, 0))
                return;

            if (!player.isEyeInFluid(FluidTags.WATER)) {
                this.addEffect(player, MobEffects.CONDUIT_POWER, maxDuration, 0);
            } else {
                if (player.getEffect(MobEffects.CONDUIT_POWER) != null && player.tickCount % 20 == 0) {
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
