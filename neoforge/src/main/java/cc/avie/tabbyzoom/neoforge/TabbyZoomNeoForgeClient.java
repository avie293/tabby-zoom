package cc.avie.tabbyzoom.neoforge;

import cc.avie.tabbyzoom.client.TabbyZoomClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

/**
 * NeoForge client entrypoint of Tabby Zoom. Not loaded on dedicated servers.
 */
@Mod(value = "tabby_zoom", dist = Dist.CLIENT)
public final class TabbyZoomNeoForgeClient {
    public TabbyZoomNeoForgeClient(IEventBus modBus, ModContainer container) {
        // NeoForge mod ids can't contain "-", TabbyLib needs the id of the mod on this loader
        TabbyZoomClient.init(container.getModId());

        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            event.registerCategory(TabbyZoomClient.CATEGORY);
            TabbyZoomClient.KEYS.forEach(event::register);
        });
    }
}
