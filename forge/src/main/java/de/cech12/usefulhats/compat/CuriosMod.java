package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.Services;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosMod {

    private CuriosMod() {}

    public static void addHeadSlot() {
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID) && Services.CONFIG.isCuriosAddHeadSlot()) {
            InterModComms.sendTo(Constants.CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        }
    }

}
