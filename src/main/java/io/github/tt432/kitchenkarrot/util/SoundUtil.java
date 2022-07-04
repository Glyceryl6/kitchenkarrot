package io.github.tt432.kitchenkarrot.util;

import io.github.tt432.kitchenkarrot.sound.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * @author DustW
 **/
public class SoundUtil {
    public static void shakerSound(Player pPlayer, Level pLevel) {
        Minecraft.getInstance().getSoundManager().play(
                new SimpleSoundInstance(
                        ModSoundEvents.SHAKER.get().getLocation(),
                        pPlayer.getSoundSource(),
                        0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F,
                        true, 0,
                        SoundInstance.Attenuation.LINEAR,
                        pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        true)
        );
    }
}
