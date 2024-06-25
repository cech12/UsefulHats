package de.cech12.usefulhats.item;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface IGameOverlayRenderer {

    void onRenderGameOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker);

}
