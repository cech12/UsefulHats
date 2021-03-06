package cech12.usefulhats.compat;

import cech12.usefulhats.config.Config;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosMod {

    public static final String CURIOS_ID = "curios";

    private CuriosMod() {}

    public static boolean isLoaded() {
        return ModList.get().isLoaded(CURIOS_ID);
    }

    public static void addHeadSlot() {
        if (isLoaded() && Config.CURIOS_ADD_HEAD_SLOT.getValue()) {
            InterModComms.sendTo(CURIOS_ID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        }
    }

}
