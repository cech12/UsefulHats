package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class HaloItem extends AbstractHatItem implements IAttackTargetChanger, IMobEntityChanger {

    public HaloItem() {
        super("halo", HatArmorMaterial.HALO, rawColorFromRGB(255, 236, 142), ServerConfig.HALO_DAMAGE_ENABLED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("item.usefulhats.halo.desc.no_attack").withStyle(ChatFormatting.BLUE));
        tooltip.add(new TranslatableComponent("item.usefulhats.halo.desc.beware_of_nether").withStyle(ChatFormatting.RED));
    }

    private static boolean isEntityInNether(Entity entity) {
        return entity.level.dimensionType().respawnAnchorWorks();
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            // Looses durability outside the nether when non-boss mobs are in range (X blocks)
            if (!isEntityInNether(player)) {
                Vec3 playerPos = player.position();
                int range = ServerConfig.HALO_DETECTING_RANGE.get();
                AABB radius = new AABB(playerPos.x()-range, playerPos.y()-range, playerPos.z()-range, playerPos.x()+range, playerPos.y()+range, playerPos.z()+range);
                List<Mob> mobsInRange = player.level.getEntitiesOfClass(Mob.class, radius, (Predicate<Entity>) entity -> entity instanceof Mob && entity.canChangeDimensions());
                if (!mobsInRange.isEmpty() && level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onEntityJoinWorldEvent(Mob entity, EntityJoinWorldEvent event) {
        // add attack goal to all neutral nether mobs against players with halo on (only in nether)
        // 1.15: (outdated)
        // - Zombie Pigman
        // 1.16:
        // - Hoglins/Zoglins (hostile) - nothing to do
        // - Piglins (hostile except golden armor) - TODO add the goal unrelated to golden armor - difficult because of its brain implementation
        // - Zombified Piglins (neutral) - add the goal
        if (entity instanceof ZombifiedPiglin) {
            entity.targetSelector.addGoal(1, new NearestHaloTargetGoal(entity, this));
        }
    }

    @Override
    public void onLivingSetAttackTarget(Mob entity, Player targetPlayer) {
        // avoid to get attacked from non-boss mob entities outside the nether
        if (entity.canChangeDimensions() && !isEntityInNether(targetPlayer)) {
            entity.setTarget(null);
        }
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
