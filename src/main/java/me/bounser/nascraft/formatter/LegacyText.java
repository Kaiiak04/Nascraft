package me.bounser.nascraft.formatter;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Section-char legacy serializer with 1.16+ hex color support.
 *
 * Used instead of {@link LegacyComponentSerializer#legacySection()}, which has no hex support and
 * therefore downsamples colors like &lt;#FF8800&gt; to the nearest of the 16 named colors.
 */
public final class LegacyText {

    public static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
            .character(LegacyComponentSerializer.SECTION_CHAR)
            .hexColors()
            // Emit §x§F§F§8§8§0§0 rather than Adventure's compact §#ff8800. Bukkit's legacy renderer and
            // ChatColor#stripColor only understand the former; the compact form leaks as literal text.
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private LegacyText() {}
}
