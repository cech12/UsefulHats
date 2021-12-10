package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.food.FoodData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MushroomHatItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public MushroomHatItem() {
        super("mushroom_hat", HatArmorMaterial.MUSHROOM, rawColorFromRGB(197, 35, 35), ServerConfig.MUSHROOM_HAT_DAMAGE_ENABLED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("item.usefulhats.mushroom_hat.desc.feeding").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!world.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //eat every 3 seconds until no food is needed
            FoodData foodStats = player.getFoodData();
            if (foodStats.needsFood() && player.tickCount % ServerConfig.MUSHROOM_HAT_EAT_INTERVAL.get() == 0) {
                foodStats.eat(1, 0.5F);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
                this.damageHatItemByOne(stack, player);
            }
        }
    }
}
