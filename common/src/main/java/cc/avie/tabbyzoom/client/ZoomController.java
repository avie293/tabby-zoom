package cc.avie.tabbyzoom.client;

import cc.avie.tabbyzoom.config.TabbyZoomConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.util.Mth;

public final class ZoomController {
	private static boolean wasHeld;
	private static boolean wasCinematicHeld;
	private static boolean hidHud;
	private static double zoomLevel = 1.0;
	private static double currentZoom = 1.0;
	private static long lastFrameNanos = -1L;
	private static double barProgress;

	private ZoomController() {
	}

	public static void update() {
		long now = System.nanoTime();
		double deltaSeconds = lastFrameNanos < 0L ? 0.0 : Math.min((now - lastFrameNanos) / 1.0E9, 0.1);
		lastFrameNanos = now;

		boolean cinematicHeld = TabbyZoomClient.CINEMATIC_ZOOM_KEY.isDown();
		boolean held = cinematicHeld || TabbyZoomClient.ZOOM_KEY.isDown();
		if (held && !wasHeld) {
			zoomLevel = TabbyZoomConfig.INITIAL_ZOOM.get();
		}
		wasHeld = held;
		updateHudHiding(cinematicHeld);
		wasCinematicHeld = cinematicHeld;

		double target = held ? zoomLevel : 1.0;
		if (TabbyZoomConfig.SMOOTH_ZOOM.get()) {
			double alpha = 1.0 - Math.exp(-TabbyZoomConfig.ZOOM_SPEED.get() * deltaSeconds);
			double logZoom = Mth.lerp(alpha, Math.log(currentZoom), Math.log(target));
			currentZoom = Math.exp(logZoom);
			if (Math.abs(currentZoom - target) < 0.001) {
				currentZoom = target;
			}
		} else {
			currentZoom = target;
		}

		boolean barsWanted = cinematicHeld
				|| (held && TabbyZoomConfig.CINEMATIC_CAMERA.get() && TabbyZoomConfig.CINEMATIC_BARS.get());
		double barStep = deltaSeconds / Math.max(TabbyZoomConfig.CINEMATIC_BAR_FADE.get(), 0.01);
		barProgress = Mth.clamp(barProgress + (barsWanted ? barStep : -barStep), 0.0, 1.0);
	}

	private static void updateHudHiding(boolean cinematicHeld) {
		Hud hud = Minecraft.getInstance().gui.hud;
		if (cinematicHeld && !wasCinematicHeld && TabbyZoomConfig.CINEMATIC_ZOOM_HIDE_HUD.get() && !hud.isHidden()) {
			hud.toggle();
			hidHud = true;
		} else if (!cinematicHeld && wasCinematicHeld && hidHud) {
			if (hud.isHidden()) {
				hud.toggle();
			}
			hidHud = false;
		}
	}

	public static float getBarProgress() {
		double t = barProgress;
		return (float) (t * t * (3.0 - 2.0 * t));
	}

	public static boolean onScroll(int amount) {
		if (!wasHeld || !TabbyZoomConfig.SCROLL_ZOOM.get()) {
			return false;
		}
		if (amount != 0) {
			double maxZoom = Math.max(TabbyZoomConfig.MAX_ZOOM.get(), TabbyZoomConfig.INITIAL_ZOOM.get());
			zoomLevel = Mth.clamp(zoomLevel * Math.pow(TabbyZoomConfig.SCROLL_STEP.get(), amount), 1.0, maxZoom);
		}
		return true;
	}

	public static boolean isZooming() {
		return wasHeld;
	}

	public static boolean useCinematicCamera() {
		return wasCinematicHeld || (wasHeld && TabbyZoomConfig.CINEMATIC_CAMERA.get());
	}

	public static double getCurrentZoom() {
		return currentZoom;
	}
}
