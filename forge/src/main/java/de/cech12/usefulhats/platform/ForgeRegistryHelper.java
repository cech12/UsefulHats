package de.cech12.usefulhats.platform;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.services.IRegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.LinkedList;
import java.util.List;

public class ForgeRegistryHelper implements IRegistryHelper {

    @Override
    public ResourceLocation getItemResourceLocation(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    @Override
    public void addGoalToMob(Mob mob, int position, Goal goal) {
        mob.targetSelector.addGoal(position, goal);
    }

    @Override
    public boolean isBossEntity(LivingEntity entity) {
        return entity.getType().is(Tags.EntityTypes.BOSSES);
    }

    @Override
    public List<ItemStack> getEquippedHatItemStacks(LivingEntity entity) {
        List<ItemStack> stacks = new LinkedList<>();
        //vanilla head slot
        ItemStack headItemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (headItemStack.getItem() instanceof AbstractHatItem) {
            stacks.add(headItemStack);
        }
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            //all curios slots that contain an AbstractHatItem
            CuriosApi.getCuriosHelper().getEquippedCurios(entity).ifPresent(itemHandler -> {
                int slots = itemHandler.getSlots();
                for (int i = 0; i < slots; i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                        stacks.add(stack);
                    }
                }
            });
        }
        return stacks;
    }

    @Override
    public boolean isAxe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.AXE_DIG);
    }

    @Override
    public boolean isHoe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.HOE_DIG);
    }

    @Override
    public boolean isFishingRod(ItemStack tool) {
        return tool.canPerformAction(ToolActions.FISHING_ROD_CAST);
    }

    @Override
    public boolean isPickaxe(ItemStack tool) {
        return tool.canPerformAction(ToolActions.PICKAXE_DIG);
    }

    @Override
    public boolean isShovel(ItemStack tool) {
        return tool.canPerformAction(ToolActions.SHOVEL_DIG);
    }

    @Override
    public boolean isSword(ItemStack tool) {
        return tool.canPerformAction(ToolActions.SWORD_SWEEP);
    }

}
