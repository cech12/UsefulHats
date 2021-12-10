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
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BunnyEarsItem extends AbstractHatItem implements IEquipmentChangeListener, IItemUseListener, IUsefulHatModelOwner {

    private static final int JUMP_BOOST_DURATION = 219;

    public BunnyEarsItem() {
        super("bunny_ears", HatArmorMaterial.BUNNY, rawColorFromRGB(142, 120, 94), ServerConfig.BUNNY_EARS_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.BLOCK_EFFICIENCY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack) + 1;
        tooltip.add(new TranslatableComponent("item.usefulhats.bunny_ears.desc.jump_boost", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        tooltip.add(new TranslatableComponent("item.usefulhats.bunny_ears.desc.eating", enchantmentLevel + 1).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int amplifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
            if (!this.isEffectCausedByOtherSource(player, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier)) {
                if (player.getEffect(MobEffects.JUMP) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
                }
                //calculate damage if effect is caused by this item
                if (level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player); //TODO only when jumping
                }
            }
        }
    }

    @Override
    public void onItemUseEvent(LivingEntityUseItemEvent event, Player player, ItemStack headSlotItemStack) {
        if (event instanceof LivingEntityUseItemEvent.Start) {
            ItemStack usedStack = event.getItem();
            if (usedStack.getItem().isEdible()) {
                int amplifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, headSlotItemStack) + 2;
                event.setDuration(event.getDuration() / amplifier);
                this.damageHatItemByOne(headSlotItemStack, player);
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int amplifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, oldStack);
        this.removeEffect(entity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
    }
}
