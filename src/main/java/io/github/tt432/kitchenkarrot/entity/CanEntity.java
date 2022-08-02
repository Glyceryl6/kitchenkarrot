package io.github.tt432.kitchenkarrot.entity;

import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;


public class CanEntity extends Mob {
    //    private final Logger logger = LogManager.getLogger();
    //TODO 将最长存活时间改成从配置文件读取 by：skyinr
    private static int MaxAge = 10 * 20;
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(CanEntity.class, EntityDataSerializers.INT);

    public CanEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        return this.isRemoved() || !(pSource.getEntity() instanceof Player);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player) {
            this.discard();
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CLOUD, getX(), getY(), getZ(), 30, 0, 0, 0, 0.1);
            }
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 0.5D);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pPlayer.getItemInHand(pHand) == ItemStack.EMPTY) {
            returnItem(level, pPlayer);
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGE, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(AGE, pCompound.getInt("age"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("age", this.entityData.get(AGE));
    }

    @Override
    public void tick() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        this.entityData.set(AGE, this.entityData.get(AGE) + 1);
        if (entityData.get(AGE) >= MaxAge) {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CLOUD, getX(), getY(), getZ(), 30, 0, 0, 0, 0.1);
            }
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }

    @Override
    public boolean isNoAi() {
        return false;
    }

    @NotNull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void returnItem(Level level, Player player) {
        level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), ModItems.EMPTY_CAN.get().getDefaultInstance()));
        this.remove(RemovalReason.KILLED);
    }
}
