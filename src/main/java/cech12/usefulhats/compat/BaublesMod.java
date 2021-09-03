package cech12.usefulhats.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class BaublesMod {

    public static final String BAUBLES_ID = "baubles";
    public static final ResourceLocation BAUBLES_ITEM_ID = new ResourceLocation(BAUBLES_ID, "item");

    private BaublesMod() {}

    public static boolean isLoaded() {
        return ModList.get().isLoaded(BAUBLES_ID);
    }

}
