package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
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
        tooltip.add(new TranslationTextComponent("item.usefulhats.halo.desc.no_attack").applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.halo.desc.beware_of_nether").applyTextStyle(TextFormatting.RED));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        // Looses durability outside the nether when non-boss mobs are in range (X blocks)
        if (player.dimension != DimensionType.THE_NETHER) {
            Vec3d playerPos = player.getPositionVec();
            int range = Config.HALO_DETECTING_RANGE.getValue();
            AxisAlignedBB radius = new AxisAlignedBB(playerPos.getX()-range, playerPos.getY()-range, playerPos.getZ()-range, playerPos.getX()+range, playerPos.getY()+range, playerPos.getZ()+range);
            List<MobEntity> mobsInRange = player.world.getEntitiesWithinAABB(MobEntity.class, radius, (Predicate<Entity>) entity -> entity instanceof MobEntity && entity.isNonBoss());
            if (!mobsInRange.isEmpty() && random.nextInt(20) == 0) {
                this.damageHatItemByOne(stack, player);
            }
        }
    }

    @Override
    public void onEntityJoinWorldEvent(MobEntity entity, EntityJoinWorldEvent event) {
        // add attack goal to ZombiePigmanEntity against players with halo on (only in nether)
        if (entity instanceof ZombiePigmanEntity) {
            entity.targetSelector.addGoal(1, new NearestHaloTargetGoal(entity, this));
        }
    }

    @Override
    public void onLivingSetAttackTarget(MobEntity entity, PlayerEntity targetPlayer) {
        // avoid to get attacked from non-boss mob entities outside the nether
        if (entity.isNonBoss() && targetPlayer.dimension != DimensionType.THE_NETHER) {
            entity.setAttackTarget(null);
        }
    }

    private static class NearestHaloTargetGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        NearestHaloTargetGoal(MobEntity mobEntity, IMobEntityChanger hatItem) {
            super(mobEntity, PlayerEntity.class, 0, true, false,
                    (entity) -> (entity instanceof PlayerEntity &&
                            entity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == hatItem &&
                            entity.dimension == DimensionType.THE_NETHER));
        }
    }
}
