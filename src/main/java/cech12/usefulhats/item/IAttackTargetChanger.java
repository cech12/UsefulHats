package cech12.usefulhats.item;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface IAttackTargetChanger {

    void onLivingSetAttackTarget(MobEntity entity, PlayerEntity targetPlayer);

}
