package cc.avie.tabbyzoom.fabric;

import cc.avie.tabbyzoom.TabbyZoom;
import cc.avie.tabbyzoom.client.TabbyZoomClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class TabbyZoomFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMapping.Category.register(TabbyZoomClient.CATEGORY.id());
        TabbyZoomClient.KEYS.forEach(KeyMappingHelper::registerKeyMapping);
        TabbyZoomClient.init(TabbyZoom.MOD_ID);
    }
}
