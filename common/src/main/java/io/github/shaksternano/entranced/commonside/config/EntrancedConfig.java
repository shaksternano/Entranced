package io.github.shaksternano.entranced.commonside.config;

import io.github.shaksternano.entranced.commonside.Entranced;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"CanBeFinal", "FieldCanBeLocal", "FieldMayBeFinal"})
@Config(name = Entranced.MOD_ID)
@Config.Gui.Background(Config.Gui.Background.TRANSPARENT)
public final class EntrancedConfig implements ConfigData {

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

    private static class AutoSwingCollapsible {
        @Comment("Is this enchantment enabled?\nMinecraft must be restarted for\nthis change to take full effect.\n\nDefault value is true.")
        private boolean enabled = true;

        @Comment("Rarity of the enchantment.\nMinecraft must be restarted for a\nchange of this option to take effect.\n\nOptions:\n  \"COMMON\"\n  \"UNCOMMON\"\n  \"RARE\"\n  \"VERY_RARE\"\n\nDefault value is \"RARE\".")
        private Enchantment.Rarity rarity = Enchantment.Rarity.RARE;

        @Comment("Should this be a treasure enchant?\n\nDefault value is false.")
        private boolean treasure = false;

        @Comment("Should villagers sell enchanted\nbooks with this enchantment?\n\nDefault value is true.")
        private boolean soldByVillagers = true;

        @Comment("Minimum experience level required to get\n in an enchanting table.\n\nDefault value is 10.")
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

        @Comment("Minimum experience level required to get\n in an enchanting table.\n\nDefault value is 15.")
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

    private static final class FrenzyCollapsible {

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

        @Comment("Minimum experience level required to get\n in an enchanting table.\n\nDefault value is 5.")
        private int minPower = 5;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 100.")
        private int maxPowerAboveMin = 100;

        @Comment("The number of extra full-strength attacks\nper second Frenzy gives per level.\n\nDefault value is 0.2.")
        private double extraAttackSpeedPerLevel = 0.2D;

        @Comment("Is Frenzy mutually exclusive with damage\nenchantments such as Sharpness?\n\nDefault value is true.")
        private boolean mutuallyExclusiveWithDamageEnchantments = true;
    }

    private static final class InfinityCollapsible {

        @Comment("Is the Infinity enchantment allowed on buckets?\n\nDefault value is true.")
        private boolean allowedOnBuckets = true;

        @Comment("Is water affected by Infinity?\n\nDefault value is true.")
        private boolean infinityAffectsWater = true;

        @Comment("IDs of fluids that are affected by Infinity.\nFor example \"minecraft:lava\".\n\nEmpty by default.")
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

        @Comment("Minimum experience level required to get\n in an enchanting table.\n\nDefault value is 10.")
        private int minPower = 5;

        @Comment("Maximum number of levels above the minimum\nlevel to get in an enchanting table.\n\nDefault value is 50.")
        private int maxPowerAboveMin = 50;
    }

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("misc")
    @Comment("Block entities keep enchantments when\nplaced and picked back up.\nIt is recommended to leave this on the default value.\n\nDefault value is true.")
    private boolean blockEntitiesStoreEnchantments = true;

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("misc")
    @Comment("Enchantments are retained in more situations.\nIt is recommended to leave this on the default value.\n\nDefault value is true.")
    private boolean retainEnchantmentsMoreOften = true;

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("misc")
    @Comment("Debug mode. Used for testing the mod.\nIt is recommended to leave this on the default value.\n\nDefault value is false.")
    private boolean debugMode = false;

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

    public boolean isInfinityAffectsWater() {
        return infinity.infinityAffectsWater;
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
