package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

import java.util.List;

public class LuckyHatItem extends AbstractHatItem implements IItemFishedListener, ILivingDropsListener, IUsefulHatModelOwner {

    public LuckyHatItem() {
        super("lucky_hat", HatArmorMaterial.LUCKY, rawColorFromRGB(72, 242, 0));
        this.addAllowedEnchantment(Enchantments.LUCK_OF_THE_SEA);
        this.addAllowedEnchantment(Enchantments.LOOTING);
    }

    private boolean isLuckOrUnluckActive(PlayerEntity player) {
        EffectInstance luck = player.getActivePotionEffect(Effects.LUCK);
        EffectInstance unluck = player.getActivePotionEffect(Effects.UNLUCK);
        return ((luck != null && luck.getDuration() > 0) || (unluck != null && unluck.getDuration() > 0));
    }

    private boolean hasHatRelatedItemInHand(PlayerEntity player) {
        //support both hands
        for (ItemStack item : player.getHeldEquipment()) {
            if (item.getToolTypes().contains(ToolType.AXE) ||
                    item.getItem() instanceof FishingRodItem ||
                    item.getItem() instanceof SwordItem) {
                return true;
            }
        }
        return false;
    }

    private int getEffectLevel(ItemStack stack) {
        //looting and luck of the sea raise the luck level
        return 1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack) +
                EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        tooltip.add(new TranslationTextComponent("item.usefulhats.lucky_hat.desc.luck", UsefulHatsUtils.getRomanNumber(getEffectLevel(stack), false)).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckActive(player)) return;
        //else add luck effect when correct item is in hand
        if (this.hasHatRelatedItemInHand(player)) {
            player.addPotionEffect(new EffectInstance(Effects.LUCK, 0, getEffectLevel(stack) - 1, false, false, true));
        }
    }

    @Override
    public void onItemFishedListener(ItemFishedEvent event, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckActive(event.getPlayer())) return;
        //damage item after fishing
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(event.getPlayer())) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

    @Override
    public void onLivingDropsEvent(LivingDropsEvent event, PlayerEntity dropReason, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckActive(dropReason)) return;
        //damage item after killing a mob
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(dropReason)) {
            this.damageHatItemByOne(headSlotItemStack, dropReason);
        }
    }
}
