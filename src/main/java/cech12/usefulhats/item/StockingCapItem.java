package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class StockingCapItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public StockingCapItem() {
        super("stocking_cap", HatArmorMaterial.STOCKING, rawColorFromRGB(204, 0, 23), ServerConfig.STOCKING_CAP_DAMAGE_ENABLED);
    }

    @Override
    public boolean hasChristmasVariant() {
        return true;
    }

    /**
     * Has no effect.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {}

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide && level.random.nextInt(1000) == 0) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            this.damageHatItemByOne(stack, player);
        }
    }
}
