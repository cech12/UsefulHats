package cech12.usefulhats.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IGameOverlayRenderer {

    @OnlyIn(Dist.CLIENT)
    void onRenderGameOverlay(int width, int height, float partialTicks);

}
