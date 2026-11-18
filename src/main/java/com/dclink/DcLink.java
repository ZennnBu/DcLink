
package com.dclink;

import org.bukkit.plugin.java.JavaPlugin;

public final class DcLink extends JavaPlugin {

    private AnnouncementManager announcementManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getCommand("discord").setExecutor(
                new DiscordCommand(this)
        );

        announcementManager = new AnnouncementManager(this);
        announcementManager.start();

        getLogger().info("DcLink enabled successfully!");
        getLogger().info("Discord system is ready!");
    }

    @Override
    public void onDisable() {

        if (announcementManager != null) {
            announcementManager.stop();
        }

        getLogger().info("DcLink disabled!");
    }

    public AnnouncementManager getAnnouncementManager() {
        return announcementManager;
    }
}
