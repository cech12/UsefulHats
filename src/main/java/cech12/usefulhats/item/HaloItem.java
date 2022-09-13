package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HaloItem extends AbstractHatItem implements IAttackTargetChanger, IMobEntityChanger, IEquipmentChangeListener {

    private static final Map<Player, Integer> PREVIOUS_DAMAGE_TICK_OF_PLAYER = new HashMap<>();

    public HaloItem() {
        super(HatArmorMaterial.HALO, rawColorFromRGB(255, 236, 142), ServerConfig.HALO_DAMAGE_ENABLED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.halo.desc.no_attack").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("item.usefulhats.halo.desc.beware_of_nether").withStyle(ChatFormatting.RED));
    }

    private static boolean isEntityInNether(Entity entity) {
        return entity.level.dimensionType().respawnAnchorWorks();
    }

    @Override
    public void onEntityJoinWorldEvent(Mob entity, EntityJoinLevelEvent event) {
        // add attack goal to all neutral nether mobs against players with halo on (only in nether)
        // - Hoglins/Zoglins (hostile) - nothing to do
        // - Piglins (hostile except golden armor) - TODO add the goal unrelated to golden armor - difficult because of its brain implementation
        // - Zombified Piglins (neutral) - add the goal
        if (entity instanceof ZombifiedPiglin) {
            entity.targetSelector.addGoal(1, new NearestHaloTargetGoal(entity, this));
        }
    }

    @Override
    public boolean avoidMobChangingTarget(ItemStack stack, Mob mob, Player targetPlayer) {
        // avoid to get attacked from non-boss mob entities outside the nether
        if (mob.getType().is(Tags.EntityTypes.BOSSES) || isEntityInNether(targetPlayer)) return false;
        if (this.enabledDamageConfig.get()) {
            //damage stack each second
            Integer previousDamageTick = PREVIOUS_DAMAGE_TICK_OF_PLAYER.get(targetPlayer);
            if (previousDamageTick == null || previousDamageTick + 20 <= targetPlayer.tickCount) {
                this.damageHatItemByOne(stack, targetPlayer);
                PREVIOUS_DAMAGE_TICK_OF_PLAYER.put(targetPlayer, targetPlayer.tickCount);
            }
        }
        return true;
    }

    @Override
    public void onEquippedHatItem(LivingEntity entity, ItemStack newStack) {
        if (isEntityInNether(entity)) return;
        //all aggressive entities in range should forget their targets, when halo is equipped
        Vec3 playerPos = entity.position();
        int range = 32;
        AABB radius = new AABB(playerPos.x()-range, playerPos.y()-range, playerPos.z()-range, playerPos.x()+range, playerPos.y()+range, playerPos.z()+range);
        entity.level.getEntitiesOfClass(Mob.class, radius, mob -> mob.getTarget() == entity && !mob.getType().is(Tags.EntityTypes.BOSSES))
                .forEach(mob -> mob.setTarget(null));
    }

    private static class NearestHaloTargetGoal extends NearestAttackableTargetGoal<Player> {
        NearestHaloTargetGoal(Mob mobEntity, IMobEntityChanger hatItem) {
            super(mobEntity, Player.class, 0, true, false,
                    (entity) -> {
                        if (entity instanceof Player && isEntityInNether(entity)) {
                            for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(entity)) {
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
