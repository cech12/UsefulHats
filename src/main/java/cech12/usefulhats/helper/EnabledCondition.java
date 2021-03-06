package cech12.usefulhats.helper;

import cech12.usefulhats.UsefulHatsMod;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Condition that checks whether item is instance of IEnabled, and if it is, whether or not it is enabled.
 * Used to disable recipes for items disabled in config.
 */
public class EnabledCondition implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(UsefulHatsMod.MOD_ID, "is_enabled");
    private final ResourceLocation item;

    public EnabledCondition(ResourceLocation item) {
        this.item = item;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        Item item = ForgeRegistries.ITEMS.getValue(this.item);

        if (item == null)
            return false;

        return !(item instanceof IEnabled) || ((IEnabled) item).isEnabled();
    }

    public static class Serializer implements IConditionSerializer<EnabledCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, EnabledCondition value) {
            json.addProperty("item", value.item.toString());
        }

        @Override
        public EnabledCondition read(JsonObject json) {
            return new EnabledCondition(new ResourceLocation(JSONUtils.getString(json, "item")));
        }

        @Override
        public ResourceLocation getID() {
            return EnabledCondition.ID;
        }
    }
}

