package cc.avie.tabbyzoom.mixin;

import cc.avie.tabbyzoom.client.ZoomController;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
	@Inject(method = "calculateFov", at = @At("RETURN"), cancellable = true)
	private void tabbyZoom$applyZoom(float partialTick, CallbackInfoReturnable<Float> cir) {
		ZoomController.update();
		double zoom = ZoomController.getCurrentZoom();
		if (zoom != 1.0) {
			cir.setReturnValue((float) (cir.getReturnValueF() / zoom));
		}
	}
}
