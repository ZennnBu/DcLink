
package com.dclink;

import org.bukkit.plugin.java.JavaPlugin;

public final class DcLink extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getLogger().info("DcLink has been enabled!");
        getLogger().info("Discord Integration System is ready!");

    }

    @Override
    public void onDisable() {

        getLogger().info("DcLink has been disabled!");

    }
}
