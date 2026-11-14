package cc.avie.tabbyzoom.config;

import cc.avie.tabbylib.api.TabbyConfig;
import cc.avie.tabbylib.api.option.BooleanOption;
import cc.avie.tabbylib.api.option.DoubleOption;
import cc.avie.tabbylib.api.option.KeyBindOption;
import cc.avie.tabbylib.api.option.Options;
import cc.avie.tabbyzoom.TabbyZoom;
import cc.avie.tabbyzoom.client.TabbyZoomClient;
import net.minecraft.network.chat.Component;

public final class TabbyZoomConfig {
    public static final KeyBindOption ZOOM_KEY = Options.keyBind("zoomKey", TabbyZoomClient.ZOOM_KEY);

    public static final KeyBindOption CINEMATIC_ZOOM_KEY =
            Options.keyBind("cinematicZoomKey", TabbyZoomClient.CINEMATIC_ZOOM_KEY);
    public static final BooleanOption CINEMATIC_ZOOM_HIDE_HUD = Options.toggle("cinematicZoomHideHud", true);

    public static final DoubleOption INITIAL_ZOOM = Options.decimal("initialZoom", 4.0)
            .slider(1.5, 20.0, 0.5)
            .format(TabbyZoomConfig::formatZoom);

    public static final BooleanOption CINEMATIC_CAMERA = Options.toggle("cinematicCamera", true);
    public static final BooleanOption RELATIVE_SENSITIVITY = Options.toggle("relativeSensitivity", true);

    public static final BooleanOption CINEMATIC_BARS = Options.toggle("cinematicBars", false)
            .dependsOn(CINEMATIC_CAMERA);
    public static final DoubleOption CINEMATIC_BAR_SIZE = Options.decimal("cinematicBarSize", 12.0)
            .slider(2.0, 30.0, 1.0)
            .format(value -> Component.literal(String.format("%.0f%%", value)))
            .enabledWhen(TabbyZoomConfig::cinematicBarsPending);
    public static final DoubleOption CINEMATIC_BAR_FADE = Options.decimal("cinematicBarFade", 0.4)
            .slider(0.05, 2.0, 0.05)
            .format(value -> Component.literal(String.format("%.2fs", value)))
            .enabledWhen(TabbyZoomConfig::cinematicBarsPending);

    public static final BooleanOption SMOOTH_ZOOM = Options.toggle("smoothZoom", true);
    public static final DoubleOption ZOOM_SPEED = Options.decimal("zoomSpeed", 10.0)
            .slider(1.0, 30.0, 1.0)
            .dependsOn(SMOOTH_ZOOM);

    public static final BooleanOption SCROLL_ZOOM = Options.toggle("scrollZoom", true);
    public static final DoubleOption SCROLL_STEP = Options.decimal("scrollStep", 1.25)
            .slider(1.05, 2.0, 0.05)
            .format(TabbyZoomConfig::formatZoom)
            .dependsOn(SCROLL_ZOOM);
    public static final DoubleOption MAX_ZOOM = Options.decimal("maxZoom", 50.0)
            .slider(2.0, 500.0, 1.0)
            .format(TabbyZoomConfig::formatZoom)
            .dependsOn(SCROLL_ZOOM);

    private static TabbyConfig config;

    private TabbyZoomConfig() {
    }

    public static void init(String modId) {
        config = TabbyConfig.builder(modId)
                .translationId(TabbyZoom.MOD_ID)
                .fileName(TabbyZoom.MOD_ID)
                .category("general", category -> category
                        .add(ZOOM_KEY, INITIAL_ZOOM, CINEMATIC_CAMERA, RELATIVE_SENSITIVITY)
                        .section("cinematicZoom", section -> section.add(CINEMATIC_ZOOM_KEY, CINEMATIC_ZOOM_HIDE_HUD))
                        .section("cinematicBars", section -> section
                                .add(CINEMATIC_BARS, CINEMATIC_BAR_SIZE, CINEMATIC_BAR_FADE))
                        .section("smoothZoom", section -> section.add(SMOOTH_ZOOM, ZOOM_SPEED))
                        .section("scrollZoom", section -> section.add(SCROLL_ZOOM, SCROLL_STEP, MAX_ZOOM)))
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
