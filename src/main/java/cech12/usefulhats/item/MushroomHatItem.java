package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MushroomHatItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public MushroomHatItem() {
        super("mushroom_hat", HatArmorMaterial.MUSHROOM, rawColorFromRGB(197, 35, 35), Config.MUSHROOM_HAT_ENABLED, Config.MUSHROOM_HAT_DAMAGE_ENABLED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.mushroom_hat.desc.feeding").withStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //eat every 3 seconds until no food is needed
            FoodStats foodStats = player.getFoodData();
            if (foodStats.needsFood() && player.tickCount % Config.MUSHROOM_HAT_EAT_INTERVAL.getValue() == 0) {
                foodStats.eat(1, 0.5F);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
                this.damageHatItemByOne(stack, player);
            }
        }
    }
}
