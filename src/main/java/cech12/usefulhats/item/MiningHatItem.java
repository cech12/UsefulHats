package cech12.usefulhats.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MiningHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IUsefulHatModelOwner {

    public MiningHatItem() {
        super("mining_hat", HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0));
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.mining_speed", value).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.night_vision").applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        //when night vision is active (potions), do nothing
        if (player.getActivePotionEffect(Effects.NIGHT_VISION) != null) return;
        //support both hands
        for (ItemStack item : player.getHeldEquipment()) {
            if (item.getToolTypes().contains(ToolType.PICKAXE) && world.getLight(player.getPosition()) < 8) {
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION));
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
                return;
            }
        }
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F + this.getEnchantmentValue(headSlotItemStack)));
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
