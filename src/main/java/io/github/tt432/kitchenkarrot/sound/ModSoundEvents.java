package io.github.tt432.kitchenkarrot.sound;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 **/
public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<SoundEvent> CHOP = register("cocktail.chop");
    public static final RegistryObject<SoundEvent> SHAKER_CLOSE = register("cocktail.shaker_close");
    public static final RegistryObject<SoundEvent> SHAKER_COCKTAIL = register("cocktail.shaker_cocktail");
    public static final RegistryObject<SoundEvent> SHAKER_OPEN = register("cocktail.shaker_open");
    public static final RegistryObject<SoundEvent> SHAKER = register("cocktail.shaker");
    public static final RegistryObject<SoundEvent> COCKTAIL_COMPLETE = register("cocktail.complete");

    protected static RegistryObject<SoundEvent> register(String key) {
        return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(Kitchenkarrot.MOD_ID, key)));
    }
}
