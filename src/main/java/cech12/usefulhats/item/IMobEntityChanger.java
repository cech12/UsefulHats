package cech12.usefulhats.item;

import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public interface IMobEntityChanger {

    void onEntityJoinWorldEvent(Mob entity, EntityJoinWorldEvent event);

}
