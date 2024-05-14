package de.cech12.usefulhats;

import de.cech12.usefulhats.item.IAttackTargetChanger;
import de.cech12.usefulhats.item.IBreakSpeedChanger;
import de.cech12.usefulhats.item.IEquipmentChangeListener;
import de.cech12.usefulhats.item.IItemFishedListener;
import de.cech12.usefulhats.item.IItemUseListener;
import de.cech12.usefulhats.item.ILivingDropsListener;
import de.cech12.usefulhats.item.ILivingJumpListener;
import de.cech12.usefulhats.item.IMobEntityChanger;
import de.cech12.usefulhats.item.IRightClickListener;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicReference;

public class UsefulHatsEventUtils {

    public static float onBreakSpeedCalculation(Player player, BlockState state, float actualSpeed) {
        final float[] newSpeed = {actualSpeed};
        Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                .filter(stack -> stack.getItem() instanceof IBreakSpeedChanger)
                .forEach(stack -> newSpeed[0] = ((IBreakSpeedChanger) stack.getItem()).onBreakSpeedEvent(player, state, newSpeed[0], stack));
        return newSpeed[0];
    }

    public static void onBlockBreak(Player player, BlockState state) {
        Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                .filter(stack -> stack.getItem() instanceof IBreakSpeedChanger)
                .forEach(stack -> ((IBreakSpeedChanger) stack.getItem()).onBreakEvent(player, state, stack));
    }

    public static void onEntityJoinWorld(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            Services.REGISTRY.getAllHatItems().stream()
                    .filter(item -> item instanceof IMobEntityChanger)
                    .forEach(item -> ((IMobEntityChanger) item).onEntityJoinWorldEvent(livingEntity));
        }
    }

    public static void onItemFished(Player entity) {
        Services.REGISTRY.getEquippedHatItemStacks(entity).stream()
                .filter(stack -> stack.getItem() instanceof IItemFishedListener)
                .forEach(stack -> ((IItemFishedListener) stack.getItem()).onItemFishedListener(entity, stack));
    }

    public static void onLivingDiesBecauseOf(Entity entity) {
        if (entity instanceof Player player) {
            Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                    .filter(stack -> stack.getItem() instanceof ILivingDropsListener)
                    .forEach(stack -> ((ILivingDropsListener) stack.getItem()).onLivingDropsEvent(player, stack));
        }
    }

    public static void onLivingJump(LivingEntity entity) {
        Services.REGISTRY.getEquippedHatItemStacks(entity).stream()
                .filter(stack -> stack.getItem() instanceof ILivingJumpListener)
                .forEach(stack -> ((ILivingJumpListener) stack.getItem()).onLivingJumpEvent(entity, stack));
    }

    public static Integer onLivingStartsUsingItem(LivingEntity entity, ItemStack usedStack, int actualDuration) {
        AtomicReference<Integer> newDuration = new AtomicReference<>(actualDuration);
        if (entity instanceof Player player) {
            Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                    .filter(stack -> stack.getItem() instanceof IItemUseListener)
                    .forEach(stack -> {
                        Integer calculatedDuration = ((IItemUseListener) stack.getItem()).onItemUseEventStart(player, usedStack, newDuration.get(), stack);
                        if (calculatedDuration != null) {
                            newDuration.set(calculatedDuration);
                        }
                    });
        }
        return newDuration.get();
    }

    public static void onEquip(LivingEntity entity, ItemStack stack) {
        if (stack.getItem() instanceof IEquipmentChangeListener equipmentChangeListener) {
            equipmentChangeListener.onEquippedHatItem(entity, stack);
        }
    }

    public static void onUnequip(LivingEntity entity, ItemStack stack) {
        if (stack.getItem() instanceof IEquipmentChangeListener equipmentChangeListener) {
            equipmentChangeListener.onUnequippedHatItem(entity, stack);
        }
    }

    /**
     * Should be called when an entity changes its target and evaluates if the new target should be avoided.
     * @param entity entity
     * @param target new target
     * @return true, if the new target should be avoided, else false
     */
    public static boolean shouldEntityAvoidChangingTarget(LivingEntity entity, LivingEntity target) {
        if (target instanceof Player player) {
            return Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                    .filter(stack -> stack.getItem() instanceof IAttackTargetChanger)
                    .anyMatch(stack -> ((IAttackTargetChanger) stack.getItem()).avoidMobChangingTarget(stack, entity, player));
        }
        return false;
    }

    /**
     * Should be called if a right click is done and evaluates if the right click should be cancelled.
     * @param level level
     * @param player player
     * @param usedStack used ItemStack
     * @param hand hand
     * @return true, if the right click should be cancelled, else false
     */
    public static boolean shouldRightClickBeCancelled(Level level, Player player, ItemStack usedStack, InteractionHand hand) {
        return Services.REGISTRY.getEquippedHatItemStacks(player).stream()
                .filter(stack -> stack.getItem() instanceof IRightClickListener)
                .anyMatch(stack -> ((IRightClickListener) stack.getItem()).onRightClickItemEvent(level, player, usedStack, hand, stack));
    }

}
