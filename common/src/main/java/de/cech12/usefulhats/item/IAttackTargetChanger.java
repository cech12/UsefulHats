package de.cech12.usefulhats.item;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IAttackTargetChanger {

    boolean avoidMobChangingTarget(ItemStack itemStack, Mob entity, Player targetPlayer);

}
