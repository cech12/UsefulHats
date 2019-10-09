package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MiningHatItem extends ArmorItem {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(UsefulHatsMod.MOD_ID, "mining_hat");

    public MiningHatItem() {
        super(HatArmorMaterial.MINING, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));
        this.setRegistryName(REGISTRY_NAME);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("While holding a pickaxe you get " + Effects.HASTE.getDisplayName().getFormattedText() +
                " and " + Effects.NIGHT_VISION.getDisplayName().getFormattedText() + " effect"));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        Item heldItem = player.getHeldItem(Hand.MAIN_HAND).getItem();
        if (heldItem instanceof PickaxeItem) {
            player.addPotionEffect(new EffectInstance(Effects.HASTE));
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION));
        }
    }

}
