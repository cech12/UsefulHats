package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PostmanHatItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int HUNGER_AMPLIFIER = 0;
    private static final int HUNGER_DURATION = 219;
    private static final int SPEED_DURATION = 219;

    public PostmanHatItem() {
        super(HatArmorMaterial.POSTMAN, rawColorFromRGB(57, 99, 150), ServerConfig.POSTMAN_HAT_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.BLOCK_EFFICIENCY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack) + 1;
        tooltip.add(new TranslatableComponent("item.usefulhats.postman_hat.desc.speed", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        if (ServerConfig.POSTMAN_HAT_HUNGER_ENABLED.get()) {
            tooltip.add(new TranslatableComponent("item.usefulhats.postman_hat.desc.hunger").withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int speedAmplifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
            //When Speed effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier)) return;
            // Speed of other sources will not be overridden here.
            if (player.isSprinting()) {
                if (player.getEffect(MobEffects.MOVEMENT_SPEED) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
                }
                if (ServerConfig.POSTMAN_HAT_HUNGER_ENABLED.get()) {
                    if (!this.isEffectCausedByOtherSource(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER)) {
                        if (player.getEffect(MobEffects.HUNGER) == null || player.tickCount % 19 == 0) {
                            this.addEffect(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                        }
                    }
                }
                if (level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            } else {
                this.removeEffect(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
                if (ServerConfig.POSTMAN_HAT_HUNGER_ENABLED.get()) {
                    this.removeEffect(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int speedAmplifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, oldStack);
        this.removeEffect(entity, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
        if (ServerConfig.POSTMAN_HAT_HUNGER_ENABLED.get()) {
            this.removeEffect(entity, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
        }
    }

}
