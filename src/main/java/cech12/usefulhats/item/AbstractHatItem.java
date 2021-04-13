package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.client.UsefulHatModel;
import cech12.usefulhats.compat.BaublesMod;
import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.config.ConfigType;
import cech12.usefulhats.helper.IEnabled;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.lazy.baubles.api.bauble.IBauble;
import com.lazy.baubles.api.cap.BaublesCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractHatItem extends ArmorItem implements IEnabled, IDyeableArmorItem {

    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;
    private static UsefulHatModel<LivingEntity> model;

    private final HatArmorMaterial material;
    private final int initColor;
    private final ConfigType.Boolean enabledConfig;
    private final ConfigType.Boolean enabledDamageConfig;

    private final ArrayList<Enchantment> allowedEnchantments = new ArrayList<>();
    private final ArrayList<Enchantment> forbiddenEnchantments = new ArrayList<>();

    public AbstractHatItem(String name, HatArmorMaterial material, int initColor, ConfigType.Boolean enabledConfig, ConfigType.Boolean enabledDamageConfig) {
        super(material, EquipmentSlotType.HEAD, (new Properties()).group(UsefulHatsMod.ITEM_GROUP));
        this.setRegistryName(new ResourceLocation(UsefulHatsMod.MOD_ID, name));
        this.material = material;
        this.initColor = initColor;
        this.enabledConfig = enabledConfig;
        this.enabledDamageConfig = enabledDamageConfig;
        this.addForbiddenEnchantment(Enchantments.FIRE_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.PROJECTILE_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.BLAST_PROTECTION);
        this.addForbiddenEnchantment(Enchantments.PROTECTION);
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
    public boolean isEnabled() {
        return this.enabledConfig.getValue();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        //get durability from config because the config is not loaded in constructor
        return this.material.getDurability(EquipmentSlotType.HEAD);
    }

    protected boolean isEffectCausedByOtherSource(LivingEntity entity, Effect effect, int maxDuration, int amplifier) {
        //TODO detect effect source correctly
        EffectInstance effectInstance = entity.getActivePotionEffect(effect);
        return (effectInstance != null && (effectInstance.isAmbient() || effectInstance.getDuration() >= maxDuration || effectInstance.getAmplifier() != amplifier));
    }

    protected void addEffect(LivingEntity entity, Effect effect, int duration, int amplifier) {
        this.addEffect(entity, effect, duration, amplifier, false);
    }

    protected void addEffect(LivingEntity entity, Effect effect, int duration, int amplifier, boolean showParticles) {
        entity.addPotionEffect(new EffectInstance(effect, duration, amplifier, false, showParticles, true));
    }

    /**
     * This is a helper method to remove an effect of a living entity with a maximal duration and a specific amplifier.
     * @param entity entity
     * @param effect effect object
     * @param maxDuration maximal effect duration
     * @param amplifier effect amplifier
     */
    protected void removeEffect(LivingEntity entity, Effect effect, int maxDuration, int amplifier) {
        EffectInstance effectInstance = entity.getActivePotionEffect(effect);
        if (effectInstance != null && !effectInstance.isAmbient() && effectInstance.getDuration() <= maxDuration && effectInstance.getAmplifier() == amplifier) {
            entity.removePotionEffect(effect);
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
        CompoundNBT compoundnbt = stack.getChildTag("display");
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
        if (!this.enabledDamageConfig.getValue() && (enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.MENDING)) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    /**
     * Copy of {@link ItemStack#damageItem(int, LivingEntity, Consumer)} to enable own damaging of hat items.
     * Added config value to disable damage.
     */
    protected void damageHatItemByOne(ItemStack stack, PlayerEntity entity) {
        if (!this.enabledDamageConfig.getValue()) return;

        if (!entity.world.isRemote && !entity.abilities.isCreativeMode) {
            if (this.isDamageable()) {
                if (stack.attemptDamageItem(1, entity.getRNG(), entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null)) {
                    entity.sendBreakAnimation(EquipmentSlotType.HEAD);
                    stack.shrink(1);
                    entity.addStat(Stats.ITEM_BROKEN.get(this));
                    stack.setDamage(0);
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
    public @Nonnull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot) {
        return HashMultimap.create();
    }

    private static final int MAX_TEXT_LINE_WIDTH = 180;

    /**
     * Workaround for 1.16.2+
     * Forge removed {@link net.minecraftforge.fml.client.gui.GuiUtils::drawHoveringText} call
     * from {@link net.minecraft.client.gui.screen.Screen} class. So, all long texts are not reordered.
     * Reordering by myself.
     * //TODO could be removed when building against Forge 1.16.3-34.1.11 or later
     */
    @OnlyIn(Dist.CLIENT)
    protected void addTextLineToTooltip(@Nonnull List<ITextComponent> tooltip, @Nonnull ITextComponent textLine) {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        int textLineWidth = font.func_238414_a_(textLine);
        if (textLineWidth > MAX_TEXT_LINE_WIDTH) {
            List<ITextProperties> wrappedLine = font.func_238420_b_().func_238362_b_(textLine, MAX_TEXT_LINE_WIDTH, Style.EMPTY);
            for (ITextProperties line : wrappedLine) {
                tooltip.add(new StringTextComponent(line.getString()).mergeStyle(textLine.getStyle()));
            }
        } else {
            tooltip.add(textLine);
        }
    }

    /**
     * Adds "When on head" line to end of tooltip.
     * When hat item has no effect, override this method with an empty method.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //tooltip.add(new StringTextComponent("Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()).mergeStyle(TextFormatting.RED));
        tooltip.add(new StringTextComponent(""));
        tooltip.add((new TranslationTextComponent("item.modifiers." + EquipmentSlotType.HEAD.getName())).mergeStyle(TextFormatting.GRAY));
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        if (this instanceof IUsefulHatModelOwner) {
            if (model == null) {
                model = new UsefulHatModel<>();
            }
            return (A) model;
        }
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if (this instanceof IUsefulHatModelOwner) {
            ResourceLocation resourceLocation = stack.getItem().getRegistryName();
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
        //for baubles
        if (BaublesMod.isLoaded()) {
            AbstractHatItemBaublesCapability cap = new AbstractHatItemBaublesCapability(stack);
            evt.addCapability(BaublesMod.BAUBLES_ITEM_ID, new ICapabilityProvider() {
                final LazyOptional<IBauble> bauble = LazyOptional.of(() -> cap);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return BaublesCapabilities.ITEM_BAUBLE.orEmpty(cap, bauble);
                }
            });
        }
    }

}
