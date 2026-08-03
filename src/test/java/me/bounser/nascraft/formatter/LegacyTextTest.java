package me.bounser.nascraft.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacyTextTest {

    private static final String HEX_INPUT = "<#FF8800>Sell Wand";

    @Test
    void preservesHexColorsInsteadOfDownsamplingThem() {
        Component component = MiniMessage.miniMessage().deserialize(HEX_INPUT);

        String hexAware = LegacyText.LEGACY.serialize(component);
        String downsampled = LegacyComponentSerializer.legacySection().serialize(component);

        // §x§f§f§8§8§0§0 — the exact color survives, in the format Bukkit's legacy renderer understands.
        assertEquals("§x§f§f§8§8§0§0Sell Wand", hexAware);

        // legacySection() collapses it to the nearest named color, which is what we are avoiding.
        assertNotEquals(hexAware, downsampled);
        assertTrue(downsampled.startsWith("§6"), "expected gold downsample, was: " + downsampled);
    }

    @Test
    void usesSectionCharSoBukkitStillRendersIt() {
        String serialized = LegacyText.LEGACY.serialize(MiniMessage.miniMessage().deserialize("<red>Market"));

        assertEquals("§cMarket", serialized);
    }

    /**
     * Category#Category strips colors and compares the result against the unstripped string to detect
     * "no formatting present". That comparison must still work now that hex sequences are emitted.
     */
    @Test
    void stripColorRemovesHexSequencesEntirely() {
        String hexAware = LegacyText.LEGACY.serialize(MiniMessage.miniMessage().deserialize(HEX_INPUT));

        assertEquals("Sell Wand", ChatColor.stripColor(hexAware));
        assertNotEquals(hexAware, ChatColor.stripColor(hexAware));
    }

    @Test
    void unformattedTextIsUnchangedSoCategoryFallbackStillTriggers() {
        String plain = LegacyText.LEGACY.serialize(MiniMessage.miniMessage().deserialize("Food"));

        assertEquals("Food", plain);
        assertEquals(plain, ChatColor.stripColor(plain));
    }
}
