package cc.avie.tabbyzoom.mixin;

import cc.avie.tabbyzoom.client.ZoomController;
import cc.avie.tabbyzoom.config.TabbyZoomConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.ScrollWheelHandler;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@ModifyExpressionValue(
			method = "turnPlayer",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;smoothCamera:Z")
	)
	private boolean tabbyZoom$smoothWhileZooming(boolean smoothCamera) {
		return smoothCamera || ZoomController.useCinematicCamera();
	}

	@ModifyExpressionValue(
			method = "turnPlayer",
			at = @At(value = "INVOKE", target = "Ljava/lang/Double;doubleValue()D", ordinal = 0)
	)
	private double tabbyZoom$relativeSensitivity(double sensitivity) {
		double zoom = ZoomController.getCurrentZoom();
		if (zoom == 1.0 || !TabbyZoomConfig.RELATIVE_SENSITIVITY.get()) {
			return sensitivity;
		}
		double base = (sensitivity * 0.6 + 0.2) / Math.cbrt(zoom);
		return (base - 0.2) / 0.6;
	}

	@WrapOperation(
			method = "onScroll",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ScrollWheelHandler;onMouseScroll(DD)Lorg/joml/Vector2i;")
	)
	private Vector2i tabbyZoom$scrollZoom(ScrollWheelHandler handler, double xOffset, double yOffset, Operation<Vector2i> original) {
		Vector2i scroll = original.call(handler, xOffset, yOffset);
		if (ZoomController.onScroll(scroll.y)) {
			return new Vector2i(0, 0);
		}
		return scroll;
	}
}
