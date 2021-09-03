package cech12.usefulhats.item;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface IAttackTargetChanger {

    void onLivingSetAttackTarget(Mob entity, Player targetPlayer);

}
