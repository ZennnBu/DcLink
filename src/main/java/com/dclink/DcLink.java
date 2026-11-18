
package com.dclink;

import org.bukkit.plugin.java.JavaPlugin;

public final class DcLink extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getCommand("discord").setExecutor(
                new DiscordCommand(this)
        );

        getLogger().info("DcLink enabled successfully!");
        getLogger().info("Discord system is ready!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DcLink disabled!");
    }
}
