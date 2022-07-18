package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.client.UsefulHatLayers;
import cech12.usefulhats.compat.CuriosMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractHatItem extends ArmorItem implements DyeableLeatherItem {

    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

    private final HatArmorMaterial material;
    private final int initColor;
    private final ForgeConfigSpec.BooleanValue enabledDamageConfig;

    private final ArrayList<Enchantment> allowedEnchantments = new ArrayList<>();
    private final ArrayList<Enchantment> forbiddenEnchantments = new ArrayList<>();

    public AbstractHatItem(HatArmorMaterial material, int initColor, ForgeConfigSpec.BooleanValue enabledDamageConfig) {
        super(material, EquipmentSlot.HEAD, (new Properties()).tab(UsefulHatsMod.ITEM_GROUP));
        this.material = material;
        this.initColor = initColor;
        this.enabledDamageConfig = enabledDamageConfig;
        this.addForbiddenEnchantment(Enchantments.FIRE_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.PROJECTILE_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.BLAST_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.ALL_DAMAGE_PROTECTION);
        //for other apis
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
    }

    protected static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);
        return rgb;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        //get durability from config because the config is not loaded in constructor
        return this.material.getDurabilityForSlot(EquipmentSlot.HEAD);
    }

    protected boolean isEffectCausedByOtherSource(LivingEntity entity, MobEffect effect, int maxDuration, int amplifier) {
        //Effect source is not detected correctly here. Maybe later there is another possibility to detect it.
        MobEffectInstance effectInstance = entity.getEffect(effect);
        return (effectInstance != null && (effectInstance.isAmbient() || effectInstance.getDuration() >= maxDuration || effectInstance.getAmplifier() != amplifier));
    }

    protected void addEffect(LivingEntity entity, MobEffect effect, int duration, int amplifier) {
        this.addEffect(entity, effect, duration, amplifier, false);
    }

    protected void addEffect(LivingEntity entity, MobEffect effect, int duration, int amplifier, boolean showParticles) {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, showParticles, true));
    }

    /**
     * This is a helper method to remove an effect of a living entity with a maximal duration and a specific amplifier.
     * @param entity entity
     * @param effect effect object
     * @param maxDuration maximal effect duration
     * @param amplifier effect amplifier
     */
    protected void removeEffect(LivingEntity entity, MobEffect effect, int maxDuration, int amplifier) {
        MobEffectInstance effectInstance = entity.getEffect(effect);
        if (effectInstance != null && !effectInstance.isAmbient() && effectInstance.getDuration() <= maxDuration && effectInstance.getAmplifier() == amplifier) {
            entity.removeEffect(effect);
        }
    }

    /**
     * Add an allowed enchantment to this item.
     * All Minecraft helmet enchantments are enabled per default. Only other enchantments should be added here.
     * @param enchantment enchantment to add
     */
    protected void addAllowedEnchantment(Enchantment enchantment) {
        this.forbiddenEnchantments.remove(enchantment);
        this.allowedEnchantments.add(enchantment);
    }

    /**
     * Add a forbidden enchantment to this item.
     * Standard forbidden enchantments are FIRE_PROTECTION, PROJECTILE_PROTECTION, BLAST_PROTECTION, PROTECTION.
     * UNBREAKING & MENDING are deactivated if the hat durability is disabled via config.
     * @param enchantment enchantment to add
     */
    protected void addForbiddenEnchantment(Enchantment enchantment) {
        this.allowedEnchantments.remove(enchantment);
        this.forbiddenEnchantments.add(enchantment);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : this.initColor;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(book).keySet()) {
            if (!this.canApplyAtEnchantingTable(stack, enchantment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (this.allowedEnchantments.contains(enchantment)) {
            return true;
        }
        if (this.forbiddenEnchantments.contains(enchantment)) {
            return false;
        }
        //disable UNBREAKING & MENDING if damage is disabled
        if (!this.enabledDamageConfig.get() && (enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.MENDING)) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    /**
     * Copy of {@link ItemStack#hurtAndBreak(int, LivingEntity, Consumer)} to enable own damaging of hat items.
     * Added config value to disable damage.
     */
    protected void damageHatItemByOne(ItemStack stack, Player entity) {
        if (!this.enabledDamageConfig.get()) return;

        if (!entity.level.isClientSide && !entity.getAbilities().instabuild) {
            if (this.canBeDepleted()) {
                if (stack.hurt(1, entity.getRandom(), entity instanceof ServerPlayer ? (ServerPlayer)entity : null)) {
                    entity.broadcastBreakEvent(EquipmentSlot.HEAD);
                    stack.shrink(1);
                    entity.awardStat(Stats.ITEM_BROKEN.get(this));
                    stack.setDamageValue(0);
                }
            }
        }
    }

    /**
     * Disables vanilla damaging.
     */
    @Deprecated
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return 0;
    }

    /**
     * Disables "When on head" line of ArmorItem Tooltip
     */
    @Override
    public @Nonnull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot equipmentSlot) {
        return HashMultimap.create();
    }

    /**
     * Adds "When on head" line to end of tooltip.
     * When hat item has no effect, override this method with an empty method.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        //tooltip.add(new TextComponent("Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()).withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal(""));
        tooltip.add((Component.translatable("item.modifiers." + EquipmentSlot.HEAD.getName())).withStyle(ChatFormatting.GRAY));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        if (this instanceof IUsefulHatModelOwner) {
            consumer.accept(Rendering.INSTANCE);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static final class Rendering implements IItemRenderProperties {

        private static final Rendering INSTANCE = new Rendering();

        private Rendering() {
        }

        @Override
        public HumanoidModel<?> getArmorModel(LivingEntity wearer, ItemStack item, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
            return UsefulHatLayers.usefulHatModel;
        }
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (this instanceof IUsefulHatModelOwner) {
            ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (resourceLocation != null) {
                String texture = resourceLocation.getPath();
                String domain = resourceLocation.getNamespace();
                return String.format("%s:textures/models/usefulhats/%s%s%s.png", domain, texture,
                        (IS_CHRISTMAS && ((IUsefulHatModelOwner) stack.getItem()).hasChristmasVariant()) ? "_xmas" : "",
                        type == null ? "" : String.format("_%s", type));
            }
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }

    /// Following Methods are related to the Curios & Baubles API

    private void setup(final FMLCommonSetupEvent evt) {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCapabilities);
    }

    private void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        if (stack.getItem() != this) {
            return;
        }
        //for curios
        if (CuriosMod.isLoaded()) {
            AbstractHatItemCurioCapability cap = new AbstractHatItemCurioCapability(stack);
            evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                final LazyOptional<ICurio> curio = LazyOptional.of(() -> cap);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, curio);
                }
            });
        }
    }

}
