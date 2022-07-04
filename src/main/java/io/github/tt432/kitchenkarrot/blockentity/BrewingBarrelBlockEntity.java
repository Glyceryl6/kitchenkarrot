package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.blockentity.sync.*;
import io.github.tt432.kitchenkarrot.capability.KKItemStackHandler;
import io.github.tt432.kitchenkarrot.menu.BrewingBarrelMenu;
import io.github.tt432.kitchenkarrot.recipes.recipe.BrewingBarrelRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import io.github.tt432.kitchenkarrot.util.ItemHandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author DustW
 **/
public class BrewingBarrelBlockEntity extends MenuBlockEntity {
    FluidTankSyncData input1;
    public KKItemStackHandler input2 = new KKItemStackHandler(this, 6);
    private KKItemStackHandler result = new KKItemStackHandler(this, 1);
    private IntSyncData progress;
    private IntSyncData maxProgress;
    private StringSyncData recipe;
    private BrewingBarrelRecipe currentRecipe;
    private BoolSyncData started;

    public BrewingBarrelBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.BREWING_BARREL.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void syncDataInit(SyncDataManager manager) {
        manager.add(input1 = new FluidTankSyncData("fluid",4000, (f) -> f.getFluid() == Fluids.WATER, true));
        manager.add(progress = new IntSyncData("progress", 0, true));
        manager.add(maxProgress = new IntSyncData("maxProgress", 0, true));
        manager.add(recipe = new StringSyncData("recipe", "", true));
        manager.add(started = new BoolSyncData("started", false, true));
    }

    public BrewingBarrelRecipe getRecipe() {
        return currentRecipe == null && !this.recipe.isEmpty() ?
                currentRecipe = (BrewingBarrelRecipe) level.getRecipeManager()
                        .byKey(new ResourceLocation(recipe.get())).get() : currentRecipe;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            var inputList = ItemHandlerUtils.toList(input2);

            if (fluidEnough() && !canUseRecipe()) {
                level.getRecipeManager().getAllRecipesFor(RecipeTypes.BREWING_BARREL.get())
                        .stream().filter(r -> r.matches(inputList)).forEach(r -> {
                            if (result.extractItem(0, 1, true).isEmpty()) {
                                setRecipe(r);
                            }
                        });
            }
            else if (this.started.get() && progress.get() != 0) {
                if (progress.reduce(1, 0) == 0) {
                    result.insertItem(0, currentRecipe.getResultItem(), false);

                    for (int i = 0; i < input2.getSlots(); i++) {
                        input2.extractItem(i, 1, false);
                    }

                    input1.get().drain(2000, IFluidHandler.FluidAction.EXECUTE);

                    finishBrewing();
                }
            }
            else if (getRecipe() != null) {
                finishBrewing();
            }
        }
    }

    public boolean resultEmpty() {
        return result.getStackInSlot(0).isEmpty();
    }

    public boolean fluidEnough() {
        return input1.get().getFluidAmount() >= 2000;
    }

    public boolean canUseRecipe() {
        return this.getRecipe() != null && this.getRecipe().matches(ItemHandlerUtils.toList(input2));
    }

    public boolean isStarted() {
        return started.get();
    }

    void finishBrewing() {
        started.set(false);
        setRecipe(null);
        setProgress();
    }

    void setRecipe(BrewingBarrelRecipe recipe) {
        this.currentRecipe = recipe;

        if (recipe != null) {
            this.recipe.set(recipe.getId().toString());
        }
        else {
            this.recipe.set("");
        }
    }

    void setProgress() {
        if (canUseRecipe()) {
            this.progress.set(currentRecipe.getCraftingTime());
            this.maxProgress.set(currentRecipe.getCraftingTime());
        }
        else {
            this.progress.set(0);
            this.maxProgress.set(0);
        }
    }

    public void start() {
        if (canUseRecipe()) {
            started.set(true);
            setProgress();
        }
    }

    @Override
    public List<ItemStack> drops() {
        return ItemHandlerUtils.toList(input2, result);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new BrewingBarrelMenu(pContainerId, pInventory, this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> input1.get()));
    }

    public Integer getMaxProgress() {
        return maxProgress.get();
    }

    public Integer getProgress() {
        return progress.get();
    }

    public IItemHandler result() {
        return result;
    }
}
