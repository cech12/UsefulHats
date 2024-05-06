package de.cech12.usefulhats.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IAttackTargetChanger {

    boolean avoidMobChangingTarget(ItemStack itemStack, LivingEntity entity, Player targetPlayer);

}
