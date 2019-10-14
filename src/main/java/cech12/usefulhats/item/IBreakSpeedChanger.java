package cech12.usefulhats.item;

import net.minecraftforge.event.entity.player.PlayerEvent;

public interface IBreakSpeedChanger {

    void onBreakSpeedEvent(PlayerEvent.BreakSpeed event);

}
