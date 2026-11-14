package cc.avie.tabbyzoom.client;

import cc.avie.tabbyzoom.TabbyZoom;
import cc.avie.tabbyzoom.config.TabbyZoomConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.util.List;

/** Shared by the Fabric and NeoForge entrypoints, which register the category and key mappings. */
public final class TabbyZoomClient {
    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(TabbyZoom.id("main"));

    public static final KeyMapping ZOOM_KEY = new KeyMapping(
            "key.tabby-zoom.zoom",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_C,
            CATEGORY
    );

    public static final KeyMapping CINEMATIC_ZOOM_KEY = new KeyMapping(
            "key.tabby-zoom.cinematicZoom",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_F6,
            CATEGORY
    );

    public static final List<KeyMapping> KEYS = List.of(ZOOM_KEY, CINEMATIC_ZOOM_KEY);

    private TabbyZoomClient() {
    }

    /** {@code modId} is the id of the mod on the current loader. */
    public static void init(String modId) {
        TabbyZoomConfig.init(modId);
    }
}
