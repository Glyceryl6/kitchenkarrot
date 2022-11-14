package io.github.tt432.kitchenkarrot.glm;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.datagen.loottable.ModGlobalLootModifierSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModGlobalLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<ModGlobalLootModifierSerializer> GLOBAL_LOOT_MODIFIER_SERIALIZER= GLM.register("piglin_barter_loot_modifier",ModGlobalLootModifierSerializer::new);
}
