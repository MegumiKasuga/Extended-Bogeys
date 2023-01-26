package com.rabbitminers.extendedbogeys.bogey.unlinked;

import com.simibubi.create.foundation.tileEntity.CachedRenderBBTileEntity;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class UnlinkedBogeyTileEntity extends CachedRenderBBTileEntity {
    public UnlinkedBogeyTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private float angle;

    public float getAngle() {
        return this.angle;
    }

    public void setRotationAngle(float angle) {
        this.angle = angle;
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    LerpedFloat virtualAnimation = LerpedFloat.angular();

    public float getVirtualAngle(float partialTicks) {
        return virtualAnimation.getValue(partialTicks);
    }

    public void animate(float distanceMoved) {
        BlockState blockState = getBlockState();
        if (!(blockState.getBlock() instanceof IUnlinkedBogeyBlock type))
            return;
        double angleDiff = 360 * distanceMoved / (Math.PI * 2 * type.getWheelRadius());
        double newWheelAngle = (virtualAnimation.getValue() - angleDiff) % 360;
        virtualAnimation.setValue(newWheelAngle);
    }
}
