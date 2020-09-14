package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.List;
import java.util.function.Predicate;

public class HaloItem extends AbstractHatItem implements IAttackTargetChanger, IMobEntityChanger {

    public HaloItem() {
        super("halo", HatArmorMaterial.HALO, rawColorFromRGB(255, 236, 142), Config.HALO_ENABLED, Config.HALO_DAMAGE_ENABLED);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        tooltip.add(new TranslationTextComponent("item.usefulhats.halo.desc.no_attack").mergeStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.halo.desc.beware_of_nether").mergeStyle(TextFormatting.RED));
    }

    private static boolean isEntityInNether(Entity entity) {
        return entity.world.func_230315_m_().func_241511_k_();
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            // Looses durability outside the nether when non-boss mobs are in range (X blocks)
            if (!isEntityInNether(player)) {
                Vector3d playerPos = player.getPositionVec();
                int range = Config.HALO_DETECTING_RANGE.getValue();
                AxisAlignedBB radius = new AxisAlignedBB(playerPos.getX()-range, playerPos.getY()-range, playerPos.getZ()-range, playerPos.getX()+range, playerPos.getY()+range, playerPos.getZ()+range);
                List<MobEntity> mobsInRange = player.world.getEntitiesWithinAABB(MobEntity.class, radius, (Predicate<Entity>) entity -> entity instanceof MobEntity && entity.isNonBoss());
                if (!mobsInRange.isEmpty() && random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onEntityJoinWorldEvent(MobEntity entity, EntityJoinWorldEvent event) {
        // add attack goal to all neutral nether mobs against players with halo on (only in nether)
        // 1.15: (outdated)
        // - Zombie Pigman
        // 1.16:
        // - Hoglins/Zoglins (hostile) - nothing to do
        // - Piglins (hostile except golden armor) - TODO add the goal unrelated to golden armor - difficult because of its brain implementation
        // - Zombified Piglins (neutral) - add the goal
        if (entity instanceof ZombifiedPiglinEntity) {
            entity.targetSelector.addGoal(1, new NearestHaloTargetGoal(entity, this));
        }
    }

    @Override
    public void onLivingSetAttackTarget(MobEntity entity, PlayerEntity targetPlayer) {
        // avoid to get attacked from non-boss mob entities outside the nether
        if (entity.isNonBoss() && !isEntityInNether(targetPlayer)) {
            entity.setAttackTarget(null);
        }
    }

    private static class NearestHaloTargetGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        NearestHaloTargetGoal(MobEntity mobEntity, IMobEntityChanger hatItem) {
            super(mobEntity, PlayerEntity.class, 0, true, false,
                    (entity) -> {
                        if (entity instanceof PlayerEntity && isEntityInNether(entity)) {
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
