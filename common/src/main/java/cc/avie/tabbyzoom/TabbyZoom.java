package cc.avie.tabbyzoom;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TabbyZoom {
    /** Namespace of the assets and translations, the NeoForge mod id is "tabby_zoom". */
    public static final String MOD_ID = "tabby-zoom";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private TabbyZoom() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
