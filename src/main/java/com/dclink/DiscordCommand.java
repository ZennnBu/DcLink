
package com.dclink;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    private final DcLink plugin;

    public DiscordCommand(DcLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        String line1 = plugin.getConfig().getString(
                "discord.title.line1", "&a&lJoin Our Server's");

        String line2 = plugin.getConfig().getString(
                "discord.title.line2", "&5&lDISCORD !");

        String link = plugin.getConfig().getString(
                "discord.link", "https://discord.gg/yourinvite");

        player.sendTitle(
                color(line1),
                color(line2),
                10, 60, 10
        );

        player.sendMessage(color(
                plugin.getConfig().getString(
                        "discord.message",
                        "&aJoin our Discord!"
                )
        ));

        Component discordLink = Component.text(
                "[ CLICK TO JOIN DISCORD ]",
                NamedTextColor.LIGHT_PURPLE
        ).clickEvent(ClickEvent.openUrl(link));

        player.sendMessage(discordLink);

        return true;
    }

    private String color(String message) {
        return message.replace("&", "§");
    }
}
