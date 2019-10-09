package cech12.item;

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

public class StrawHatItem extends ArmorItem {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(UsefulHatsMod.MOD_ID, "straw_hat");

    public StrawHatItem() {
        super(HatArmorMaterial.STRAW, EquipmentSlotType.HEAD, (new Properties()).group(ItemGroup.COMBAT));
        this.setRegistryName(REGISTRY_NAME);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("While holding a shovel or hoe you get " + Effects.HASTE.getDisplayName() + " effect"));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        Item heldItem = player.getHeldItem(Hand.MAIN_HAND).getItem();
        if (heldItem instanceof ShovelItem || heldItem instanceof HoeItem) {
            player.addPotionEffect(new EffectInstance(Effects.HASTE));
        }
    }

}
