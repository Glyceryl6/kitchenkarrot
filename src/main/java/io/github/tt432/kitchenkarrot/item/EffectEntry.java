package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class EffectEntry {
    public Supplier<MobEffectInstance> effect;
    public float probability;

    private EffectEntry(Supplier<MobEffectInstance> effect, float probability) {
        this.effect = effect;
        this.probability = probability;
    }

    static EffectEntry of(RegistryObject<MobEffect> effect, int time, float probability) {
        return new EffectEntry(() -> new MobEffectInstance(effect.get(), time * 20), probability);
    }

    static EffectEntry of(RegistryObject<MobEffect> effect, int time, int level, float probability) {
        return new EffectEntry(() -> new MobEffectInstance(effect.get(), time * 20, level - 1), probability);
    }

    static EffectEntry of(MobEffect effect, int time, float probability) {
        return new EffectEntry(() -> new MobEffectInstance(effect, time * 20), probability);
    }

    static EffectEntry of(MobEffect effect, int time, int level, float probability) {
        return new EffectEntry(() -> new MobEffectInstance(effect, time * 20, level - 1), probability);
    }
}
