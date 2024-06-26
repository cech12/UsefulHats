package de.cech12.usefulhats.platform;

import de.cech12.usefulhats.compat.Baubles2Compat;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.services.IRegistryHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.ToolActions;

import java.util.LinkedList;
import java.util.List;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    @Override
    public void addGoalToMob(Mob mob, int position, Goal goal) {
        mob.targetSelector.addGoal(position, goal);
    }

    @Override
    public boolean isBossEntity(LivingEntity entity) {
        return entity.getType().is(Tags.EntityTypes.BOSSES);
    }

    @Override
    public List<Item> getAllHatItems() {
        return ModItems.ITEMS.getEntries().stream().map(itemDeferredHolder -> (Item) itemDeferredHolder.get()).toList();
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
        if (Services.PLATFORM.isModLoaded(AccessoriesCompat.MOD_ID)) {
            AccessoriesCompat.addEquippedHatsToList(entity, stacks);
        }
         */
        if (Services.PLATFORM.isModLoaded(Baubles2Compat.MOD_ID)) {
            Baubles2Compat.addEquippedHatsToList(entity, stacks);
        }
        return stacks;
    }

    @Override
    public boolean areEntityEyesInDrownableFluid(LivingEntity entity) {
        return entity.getEyeInFluidType().canDrownIn(entity);
    }

    @Override
    public boolean isEntityInFluid(LivingEntity entity) {
        return entity.isInFluidType();
    }

    @Override
    public boolean isAxe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.AXE_DIG);
    }

    @Override
    public boolean isHoe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.HOE_DIG);
    }

    @Override
    public boolean isPickaxe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.PICKAXE_DIG);
    }

    @Override
    public boolean isShovel(ItemStack tool) {
        return tool.canPerformAction(ToolActions.SHOVEL_DIG);
    }

}
