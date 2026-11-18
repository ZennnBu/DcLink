
package com.dclink;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class AnnouncementManager {

    private final DcLink plugin;
    private BukkitTask task;

    public AnnouncementManager(DcLink plugin) {
        this.plugin = plugin;
    }

    public void start() {
        stop();

        if (!plugin.getConfig().getBoolean(
                "discord.announcement.enabled", true)) {
            return;
        }

        String interval = plugin.getConfig().getString(
                "discord.announcement.interval", "30m");

        long seconds = parseTime(interval);

        if (seconds <= 0) {
            plugin.getLogger().warning(
                    "Invalid announcement interval: " + interval);
            return;
        }

        long ticks = seconds * 20L;

        task = Bukkit.getScheduler().runTaskTimer(
                plugin,
                this::broadcastAnnouncement,
                ticks,
                ticks
        );

        plugin.getLogger().info(
                "Discord announcements started: " + interval);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void restart() {
        start();
    }

    private void broadcastAnnouncement() {

        String message = plugin.getConfig().getString(
                "discord.announcement.message",
                "&aJoin our Discord community!"
        );

        String link = plugin.getConfig().getString(
                "discord.link",
                "https://discord.gg/yourinvite"
        );

        Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes('&', message)
        );

        Component open = Component.text(
                "[ OPEN DISCORD ]",
                NamedTextColor.GREEN
        ).clickEvent(ClickEvent.openUrl(link));

        Component copy = Component.text(
                "[ COPY LINK ]",
                NamedTextColor.LIGHT_PURPLE
        ).clickEvent(ClickEvent.copyToClipboard(link));

        Bukkit.broadcast(open);
        Bukkit.broadcast(copy);
    }

    private long parseTime(String time) {

        if (time == null || time.length() < 2) {
            return -1;
        }

        try {
            String number = time.substring(
                    0, time.length() - 1);

            char unit = Character.toLowerCase(
                    time.charAt(time.length() - 1));

            long value = Long.parseLong(number);

            if (value <= 0) {
                return -1;
            }

            return switch (unit) {
                case 'm' -> Math.multiplyExact(value, 60L);
                case 'h' -> Math.multiplyExact(value, 3600L);
                default -> -1;
            };

        } catch (NumberFormatException | ArithmeticException e) {
            return -1;
        }
    }
}
