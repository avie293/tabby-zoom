package cc.avie.tabbyzoom.config;

import cc.avie.tabbyzoom.TabbyZoom;
import cc.avie.tabbyzoom.client.TabbyZoomClient;
import me.avie29.tabbylib.api.TabbyConfig;
import me.avie29.tabbylib.api.option.BooleanOption;
import me.avie29.tabbylib.api.option.DoubleOption;
import me.avie29.tabbylib.api.option.KeyBindOption;
import net.minecraft.network.chat.Component;

public final class TabbyZoomConfig {
	public static final KeyBindOption ZOOM_KEY = KeyBindOption.builder("zoomKey", TabbyZoomClient.ZOOM_KEY).build();

	public static final KeyBindOption CINEMATIC_ZOOM_KEY = KeyBindOption.builder("cinematicZoomKey", TabbyZoomClient.CINEMATIC_ZOOM_KEY).build();
	public static final BooleanOption CINEMATIC_ZOOM_HIDE_HUD = BooleanOption.builder("cinematicZoomHideHud", true).build();

	public static final DoubleOption INITIAL_ZOOM = DoubleOption.builder("initialZoom", 4.0)
			.slider(1.5, 20.0, 0.5)
			.formatter(TabbyZoomConfig::formatZoom)
			.build();

	public static final BooleanOption CINEMATIC_CAMERA = BooleanOption.builder("cinematicCamera", true).build();
	public static final BooleanOption RELATIVE_SENSITIVITY = BooleanOption.builder("relativeSensitivity", true).build();

	public static final BooleanOption CINEMATIC_BARS = BooleanOption.builder("cinematicBars", false)
			.dependsOn(CINEMATIC_CAMERA)
			.build();
	public static final DoubleOption CINEMATIC_BAR_SIZE = DoubleOption.builder("cinematicBarSize", 12.0)
			.slider(2.0, 30.0, 1.0)
			.formatter(value -> Component.literal(String.format("%.0f%%", value)))
			.enabledWhen(TabbyZoomConfig::cinematicBarsPending)
			.build();
	public static final DoubleOption CINEMATIC_BAR_FADE = DoubleOption.builder("cinematicBarFade", 0.4)
			.slider(0.05, 2.0, 0.05)
			.formatter(value -> Component.literal(String.format("%.2fs", value)))
			.enabledWhen(TabbyZoomConfig::cinematicBarsPending)
			.build();

	public static final BooleanOption SMOOTH_ZOOM = BooleanOption.builder("smoothZoom", true).build();
	public static final DoubleOption ZOOM_SPEED = DoubleOption.builder("zoomSpeed", 10.0)
			.slider(1.0, 30.0, 1.0)
			.dependsOn(SMOOTH_ZOOM)
			.build();

	public static final BooleanOption SCROLL_ZOOM = BooleanOption.builder("scrollZoom", true).build();
	public static final DoubleOption SCROLL_STEP = DoubleOption.builder("scrollStep", 1.25)
			.slider(1.05, 2.0, 0.05)
			.formatter(TabbyZoomConfig::formatZoom)
			.dependsOn(SCROLL_ZOOM)
			.build();
	public static final DoubleOption MAX_ZOOM = DoubleOption.builder("maxZoom", 50.0)
			.slider(2.0, 500.0, 1.0)
			.formatter(TabbyZoomConfig::formatZoom)
			.dependsOn(SCROLL_ZOOM)
			.build();

	private static TabbyConfig config;

	private TabbyZoomConfig() {
	}

	public static void init() {
		config = TabbyConfig.builder(TabbyZoom.MOD_ID)
				.category("general", category -> category
						.add(ZOOM_KEY, INITIAL_ZOOM, CINEMATIC_CAMERA, RELATIVE_SENSITIVITY)
						.group("cinematicZoom", group -> group.add(CINEMATIC_ZOOM_KEY, CINEMATIC_ZOOM_HIDE_HUD))
						.group("cinematicBars",group -> group.add(CINEMATIC_BARS, CINEMATIC_BAR_SIZE, CINEMATIC_BAR_FADE))
						.group("smoothZoom",group -> group.add(SMOOTH_ZOOM, ZOOM_SPEED))
						.group("scrollZoom", group -> group.add(SCROLL_ZOOM, SCROLL_STEP, MAX_ZOOM)))
				.build();
	}

	public static TabbyConfig get() {
		return config;
	}

	private static boolean cinematicBarsPending() {
		return CINEMATIC_CAMERA.getPending() && CINEMATIC_BARS.getPending();
	}

	private static Component formatZoom(double value) {
		return Component.literal(String.format("%.2fx", value));
	}
}
