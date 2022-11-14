package io.github.tt432.kitchenkarrot.datagen.loottable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddLootTableModifier extends LootModifier {
    public final LootItemCondition[] conditions;
    private final Item item;
    private final int weight;

    public Item getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected AddLootTableModifier(LootItemCondition[] conditionsIn, Item item, int weight) {
        super(conditionsIn);
        this.conditions = conditionsIn;
        this.weight = weight;
        this.item = item;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //TODO 现在是手动模拟一个权重，以后可以考虑换一种实现。使用mixin，或者参考Blueprint库
        if (context.getRandom().nextInt(900) < weight) {
            generatedLoot.clear();
            generatedLoot.add(item.getDefaultInstance());
        }
        return generatedLoot;
    }

}
