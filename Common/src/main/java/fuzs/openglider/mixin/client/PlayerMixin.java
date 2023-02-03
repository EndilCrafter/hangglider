package fuzs.openglider.mixin.client;

import fuzs.openglider.helper.GliderCapabilityHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    protected void updatePlayerPose(CallbackInfo callback) {
        if (this.canEnterPose(Pose.SWIMMING) && GliderCapabilityHelper.getIsGliderDeployed(Player.class.cast(this))) {
            this.setPose(Pose.FALL_FLYING);
            callback.cancel();
        }
    }
}
