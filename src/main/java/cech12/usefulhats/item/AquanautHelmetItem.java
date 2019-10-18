package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class AquanautHelmetItem extends AbstractHatItem {

    private static final ResourceLocation AQUANAUT_GUI_TEX_PATH = new ResourceLocation(UsefulHatsMod.MOD_ID, "textures/misc/aquanautblur.png");

    public AquanautHelmetItem() {
        super("aquanaut_helmet", HatArmorMaterial.AQUANAUT, rawColorFromRGB(71, 191, 74));
        this.addAllowedEnchantment(Enchantments.RESPIRATION);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack) + 1;
        String minutes =  enchantmentLevel + " minute";
        if (enchantmentLevel > 1) {
            minutes += "s";
        }
        tooltip.add(new StringTextComponent("You get " + Effects.CONDUIT_POWER.getDisplayName().getFormattedText() + " effect for " + minutes + ".").applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!player.areEyesInFluid(FluidTags.WATER)) {
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack) + 1;
            player.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, enchantmentLevel * 1200, 0, false, false, true));
        } else {
            //TODO only get damage, when the effect comes from this helmet!
            if (random.nextInt(20) == 0) {
                this.damageHatItemByOne(stack, player);
            }
        }
    }

    @Override
    public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {
        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlphaTest();
        Minecraft.getInstance().getTextureManager().bindTexture(AQUANAUT_GUI_TEX_PATH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, height, -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos(width, height, -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos(width, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        GlStateManager.enableAlphaTest();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
