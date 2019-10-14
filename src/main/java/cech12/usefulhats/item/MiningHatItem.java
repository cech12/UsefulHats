package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.List;

public class MiningHatItem extends AbstractHatItem implements IBreakSpeedChanger {

    public MiningHatItem() {
        super("mining_hat", HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("Mining with a pickaxe is 20% faster. (stackable with enchantments and potions)"));
        tooltip.add(new StringTextComponent("While holding a pickaxe you get " + Effects.NIGHT_VISION.getDisplayName().getFormattedText() + " effect."));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        //support both hands
        for (ItemStack item : player.getHeldEquipment()) {
            if (item.getToolTypes().contains(ToolType.PICKAXE)) {
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION));
                return;
            }
        }
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.2F);
        }
    }
}
