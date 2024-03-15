package de.cech12.usefulhats.item;

import net.minecraft.world.entity.Mob;

public interface IMobEntityChanger {

    void onEntityJoinWorldEvent(Mob entity);

}
