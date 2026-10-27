package cc.avie.tabbyzoom.mixin;

import cc.avie.tabbyzoom.client.CinematicBars;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
	@ModifyExpressionValue(
			method = "extractRenderState",
			at = @At(value = "NEW", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;")
	)
	private GuiGraphicsExtractor tabbyZoom$renderCinematicBars(GuiGraphicsExtractor graphics) {
		CinematicBars.render(graphics);
		return graphics;
	}
}
