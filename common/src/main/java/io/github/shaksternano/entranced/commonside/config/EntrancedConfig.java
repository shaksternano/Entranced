package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import io.github.shaksternano.entranced.commonside.registry.EntrancedEnchantments;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The mod's config class.
 */
@SuppressWarnings({"CanBeFinal", "FieldCanBeLocal", "FieldMayBeFinal"})
@Config(name = Entranced.MOD_ID)
@Config.Gui.Background(Config.Gui.Background.TRANSPARENT)
public class EntrancedConfig implements ConfigData {

    @ConfigEntry.Category("enchantment")
    @ConfigEntry.Gui.CollapsibleObject
    private final AutoSwingCollapsible autoswing = new AutoSwingCollapsible();

    @ConfigEntry.Category("enchantment")
    @ConfigEntry.Gui.CollapsibleObject
    private final ImperishableCollapsible imperishable = new ImperishableCollapsible();

    @ConfigEntry.Category("enchantment")
    @ConfigEntry.Gui.CollapsibleObject
    private final FrenzyCollapsible frenzy = new FrenzyCollapsible();

    @ConfigEntry.Category("enchantment")
    @ConfigEntry.Gui.CollapsibleObject
    private final InfinityCollapsible infinity = new InfinityCollapsible();

    @ConfigEntry.Category("enchantment")
    @ConfigEntry.Gui.CollapsibleObject
    private final MlgCollapsible mlg = new MlgCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    private boolean enchantingCatalystEnabled = true;

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final MeleeCatalystCollapsible meleeCatalyst = new MeleeCatalystCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final RangedCatalystCollapsible rangedCatalyst = new RangedCatalystCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final DefensiveCatalystCollapsible defensiveCatalyst = new DefensiveCatalystCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final MagicCatalystCollapsible magicCatalyst = new MagicCatalystCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final UtilityCatalystCollapsible utilityCatalyst = new UtilityCatalystCollapsible();

    @ConfigEntry.Category("enchantingCatalyst")
    @ConfigEntry.Gui.CollapsibleObject
    private final MiscCatalystCollapsible miscCatalyst = new MiscCatalystCollapsible();

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("enchantment")
    @Comment("Block entities keep enchantments when\nplaced and picked back up.\nIt is recommended to leave this on the default value.\n\nDefault value is true.")
    private boolean blockEntitiesStoreEnchantments = true;

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("enchantment")
    @Comment("Enchantments are retained in more situations.\nIt is recommended to leave this on the default value.\n\nDefault value is true.")
    private boolean retainEnchantmentsMoreOften = true;

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("enchantment")
    @Comment("Debug mode. Used for testing the mod.\nIt is recommended to leave this on the default value.\n\nDefault value is false.")
    private boolean debugMode = false;

    private static class AutoSwingCollapsible {

        @Comment("Is this enchantment enabled?\nMinecraft must be restarted for\nthis change to take full effect.\n\nDefault value is true.")
        private boolean enabled = true;

        @Comment("Rarity of the enchantment.\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nOptions:\n  \"COMMON\"\n  \"UNCOMMON\"\n  \"RARE\"\n  \"VERY_RARE\"\n\nDefault value is \"RARE\".")
        private Enchantment.Rarity rarity = Enchantment.Rarity.RARE;

        @Comment("Should this be a treasure enchant?\n\nDefault value is false.")
        private boolean treasure = false;

        @Comment("Should villagers sell enchanted\nbooks with this enchantment?\n\nDefault value is true.")
        private boolean soldByVillagers = true;

        @Comment("Minimum experience level required to get\nin an enchanting table.\n\nDefault value is 10.")
        private int minPower = 10;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 50.")
        private int maxPowerAboveMin = 50;
    }

    private static class ImperishableCollapsible {

        @Comment("Is this enchantment enabled?\nMinecraft must be restarted for\nthis change to take full effect.\n\nDefault value is true.")
        private boolean enabled = true;

        @Comment("Rarity of the enchantment.\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nOptions:\n  \"COMMON\"\n  \"UNCOMMON\"\n  \"RARE\"\n  \"VERY_RARE\"\n\nDefault value is \"RARE\".")
        private Enchantment.Rarity rarity = Enchantment.Rarity.RARE;

        @Comment("Should this be a treasure enchant?\n\nDefault value is false.")
        private boolean treasure = false;

        @Comment("Should villagers sell enchanted\nbooks with this enchantment?\n\nDefault value is true.")
        private boolean soldByVillagers = true;

        @Comment("Minimum experience level required to get\nin an enchanting table.\n\nDefault value is 15.")
        private int minPower = 15;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 50.")
        private int maxPowerAboveMin = 50;

        @Comment("Should Imperishable prevent items from despawning?\n\nDefault value is true.")
        private boolean preventsDespawn = true;

        @Comment("Should Imperishable prevent items from being destroyed?\n\nDefault value is true.")
        private boolean protectsFromDamage = true;

        @Comment("Should Imperishable prevent items from\nbeing destroyed in the void?\n\nDefault value is true.")
        private boolean protectsFromVoid = true;

        @Comment("Should Imperishable prevent items from breaking?\nIf true, items don't break when they reach\n0 durability, but rather lose any special\nproperties, such as increased mining speed\non a pickaxe, until they are repaired.\n\nDefault value is true.")
        private boolean preventsBreaking = true;

        @Comment("Is the Imperishable enchantment needed\nto prevent tools from breaking?\n\nDefault value is true.")
        private boolean enchantmentNeededToPreventBreaking = true;

        @Comment("IDs of items that aren't\nprotected by Imperishable.\nFor example \"minecraft:stone\".\n\nEmpty by default.")
        private final List<String> globalBlacklist = new ArrayList<>();

        @Comment("IDs of items that aren't protected\nby Imperishable from despawning.\nFor example \"minecraft:stone\".\n\nEmpty by default.")
        private final List<String> despawnProtectionBlacklist = new ArrayList<>();

        @Comment("IDs of items that aren't protected by Imperishable\nfrom being destroyed by things like fire.\nFor example \"minecraft:stone\".\n\nEmpty by default.")
        private final List<String> damageProtectionBlacklist = new ArrayList<>();

        @Comment("IDs of items that aren't protected by\nImperishable from falling into the void.\nFor example \"minecraft:stone\".\n\nEmpty by default.")
        private final List<String> voidProtectionBlacklist = new ArrayList<>();

        @Comment("IDs of items that aren't protected\nby Imperishable from breaking.\nFor example \"minecraft:stone\".\n\nEmpty by default.")
        private final List<String> breakProtectionBlacklist = new ArrayList<>();
    }

    private static class FrenzyCollapsible {

        @Comment("Is this enchantment enabled?\nMinecraft must be restarted for\nthis change to take full effect.\n\nDefault value is true.")
        private boolean enabled = true;

        @Comment("Maximum level of the enchantment\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nDefault value is 5.")
        private int maxLevel = 5;

        @Comment("Rarity of the enchantment.\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nOptions:\n  \"COMMON\"\n  \"UNCOMMON\"\n  \"RARE\"\n  \"VERY_RARE\"\n\nDefault value is \"COMMON\".")
        private Enchantment.Rarity rarity = Enchantment.Rarity.COMMON;

        @Comment("Should this be a treasure enchant?\n\nDefault value is false.")
        private boolean treasure = false;

        @Comment("Should villagers sell enchanted\nbooks with this enchantment?\n\nDefault value is true.")
        private boolean soldByVillagers = true;

        @Comment("Minimum experience level required to get\nin an enchanting table.\n\nDefault value is 5.")
        private int minPower = 5;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 100.")
        private int maxPowerAboveMin = 100;

        @Comment("The number of extra full-strength attacks\nper second Frenzy gives per level.\n\nDefault value is 0.2.")
        private double extraAttackSpeedPerLevel = 0.2D;

        @Comment("Is Frenzy mutually exclusive with damage\nenchantments such as Sharpness?\n\nDefault value is true.")
        private boolean mutuallyExclusiveWithDamageEnchantments = true;
    }

    private static class InfinityCollapsible {

        @Comment("Is the Infinity enchantment allowed on buckets?\n\nDefault value is true.")
        private boolean allowedOnBuckets = true;

        @Comment("IDs of fluids that are affected by Infinity.\nFor example \"minecraft:lava\".\n\nContains \"minecraft:water\" by default.")
        private final List<String> fluidWhitelist = new ArrayList<>();

        @Comment("IDs of blocks that are placed by\nbuckets that are affected by Infinity.\nFor example \"minecraft:powder_snow\".\n\nEmpty by default.")
        private final List<String> blockWhitelist = new ArrayList<>();

        @Comment("IDs of bucket items that are affected by Infinity.\nFor example \"minecraft:milk_bucket\".\n\nEmpty by default.")
        private final List<String> itemWhitelist = new ArrayList<>();
    }

    private static class MlgCollapsible {

        @Comment("Is this enchantment enabled?\nMinecraft must be restarted for\nthis change to take full effect.\n\nDefault value is true.")
        private boolean enabled = true;

        @Comment("Rarity of the enchantment.\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nOptions:\n  \"COMMON\"\n  \"UNCOMMON\"\n  \"RARE\"\n  \"VERY_RARE\"\n\nDefault value is \"RARE\".")
        private Enchantment.Rarity rarity = Enchantment.Rarity.COMMON;

        @Comment("Should this be a treasure enchant?\n\nDefault value is false.")
        private boolean treasure = false;

        @Comment("Should villagers sell enchanted\nbooks with this enchantment?\n\nDefault value is true.")
        private boolean soldByVillagers = true;

        @Comment("Minimum experience level required to get\nin an enchanting table.\n\nDefault value is 10.")
        private int minPower = 5;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 50.")
        private int maxPowerAboveMin = 50;
    }

    private static class MeleeCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    private static class RangedCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    private static class DefensiveCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    private static class MagicCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    private static class UtilityCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    private static class MiscCatalystCollapsible {

        private final List<String> affectedEnchantments = new ArrayList<>();

        private final List<String> consumableItems = new ArrayList<>();

        private final List<String> unconsumableItems = new ArrayList<>();
    }

    /**
     * Sets the default collection values and saves it to the config file.
     * @param configHolder The config holder associated with this config object.
     * @throws IllegalArgumentException if the config object in {@code configHolder} is not equal to this config object.
     */
    public void initCollectionDefaultValues(ConfigHolder<EntrancedConfig> configHolder) {
        if (configHolder.getConfig() == this) {
            setDefaultCollectionValues(infinity.fluidWhitelist,
                    "minecraft:water"
            );

            setDefaultCollectionValues(meleeCatalyst.affectedEnchantments,
                    "minecraft:sharpness",
                    "minecraft:smite",
                    "minecraft:bane_of_arthropods",

                    "minecraft:sweeping",
                    "minecraft:knockback",
                    "minecraft:fire_aspect",

                    "minecraft:impaling",

                    EntrancedEnchantments.AUTOSWING.getId(),
                    EntrancedEnchantments.FRENZY.getId()
            );

            setDefaultCollectionValues(rangedCatalyst.affectedEnchantments,
                    "minecraft:power",
                    "minecraft:punch",
                    "minecraft:infinity",
                    "minecraft:flame",

                    "minecraft:quick_charge",
                    "minecraft:piercing",
                    "minecraft:multishot",

                    "minecraft:loyalty",
                    "minecraft:channeling",
                    "minecraft:riptide"
            );

            setDefaultCollectionValues(defensiveCatalyst.affectedEnchantments,
                    "minecraft:protection",
                    "minecraft:projectile_protection",
                    "minecraft:fire_protection",
                    "minecraft:blast_protection",

                    "minecraft:feather_falling",
                    "minecraft:thorns"
            );

            setDefaultCollectionValues(utilityCatalyst.affectedEnchantments,
                    "minecraft:unbreaking",
                    "minecraft:mending",

                    "minecraft:efficiency",
                    "minecraft:silk_touch",
                    "minecraft:fortune",

                    "minecraft:looting",

                    "minecraft:aqua_affinity",
                    "minecraft:depth_strider",
                    "minecraft:frost_walker",
                    "minecraft:respiration",
                    "minecraft:soul_speed",

                    "minecraft:luck_of_the_sea",
                    "minecraft:lure",

                    EntrancedEnchantments.IMPERISHABLE.getId(),
                    EntrancedEnchantments.MLG.getId()
            );

            setDefaultCollectionValues(miscCatalyst.affectedEnchantments,
                    "minecraft:vanishing_curse",
                    "minecraft:binding_curse"
            );

            setDefaultCollectionValues(meleeCatalyst.consumableItems,
                    "minecraft:blaze_powder"
            );

            setDefaultCollectionValues(meleeCatalyst.unconsumableItems,
                    "minecraft:enchanted_golden_apple"
            );

            configHolder.save();
        } else {
            throw new IllegalArgumentException("This config object does not match the config object in the config holder!");
        }
    }

    /**
     * Clears the contents of a collection and fills it with default values.
     * @param collection The collection to clear and add elements to.
     * @param elements The elements to add to the collection.
     */
    private static void setDefaultCollectionValues(Collection<String> collection, String... elements) {
        collection.clear();
        collection.addAll(List.of(elements));
    }

    /**
     * Updates collections that are filled with objects corresponding to {@link String} IDs in the config.
     */
    public static void updateConfigCollections() {
        InfinityBucketWhitelists.updateWhitelists();
        ImperishableBlacklists.updateBlacklists();
        EnchantingCatalystConfig.updateCatalystConfigCollections();
    }

    // Accessors

    // Autoswing
    public boolean isAutoswingEnabled() {
        return autoswing.enabled;
    }

    public Enchantment.Rarity getAutoswingRarity() {
        return autoswing.rarity;
    }

    public boolean isAutoswingTreasure() {
        return autoswing.treasure;
    }

    public boolean isAutoswingSoldByVillagers() {
        return autoswing.soldByVillagers;
    }

    public int getAutoswingMinPower() {
        return autoswing.minPower;
    }

    public int getAutoswingMaxPowerAboveMin() {
        return autoswing.maxPowerAboveMin;
    }

    // Imperishable
    public boolean isImperishableEnabled() {
        return imperishable.enabled;
    }

    public Enchantment.Rarity getImperishableRarity() {
        return imperishable.rarity;
    }

    public boolean isImperishableTreasure() {
        return imperishable.treasure;
    }

    public boolean isImperishableSoldByVillagers() {
        return imperishable.soldByVillagers;
    }

    public int getImperishableMinPower() {
        return imperishable.minPower;
    }

    public int getImperishableMaxPowerAboveMin() {
        return imperishable.maxPowerAboveMin;
    }

    public boolean isImperishablePreventsDespawn() {
        return imperishable.preventsDespawn;
    }

    public boolean isImperishableProtectsFromDamage() {
        return imperishable.protectsFromDamage;
    }

    public boolean isImperishableProtectsFromVoid() {
        return imperishable.protectsFromVoid;
    }

    public boolean isImperishablePreventsBreaking() {
        return imperishable.preventsBreaking;
    }

    public boolean isEnchantmentNotNeededToPreventBreaking() {
        return !imperishable.enchantmentNeededToPreventBreaking;
    }

    public List<String> getImperishableGlobalBlacklist() {
        return imperishable.globalBlacklist;
    }

    public List<String> getImperishableDespawnProtectionBlacklist() {
        return imperishable.despawnProtectionBlacklist;
    }

    public List<String> getImperishableDamageProtectionBlacklist() {
        return imperishable.damageProtectionBlacklist;
    }

    public List<String> getImperishableVoidProtectionBlacklist() {
        return imperishable.voidProtectionBlacklist;
    }

    public List<String> getImperishableBreakProtectionBlacklist() {
        return imperishable.breakProtectionBlacklist;
    }

    // Frenzy
    public boolean isFrenzyEnabled() {
        return frenzy.enabled;
    }

    public Enchantment.Rarity getFrenzyRarity() {
        return frenzy.rarity;
    }

    public boolean isFrenzyTreasure() {
        return frenzy.treasure;
    }

    public boolean isFrenzySoldByVillagers() {
        return frenzy.soldByVillagers;
    }

    public int getFrenzyMinPower() {
        return frenzy.minPower;
    }

    public int getFrenzyMaxPowerAboveMin() {
        return frenzy.maxPowerAboveMin;
    }

    public double getFrenzyExtraAttackSpeedPerLevel() {
        return frenzy.extraAttackSpeedPerLevel;
    }

    public int getFrenzyMaxLevel() {
        return frenzy.maxLevel;
    }

    public boolean isFrenzyMutuallyExclusiveWithDamageEnchantments() {
        return frenzy.mutuallyExclusiveWithDamageEnchantments;
    }

    // Infinity
    public boolean isInfinityAllowedOnBuckets() {
        return infinity.allowedOnBuckets;
    }

    public List<String> getInfinityFluidWhitelist() {
        return infinity.fluidWhitelist;
    }

    public List<String> getInfinityBlockWhitelist() {
        return infinity.blockWhitelist;
    }

    public List<String> getInfinityItemWhitelist() {
        return infinity.itemWhitelist;
    }

    public boolean isMlgEnabled() {
        return mlg.enabled;
    }

    public Enchantment.Rarity getMlgRarity() {
        return mlg.rarity;
    }

    public boolean isMlgTreasure() {
        return mlg.treasure;
    }

    public boolean isMlgSoldByVillagers() {
        return mlg.soldByVillagers;
    }

    public int getMlgMinPower() {
        return mlg.minPower;
    }

    public int getMlgMaxPowerAboveMin() {
        return mlg.maxPowerAboveMin;
    }

    // Enchanting catalyst
    public boolean isEnchantingCatalystEnabled() {
        return enchantingCatalystEnabled;
    }

    public List<String> getMeleeCatalystAffectedEnchantments() {
        return meleeCatalyst.affectedEnchantments;
    }

    public List<String> getMeleeCatalystConsumableItems() {
        return meleeCatalyst.consumableItems;
    }

    public List<String> getMeleeCatalystUnconsumableItems() {
        return meleeCatalyst.unconsumableItems;
    }

    public List<String> getRangedCatalystAffectedEnchantments() {
        return rangedCatalyst.affectedEnchantments;
    }

    public List<String> getRangedCatalystConsumableItems() {
        return rangedCatalyst.consumableItems;
    }

    public List<String> getRangedCatalystUnconsumableItems() {
        return rangedCatalyst.unconsumableItems;
    }

    public List<String> getDefensiveCatalystAffectedEnchantments() {
        return defensiveCatalyst.affectedEnchantments;
    }

    public List<String> getDefensiveCatalystConsumableItems() {
        return defensiveCatalyst.consumableItems;
    }

    public List<String> getDefensiveCatalystUnconsumableItems() {
        return defensiveCatalyst.unconsumableItems;
    }

    public List<String> getMagicCatalystAffectedEnchantments() {
        return magicCatalyst.affectedEnchantments;
    }

    public List<String> getMagicCatalystConsumableItems() {
        return magicCatalyst.consumableItems;
    }

    public List<String> getMagicCatalystUnconsumableItems() {
        return magicCatalyst.unconsumableItems;
    }

    public List<String> getUtilityCatalystAffectedEnchantments() {
        return utilityCatalyst.affectedEnchantments;
    }

    public List<String> getUtilityCatalystConsumableItems() {
        return utilityCatalyst.consumableItems;
    }

    public List<String> getUtilityCatalystUnconsumableItems() {
        return utilityCatalyst.unconsumableItems;
    }

    public List<String> getMiscCatalystAffectedEnchantments() {
        return miscCatalyst.affectedEnchantments;
    }

    public List<String> getMiscCatalystConsumableItems() {
        return miscCatalyst.consumableItems;
    }

    public List<String> getMiscCatalystUnconsumableItems() {
        return miscCatalyst.unconsumableItems;
    }

    // Miscellaneous
    public boolean isBlockEntitiesStoreEnchantments() {
        return blockEntitiesStoreEnchantments;
    }

    public boolean isRetainEnchantmentsMoreOften() {
        return retainEnchantmentsMoreOften;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
