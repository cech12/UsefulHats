package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import cech12.usefulhats.config.ConfigType;
import cech12.usefulhats.helper.IEnabled;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractHatItem extends ArmorItem implements IEnabled, IDyeableArmorItem {

    private final HatArmorMaterial material;
    private final int initColor;
    private final ConfigType.Boolean enabledConfig;
    private final ConfigType.Boolean enabledDamageConfig;

    private final ArrayList<Enchantment> allowedEnchantments = new ArrayList<>();
    private final ArrayList<Enchantment> allowedAdditionalBookEnchantments = new ArrayList<>();

    public AbstractHatItem(String name, HatArmorMaterial material, int initColor, ConfigType.Boolean enabledConfig, ConfigType.Boolean enabledDamageConfig) {
        super(material, EquipmentSlotType.HEAD, (new Properties()).group(ItemGroup.COMBAT));
        this.setRegistryName(new ResourceLocation(UsefulHatsMod.MOD_ID, name));
        this.material = material;
        this.initColor = initColor;
        this.enabledConfig = enabledConfig;
        this.enabledDamageConfig = enabledDamageConfig;
        this.allowedEnchantments.add(Enchantments.UNBREAKING);
        this.allowedEnchantments.add(Enchantments.RESPIRATION);
        this.allowedEnchantments.add(Enchantments.AQUA_AFFINITY);
        this.allowedAdditionalBookEnchantments.add(Enchantments.THORNS);
        this.allowedAdditionalBookEnchantments.add(Enchantments.MENDING);
        this.allowedAdditionalBookEnchantments.add(Enchantments.BINDING_CURSE);
        this.allowedAdditionalBookEnchantments.add(Enchantments.VANISHING_CURSE);
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

    /**
     * Add an allowed enchantment to this item.
     * Standard allowed enchantments are MENDING and UNBREAKING.
     * @param enchantment enchantment to add
     */
    protected void addAllowedEnchantment(Enchantment enchantment) {
        this.allowedEnchantments.add(enchantment);
    }

    /**
     * Add an allowed additional book enchantment to this item.
     * Standard allowed additional book enchantments are BINDING_CURSE and VANISHING_CURSE.
     * @param enchantment enchantment to add
     */
    protected void addAllowedAdditionalBookEnchantment(Enchantment enchantment) {
        this.allowedAdditionalBookEnchantments.add(enchantment);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : this.initColor;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ArrayList<Enchantment> allowedE = new ArrayList<>(allowedEnchantments);
        allowedE.addAll(allowedAdditionalBookEnchantments);
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(book).keySet()) {
            if (!allowedE.contains(enchantment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return this.allowedEnchantments.contains(enchantment);
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
    public @Nonnull Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot) {
        return HashMultimap.create();
    }

    /**
     * Adds "When on head" line to end of tooltip.
     * When hat item has no effect, override this method with an empty method.
     */
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        //tooltip.add(new StringTextComponent("Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()).applyTextStyle(TextFormatting.RED));
        tooltip.add(new StringTextComponent(""));
        tooltip.add((new TranslationTextComponent("item.modifiers." + EquipmentSlotType.HEAD.getName())).applyTextStyle(TextFormatting.GRAY));
    }

}
