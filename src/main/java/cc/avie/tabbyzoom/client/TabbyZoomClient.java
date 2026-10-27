package cc.avie.tabbyzoom.client;

import cc.avie.tabbyzoom.TabbyZoom;
import cc.avie.tabbyzoom.config.TabbyZoomConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class TabbyZoomClient implements ClientModInitializer {
	private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(TabbyZoom.id("main"));

	public static final KeyMapping ZOOM_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.tabby-zoom.zoom",
			InputConstants.Type.KEYBOARD,
			InputConstants.KEY_C,
			CATEGORY
	));

	public static final KeyMapping CINEMATIC_ZOOM_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.tabby-zoom.cinematicZoom",
			InputConstants.Type.KEYBOARD,
			InputConstants.KEY_F6,
			CATEGORY
	));

	@Override
	public void onInitializeClient() {
		TabbyZoomConfig.init();
	}
}
