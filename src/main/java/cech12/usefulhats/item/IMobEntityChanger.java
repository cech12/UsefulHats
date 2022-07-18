package cech12.usefulhats.item;

import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public interface IMobEntityChanger {

    void onEntityJoinWorldEvent(Mob entity, EntityJoinLevelEvent event);

}
