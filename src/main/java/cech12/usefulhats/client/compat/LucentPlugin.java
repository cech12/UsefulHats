package cech12.usefulhats.client.compat;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.item.MiningHatItem;
import com.legacy.lucent.api.plugin.ILucentPlugin;
import com.legacy.lucent.api.registry.EntityLightingRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@com.legacy.lucent.api.plugin.LucentPlugin
@OnlyIn(Dist.CLIENT)
public class LucentPlugin implements ILucentPlugin {

    @Override
    public String ownerModID() {
        return UsefulHatsMod.MOD_ID;
    }

    @Override
    public void registerEntityLightings(EntityLightingRegistry registry) {
        registry.register(EntityType.PLAYER, player -> MiningHatItem.isLightEnabled(player) ?  15 : 0);
    }

}
