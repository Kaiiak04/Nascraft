package me.bounser.nascraft.premium;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * No-op stand-in for the closed-source premium module.
 *
 * Upstream {@code master} calls {@link #enable(JavaPlugin)} / {@link #disable()} from
 * {@code Nascraft#onEnable} / {@code Nascraft#onDisable}, but the {@code me.bounser.nascraft.premium}
 * package has never been published to the repository, so master does not compile as-is.
 *
 * This stub keeps those call sites intact — so merges from upstream stay clean — while the free build
 * simply does nothing. If the real premium module is ever published, delete this file.
 */
public final class PremiumLoader {

    public static void enable(JavaPlugin plugin) {
        // no premium features in this build
    }

    public static void disable() {
        // no premium features in this build
    }

    private PremiumLoader() {}
}
