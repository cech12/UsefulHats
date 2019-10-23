package cech12.usefulhats.item;

import net.minecraft.entity.MobEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public interface IMobEntityChanger {

    void onEntityJoinWorldEvent(MobEntity entity, EntityJoinWorldEvent event);

}
