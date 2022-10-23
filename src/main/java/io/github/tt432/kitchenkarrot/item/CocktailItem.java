package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.client.cocktail.CocktailList;
import io.github.tt432.kitchenkarrot.recipes.object.EffectStack;
import io.github.tt432.kitchenkarrot.recipes.recipe.CocktailRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author DustW
 **/
@ParametersAreNonnullByDefault
public class CocktailItem extends Item {

    public static final ResourceLocation UNKNOWN_COCKTAIL = new ResourceLocation(Kitchenkarrot.MOD_ID, "unknown");

    public CocktailItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> itemStackNonNullList) {
        if (p_41391_ == ModItems.COCKTAIL_TAB) {
            for (String cocktail : CocktailList.INSTANCE.cocktails) {
                ItemStack stack = new ItemStack(this);
                setCocktail(stack, new ResourceLocation(cocktail));
                itemStackNonNullList.add(stack);
            }
        }
    }

    public static ItemStack unknownCocktail() {
        var stack = new ItemStack(ModItems.COCKTAIL.get());
        setCocktail(stack, UNKNOWN_COCKTAIL);
        return stack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            ResourceLocation cocktail = getCocktail(stack);
            if (cocktail != null) {
                CocktailRecipe recipe = get(pLevel, cocktail);
                if (recipe != null) {
                    player.getFoodData().eat(recipe.content.hunger, recipe.content.saturation);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    for (EffectStack effectStack : recipe.content.getEffect()) {
                        player.addEffect(effectStack.get());
                    }
                } else if (cocktail.equals(UNKNOWN_COCKTAIL)) {
                    List<MobEffect> array = ForgeRegistries.MOB_EFFECTS.getValues().stream()
                            .filter(eff -> "minecraft".equals(Objects.requireNonNull(eff.getRegistryName()).getNamespace()) &&
                                    eff != MobEffects.HEAL && eff != MobEffects.HARM &&
                                    eff != MobEffects.BAD_OMEN && eff != MobEffects.HERO_OF_THE_VILLAGE).toList();
                    player.addEffect(new MobEffectInstance(array.get(pLevel.random.nextInt(array.size())), 20 * 5, 0));
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
            }
        }

        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        ResourceLocation name = getCocktail(stack);

        if (name != null) {
            return name.toString().replace(":", ".");
        }

        return super.getDescriptionId(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        ResourceLocation name = getCocktail(stack);
        if (name != null && level != null) {
            tooltip.add(new TranslatableComponent(name.toString().replace(":", ".") + ".tooltip"));
            CocktailRecipe recipe = get(level, Objects.requireNonNull(getCocktail(stack)));
            if (recipe != null) {
                tooltip.add(new TranslatableComponent("item.cocktail.author", recipe.author));
            }
        }
    }

    @Nullable
    public static ResourceLocation getCocktail(ItemStack itemStack) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("cocktail")) {
            return new ResourceLocation(itemStack.getTag().getString("cocktail"));
        }

        return null;
    }

    public static void setCocktail(ItemStack itemStack, ResourceLocation name) {
        itemStack.getOrCreateTag().putString("cocktail", name.toString());
    }

    @Nullable
    public static CocktailRecipe get(Level level, ResourceLocation resourceLocation) {
        Optional<? extends Recipe<?>> result = level.getRecipeManager().byKey(resourceLocation);

        if (result.isPresent() && result.get().getType() == RecipeTypes.COCKTAIL.get()) {
            return (CocktailRecipe) result.get();
        }

        return null;
    }

}