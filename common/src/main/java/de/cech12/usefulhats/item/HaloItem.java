package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HaloItem extends AbstractHatItem implements IAttackTargetChanger, IMobEntityChanger, IEquipmentChangeListener {

    private static final Map<LivingEntity, Integer> PREVIOUS_DAMAGE_TICK_OF_ENTITY = new HashMap<>();

    public HaloItem() {
        super(HatArmorMaterials.HALO, rawColorFromRGB(255, 236, 142), Services.CONFIG::getHaloDurability, Services.CONFIG::isHaloDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.halo.desc.no_attack").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("item.usefulhats.halo.desc.beware_of_nether").withStyle(ChatFormatting.RED));
    }

    private static boolean isEntityInNether(Entity entity) {
        return entity.level().dimensionType().respawnAnchorWorks();
    }

    @Override
    public void onEntityJoinWorldEvent(LivingEntity entity) {
        // add attack goal to all neutral nether mobs against living entities wearing a halo (only in nether)
        // - Hoglins/Zoglins (hostile) - nothing to do
        // - Piglins (hostile except golden armor) - TODO add the goal unrelated to golden armor - difficult because of its brain implementation
        // - Zombified Piglins (neutral) - add the goal
        if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
            Services.REGISTRY.addGoalToMob(zombifiedPiglin, 1, new NearestHaloTargetGoal(zombifiedPiglin, this));
        }
    }

    @Override
    public boolean avoidMobChangingTarget(ItemStack stack, LivingEntity entity, LivingEntity target) {
        // avoid to get attacked from non-boss mob entities outside the nether
        if (Services.REGISTRY.isBossEntity(entity) || isEntityInNether(target)) return false;
        if (this.enabledDamageConfig.get()) {
            //damage stack each second
            Integer previousDamageTick = PREVIOUS_DAMAGE_TICK_OF_ENTITY.get(target);
            if (previousDamageTick == null || previousDamageTick + 20 <= target.tickCount) {
                this.damageHatItemByOne(stack, target);
                PREVIOUS_DAMAGE_TICK_OF_ENTITY.put(target, target.tickCount);
            }
        }
        return true;
    }

    @Override
    public void onEquippedHatItem(LivingEntity entity, ItemStack newStack) {
        if (isEntityInNether(entity)) return;
        //all aggressive entities in range should forget their targets, when halo is equipped
        Vec3 entityPos = entity.position();
        int range = 32;
        AABB radius = new AABB(entityPos.x()-range, entityPos.y()-range, entityPos.z()-range, entityPos.x()+range, entityPos.y()+range, entityPos.z()+range);
        entity.level().getEntitiesOfClass(Mob.class, radius, mob -> mob.getTarget() == entity && !Services.REGISTRY.isBossEntity(mob))
                .forEach(mob -> mob.setTarget(null));
    }

    private static class NearestHaloTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
        NearestHaloTargetGoal(Mob mobEntity, IMobEntityChanger hatItem) {
            super(mobEntity, LivingEntity.class, 0, true, false,
                    (entity) -> {
                        if (entity instanceof LivingEntity && isEntityInNether(entity)) {
                            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(entity)) {
                                if (headSlotItemStack.getItem() == hatItem) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    });
        }
    }
}
