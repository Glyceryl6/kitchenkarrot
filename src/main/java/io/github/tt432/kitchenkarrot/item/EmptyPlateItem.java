package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.client.plate.PlateList;
import io.github.tt432.kitchenkarrot.util.json.JsonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class EmptyPlateItem extends Item {

    public EmptyPlateItem(Properties properties) {
        super(properties);
    }

    @NotNull
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionResult = this.place(new BlockPlaceContext(context));
        if (!interactionResult.consumesAction() && this.isEdible() && context.getPlayer() != null) {
            InteractionResult interactionResult1 = this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
            return interactionResult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionResult1;
        } else {
            return interactionResult;
        }
    }

    public InteractionResult place(BlockPlaceContext context) {
        if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockPlaceContext = this.updatePlacementContext(context);
            if (blockPlaceContext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(blockPlaceContext);
                if (blockState == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockPlaceContext, blockState)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockPos = blockPlaceContext.getClickedPos();
                    Level level = blockPlaceContext.getLevel();
                    Player player = blockPlaceContext.getPlayer();
                    ItemStack itemStack = blockPlaceContext.getItemInHand();
                    BlockState blockState1 = level.getBlockState(blockPos);
                    if (blockState1.is(blockState.getBlock())) {
                        blockState1 = this.updateBlockStateFromTag(blockPos, level, itemStack, blockState1);
                        this.updateCustomBlockEntityTag(blockPos, level, player, itemStack);
                        blockState1.getBlock().setPlacedBy(level, blockPos, blockState1, player, itemStack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                        }
                    }

                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
                    SoundType soundType = blockState1.getSoundType(level, blockPos, context.getPlayer());
                    float volume = (soundType.getVolume() + 1.0F) / 2.0F;
                    float pitch = soundType.getPitch() * 0.8F;
                    level.playSound(player, blockPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, volume, pitch);
                    if (player == null || !player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        return context;
    }

    protected void updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack) {
        updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            CompoundTag compoundTag1 = compoundTag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> stateDefinition = state.getBlock().getStateDefinition();
            for(String s : compoundTag1.getAllKeys()) {
                Property<?> property = stateDefinition.getProperty(s);
                if (property != null) {
                    String s1 = Objects.requireNonNull(compoundTag1.get(s)).getAsString();
                    blockState = updateState(blockState, property, s1);
                }
            }
        }

        if (blockState != state) {
            level.setBlock(pos, blockState, 2);
        }

        return blockState;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState state, Property<T> property, String valueIdentifier) {
        return property.getValue(valueIdentifier).map((t) -> state.setValue(property, t)).orElse(state);
    }

    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player player = context.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return state.canSurvive(context.getLevel(), context.getClickedPos()) && context.getLevel().isUnobstructed(state, context.getClickedPos(), collisioncontext);
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    public static void updateCustomBlockEntityTag(Level level, @Nullable Player player, BlockPos pos, ItemStack stack) {
        MinecraftServer server = level.getServer();
        if (server != null) {
            CompoundTag compoundTag = getBlockEntityData(stack);
            if (compoundTag != null) {
                BlockEntity blockentity = level.getBlockEntity(pos);
                if (blockentity != null) {
                    if (!level.isClientSide && blockentity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
                        return;
                    }

                    CompoundTag compoundTag1 = blockentity.saveWithoutMetadata();
                    CompoundTag compoundTag2 = compoundTag1.copy();
                    compoundTag1.merge(compoundTag);
                    if (!compoundTag1.equals(compoundTag2)) {
                        blockentity.load(compoundTag1);
                        blockentity.setChanged();
                    }
                }
            }
        }
    }

    //获取所有可以装在盘子中的食物
    public static void showPlateRecipeList(List<Component> tooltip) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text5"));
        for (String namespace : manager.getNamespaces()) {
            try {
                ResourceLocation resourceName = new ResourceLocation(namespace, "plate/list.json");
                if (manager.hasResource(resourceName)) {
                    List<Resource> resources = manager.getResources(resourceName);
                    for (Resource resource : resources) {
                        InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                        PlateList list = JsonUtils.INSTANCE.noExpose.fromJson(reader, PlateList.class);
                        List<String> stringList = new ArrayList<>();
                        for (String string : list.plates) {
                            int last = string.lastIndexOf("_");
                            stringList.add(string.replaceAll("\\d+","").substring(0, last));
                        }
                        if (stringList.size() > 0) {
                            LinkedHashSet<String> hashSet = new LinkedHashSet<>(stringList);
                            ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
                            for (String string : listWithoutDuplicates) {
                                Item canPutOnPlate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
                                if (canPutOnPlate != null && canPutOnPlate.getRegistryName() != null && !canPutOnPlate.getDefaultInstance().is(Items.AIR)) {
                                    String defaultItemName = canPutOnPlate.getDefaultInstance().getDisplayName().getString();
                                    String correctItemName = defaultItemName.replace("[", "").replace("]", "");
                                    tooltip.add(new TextComponent(correctItemName).withStyle(ChatFormatting.GRAY));
                                }
                            }
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        this.getBlock().appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text1"));
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text2"));
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text3"));
        if (level != null) {
            if (Screen.hasShiftDown()) {
                showPlateRecipeList(tooltip);
            } else {
                tooltip.add(new TranslatableComponent("info.kitchenkarrot.text4"));
            }
        }
    }

    public Block getBlock() {
        return ModBlocks.PLATE.get().delegate.get();
    }

    @Nullable
    public static CompoundTag getBlockEntityData(ItemStack itemStack) {
        return itemStack.getTagElement("BlockEntityTag");
    }

}