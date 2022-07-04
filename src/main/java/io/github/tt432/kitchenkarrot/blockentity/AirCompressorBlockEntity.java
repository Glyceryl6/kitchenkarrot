package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.blockentity.sync.SyncDataManager;
import io.github.tt432.kitchenkarrot.capability.KKItemStackHandler;
import io.github.tt432.kitchenkarrot.menu.AirCompressorMenu;
import io.github.tt432.kitchenkarrot.blockentity.sync.IntSyncData;
import io.github.tt432.kitchenkarrot.blockentity.sync.StringSyncData;
import io.github.tt432.kitchenkarrot.recipes.recipe.AirCompressorRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeManager;
import io.github.tt432.kitchenkarrot.tag.ModItemTags;
import io.github.tt432.kitchenkarrot.util.ItemHandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class AirCompressorBlockEntity extends MenuBlockEntity {
    /** 原料 / 容器输入 */
    ItemStackHandler input1 = new KKItemStackHandler(this, 5) {
        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            if (!isItemValid(slot, stack)) {return;}
            super.setStackInSlot(slot, stack);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot != 4 || stack.is(ModItemTags.CONTAINER_ITEM);
        }
    };
    /** 燃料输入 */
    ItemStackHandler input2 = new KKItemStackHandler(this, 1) {
        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            if (!isItemValid(slot, stack)) {return;}
            super.setStackInSlot(slot, stack);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack, null) != 0;
        }
    };
    /** 成品输出 */
    ItemStackHandler output = new KKItemStackHandler(this, 1);

    public AirCompressorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.AIR_COMPRESSOR.get(), pWorldPosition, pBlockState);
    }

    IntSyncData progress;
    IntSyncData maxBurnTime;
    IntSyncData burnTime;
    StringSyncData recipeId;
    AirCompressorRecipe recipe;

    @Override
    protected void syncDataInit(SyncDataManager manager) {
        manager.add(burnTime = new IntSyncData("burn_time", 0, true));
        manager.add(recipeId = new StringSyncData("recipe", "", true));
        manager.add(progress = new IntSyncData("progress", 0, true));
        manager.add(maxBurnTime = new IntSyncData("max_burn_time", 0, true));
    }

    public AirCompressorRecipe getRecipe() {
        return recipe == null && !this.recipeId.isEmpty() ?
                recipe = (AirCompressorRecipe) level.getRecipeManager()
                        .byKey(new ResourceLocation(recipeId.get())).get() : recipe;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            return;
        }

        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            items.add(input1.getStackInSlot(i));
        }

        if (progress.get() == 0) {
            // 尚未开始
            if (!recipeValid(items)) {
                if (burnTime.get() > 0 || !input2.getStackInSlot(0).isEmpty()) {
                    var recipeList = RecipeManager.getAirCompressorRecipe(level)
                            .stream().filter(r -> r.matches(items) && r.testContainer(input1.getStackInSlot(4))).toArray();

                    for (Object obj : recipeList) {
                        var r = (AirCompressorRecipe) obj;

                        if (output.insertItem(0, r.getResultItem(), true).isEmpty()) {
                            setRecipe(r);
                            progress.set(recipe.getCraftingTime());

                            if (burnTime.get() == 0) {
                                addFuel();
                            }

                            setChanged();

                            return;
                        }
                    }
                }
            }
            else {
                // 结束
                for (int i = 0; i < 4; i++) {
                    input1.extractItem(i, 1, false);
                }

                if (getRecipe().getContainer() != null) {
                    input1.extractItem(4, 1, false);
                }

                output.insertItem(0, recipe.getResultItem(), false);

                stop();
            }
        }
        else if (burnTime.get() != 0 && recipeValid(items)) {
            // 还在工作
            progress.reduce(1, 0);
        }
        else {
            // 中止
            stop();
        }

        if (burnTime.reduce(1, 0) == 0 && recipeValid(items)) {
            // 燃料耗尽
            addFuel();
        }
    }

    protected boolean recipeValid(List<ItemStack> items) {
        return getRecipe() != null && getRecipe().matches(items);
    }

    protected void setRecipe(AirCompressorRecipe recipe) {
        this.recipe = recipe;
        this.recipeId.set(recipe.getId().toString());
    }

    protected void addFuel() {
        burnTime.set(ForgeHooks.getBurnTime(input2.getStackInSlot(0), null));
        var fuel = input2.extractItem(0, 1, false);
        if (!fuel.getContainerItem().isEmpty()) {
            input2.insertItem(0, fuel.getContainerItem(), false);
        }
        maxBurnTime.set(burnTime.get());

        setChanged();
    }

    protected void stop() {
        recipe = null;
        recipeId.set("");
        progress.set(0);

        setChanged();
    }

    @Override
    public List<ItemStack> drops() {
        return ItemHandlerUtils.toList(input1, input2, output);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return side == null ? LazyOptional.empty() : CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
                .orEmpty(cap, LazyOptional.of(() -> switch (side) {
                    case DOWN -> output;
                    case UP -> input1;
                    case NORTH, SOUTH, WEST, EAST -> input2;
                }));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new AirCompressorMenu(pContainerId, pInventory, this);
    }

    public int getProgress() {
        return this.progress.get();
    }

    public int getMaxProgress() {
        return getRecipe() == null ? 0 : recipe.getCraftingTime();
    }

    public int getBurnTime() {
        return burnTime.get();
    }

    public int getMaxBurnTime() {
        return maxBurnTime.get();
    }

    public ItemStackHandler getInput1() {
        return input1;
    }

    public ItemStackHandler getInput2() {
        return input2;
    }

    public ItemStackHandler getOutput() {
        return output;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        var input1 = getInput1().serializeNBT();
        var input2 = getInput2().serializeNBT();
        var output = getOutput().serializeNBT();

        pTag.put("input1", input1);
        pTag.put("input2", input2);
        pTag.put("output", output);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (!isSyncTag(pTag)) {
            input1.deserializeNBT(pTag.getCompound("input1"));
            input2.deserializeNBT(pTag.getCompound("input2"));
            output.deserializeNBT(pTag.getCompound("output"));
        }
    }
}
