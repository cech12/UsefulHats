package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class MushroomHatItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public MushroomHatItem() {
        super("mushroom_hat", HatArmorMaterial.MUSHROOM, rawColorFromRGB(197, 35, 35), Config.MUSHROOM_HAT_ENABLED, Config.MUSHROOM_HAT_DAMAGE_ENABLED);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        //tooltip.add(new TranslationTextComponent("item.usefulhats.mushroom_hat.desc.feeding", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.mushroom_hat.desc.feeding", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).func_240699_a_(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            //eat every 3 seconds until no food is needed
            FoodStats foodStats = player.getFoodStats();
            if (foodStats.needFood() && player.ticksExisted % Config.MUSHROOM_HAT_EAT_INTERVAL.getValue() == 0) {
                foodStats.addStats(1, 0.5F);
                world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                this.damageHatItemByOne(stack, player);
            }
        }
    }
}
