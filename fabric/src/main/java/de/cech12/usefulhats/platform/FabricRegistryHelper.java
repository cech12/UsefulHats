package de.cech12.usefulhats.platform;

import de.cech12.usefulhats.compat.AccessoriesCompat;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.services.IRegistryHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class FabricRegistryHelper implements IRegistryHelper {

    @Override
    public void addGoalToMob(Mob mob, int position, Goal goal) {
        mob.targetSelector.addGoal(position, goal);
    }

    @Override
    public boolean isBossEntity(LivingEntity entity) {
        return entity.getType().is(ConventionalEntityTypeTags.BOSSES);
    }

    @Override
    public List<Item> getAllHatItems() {
        return ModItems.ALL_HATS;
    }

    @Override
    public List<ItemStack> getEquippedHatItemStacks(LivingEntity entity) {
        List<ItemStack> stacks = new LinkedList<>();
        //vanilla head slot
        ItemStack headItemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (headItemStack.getItem() instanceof AbstractHatItem) {
            stacks.add(headItemStack);
        }
        /* TODO
        if (Services.PLATFORM.isModLoaded(TrinketsCompat.MOD_ID)) {
            TrinketsCompat.addEquippedHatsToList(entity, stacks);
        }
         */
        if (Services.PLATFORM.isModLoaded(AccessoriesCompat.MOD_ID)) {
            AccessoriesCompat.addEquippedHatsToList(entity, stacks);
        }
        return stacks;
    }

    @Override
    public boolean areEntityEyesInDrownableFluid(LivingEntity entity) {
        return entity.isEyeInFluid(FluidTags.WATER);
    }

    @Override
    public boolean isEntityInFluid(LivingEntity entity) {
        return entity.isInWater() || entity.isInLava();
    }

    @Override
    public boolean isAxe(ItemStack tool) {
        return tool.is(ItemTags.AXES);
    }

    @Override
    public boolean isHoe(ItemStack tool) {
        return tool.is(ItemTags.HOES);
    }

    @Override
    public boolean isPickaxe(ItemStack tool) {
        return tool.is(ItemTags.PICKAXES);
    }

    @Override
    public boolean isShovel(ItemStack tool) {
        return tool.is(ItemTags.SHOVELS);
    }

}
