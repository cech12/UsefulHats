package de.cech12.usefulhats.platform.services;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Common registry helper service interface.
 */
public interface IRegistryHelper {

    void addGoalToMob(Mob mob, int position, Goal goal);

    boolean isBossEntity(LivingEntity entity);

    /**
     * @return Returns all available useful hats.
     */
    List<Item> getAllHatItems();

    /**
     * Get all equipped item stacks of hat items of this mod. Some APIs like Curios enables to have more than one slot.
     * If two hats of the same item are worn, only one is in the list to avoid effect stacking.
     * @param entity entity
     * @return List of all equipped item stacks of hat items of this mod.
     */
    List<ItemStack> getEquippedHatItemStacks(LivingEntity entity);

    /**
     * Checks if the eyes of the given entity are in a fluid, where it can drown.
     * @param entity entity to check
     * @return true, if the eyes of the given entity are in a drownable fluid, else false
     */
    boolean areEntityEyesInDrownableFluid(LivingEntity entity);

    /**
     * Checks if the given entity is in a fluid.
     * @param entity entity to check
     * @return true, if the given entity is in a fluid, else false
     */
    boolean isEntityInFluid(LivingEntity entity);

    default boolean isAxe(ItemStack tool) {
        return tool.is(ItemTags.AXES);
    }

    default boolean isHoe(ItemStack tool) {
        return tool.is(ItemTags.HOES);
    }

    default boolean isPickaxe(ItemStack tool) {
        return tool.is(ItemTags.PICKAXES);
    }

    default boolean isShovel(ItemStack tool) {
        return tool.is(ItemTags.SHOVELS);
    }

}
