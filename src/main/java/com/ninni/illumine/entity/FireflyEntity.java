package com.ninni.illumine.entity;

import com.ninni.illumine.client.IllumineParticles;
import com.ninni.illumine.entity.ai.FlyAroundGoal;
import com.ninni.illumine.item.IllumineItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class FireflyEntity extends PathAwareEntity {
    private static final TrackedData<Integer> COUNT = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public FireflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setCount(random.nextBetween(1,3));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COUNT, 1);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Count", this.getCount());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCount(nbt.getInt("Count"));
    }

    public void setCount(int count) {
        this.dataTracker.set(COUNT, count);
    }

    public int getCount() {
        return this.dataTracker.get(COUNT);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FlyAroundGoal(this));
        this.goalSelector.add(2, new SwimGoal(this));
    }

    @Override
    public boolean doesNotCollide(double offsetX, double offsetY, double offsetZ) {
        return true;
    }

    @Override
    protected void knockback(LivingEntity target) {
        target.takeKnockback(0, this.getX(), this.getZ());
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.GLASS_BOTTLE) && this.getCount() > 0) {
            this.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            if (!this.world.isClient()) {
                this.setCount(this.getCount() - 1);
                this.emitGameEvent(GameEvent.ENTITY_INTERACT);
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(IllumineItems.FIREFLY_BOTTLE)));
                if (this.getCount() == 0) this.discard();
            }
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(IllumineItems.FIREFLY_BOTTLE) && this.getCount() < 3 && !this.world.isClient()) {
            this.setCount(this.getCount() + 1);
            this.emitGameEvent(GameEvent.ENTITY_INTERACT);
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            this.world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            int count = this.getCount();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            float width = 1 + (count - 1) * 0.5F;
            mutable.set(this.getX() + MathHelper.nextBetween(random, -width, width), this.getBlockPos().getY(), this.getZ() + MathHelper.nextBetween(random, -width, width));
            Random random = this.getWorld().random;
            double j = random.nextGaussian() * 0.025;
            double k = random.nextGaussian() * 0.025;
            double l = random.nextGaussian() * 0.025;
            for (int i = 0; i < count; i++) if (this.random.nextFloat() < 0.1F) world.addParticle(IllumineParticles.FIREFLY, mutable.getX() + random.nextDouble(), this.getY() + random.nextDouble(), mutable.getZ() + random.nextDouble(), j, k, l);
        }
    }

    public static DefaultAttributeContainer.Builder createFireflyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.1f).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<FireflyEntity> entity, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getLightLevel(LightType.SKY, pos) == 0;
    }
}
