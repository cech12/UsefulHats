package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;

public class BunnyEarsItem extends AbstractHatItem implements IEquipmentChangeListener, IItemUseListener, IUsefulHatModelOwner {

    private static final int JUMP_BOOST_DURATION = 219;

    public BunnyEarsItem() {
        super("bunny_ears", HatArmorMaterial.BUNNY, rawColorFromRGB(142, 120, 94), Config.BUNNY_EARS_ENABLED, Config.BUNNY_EARS_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        tooltip.add(new TranslationTextComponent("item.usefulhats.bunny_ears.desc.jump_boost", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.bunny_ears.desc.eating", enchantmentLevel + 1).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int amplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if (!this.isEffectCausedByOtherSource(player, Effects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier)) {
                if (player.getActivePotionEffect(Effects.JUMP_BOOST) == null || player.ticksExisted % 19 == 0) {
                    this.addEffect(player, Effects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier);
                }
                //calculate damage if effect is caused by this item
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player); //TODO only when jumping
                }
            }
        }
    }

    @Override
    public void onItemUseEvent(LivingEntityUseItemEvent event, PlayerEntity player, ItemStack headSlotItemStack) {
        if (event instanceof LivingEntityUseItemEvent.Start) {
            ItemStack usedStack = event.getItem();
            if (usedStack.getItem().isFood()) {
                int amplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, headSlotItemStack) + 2;
                event.setDuration(event.getDuration() / amplifier);
                this.damageHatItemByOne(headSlotItemStack, player);
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int amplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, oldStack);
        this.removeEffect(entity, Effects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier);
    }
}
