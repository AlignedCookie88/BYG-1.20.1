package potionstudios.byg.common.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BYGTier implements Tier {
   public static final BYGTier AMETRINE = new BYGTier("ametrine", 4, 64, 16.0F, 0.8F, 25, () -> Ingredient.of(BYGItems.AMETRINE_GEMS.get()));
    public static final BYGTier PENDORITE = new BYGTier("pendorite", 5, 2500, 10.0F, 4.0F, 15, () -> Ingredient.of(BYGItems.PENDORITE_SCRAPS.get()));

    private final String id;
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    public BYGTier(String id, int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.id = id;
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
    }

    public int getUses() {
        return this.maxUses;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }


    public String getId() {
        return id;
    }
}