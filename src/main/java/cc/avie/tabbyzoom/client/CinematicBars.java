package cc.avie.tabbyzoom.client;

import cc.avie.tabbyzoom.config.TabbyZoomConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class CinematicBars {
	private CinematicBars() {
	}

	public static void render(GuiGraphicsExtractor graphics) {
		float progress = ZoomController.getBarProgress();
		if (progress <= 0.0F) {
			return;
		}

		int width = graphics.guiWidth();
		int height = graphics.guiHeight();
		int barHeight = Math.round(height * (float) (TabbyZoomConfig.CINEMATIC_BAR_SIZE.get() / 100.0) * progress);
		if (barHeight <= 0) {
			return;
		}
		int color = Math.round(progress * 255.0F) << 24;

		graphics.fill(0, 0, width, barHeight, color);
		graphics.fill(0, height - barHeight, width, height, color);
	}
}
