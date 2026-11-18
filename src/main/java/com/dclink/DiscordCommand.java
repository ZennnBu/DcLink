
package com.dclink;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import java.util.Arrays;

public class DiscordCommand implements CommandExecutor {

    private final DcLink plugin;

    public DiscordCommand(DcLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,
            Command command, String label, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Players only!");
                return true;
            }

            String link = plugin.getConfig().getString(
                "discord.link", "https://discord.gg/yourinvite");

            player.sendTitle(
                color(plugin.getConfig().getString("discord.title.line1")),
                color(plugin.getConfig().getString("discord.title.line2")),
                10, 60, 10
            );

            player.sendMessage(color(
                plugin.getConfig().getString("discord.message")));

            player.sendMessage(Component.text("[ OPEN DISCORD ]")
                .color(NamedTextColor.GREEN)
                .clickEvent(ClickEvent.openUrl(link)));

            player.sendMessage(Component.text("[ COPY LINK ]")
                .color(NamedTextColor.LIGHT_PURPLE)
                .clickEvent(ClickEvent.copyToClipboard(link)));

            return true;
        }

        if (!sender.hasPermission("dclink.admin")) {
            sender.sendMessage("§cNo permission!");
            return true;
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("msg")
                && args[1].equalsIgnoreCase("set")) {

            String msg = String.join(" ",
                Arrays.copyOfRange(args, 2, args.length));

            plugin.getConfig().set("discord.message", msg);
            plugin.saveConfig();
            sender.sendMessage("§aMessage updated!");
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("link")
                && args[1].equalsIgnoreCase("set")) {

            String link = args[2];

            if (!link.matches("https://(discord\\.gg|discord\\.com)/.+")) {
                sender.sendMessage("§cInvalid Discord link!");
                return true;
            }

            plugin.getConfig().set("discord.link", link);
            plugin.saveConfig();
            sender.sendMessage("§aDiscord link updated!");
            return true;
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("anc")
                && args[1].equalsIgnoreCase("set")) {

            String msg = String.join(" ",
                Arrays.copyOfRange(args, 2, args.length));

            plugin.getConfig().set(
                "discord.announcement.message", msg);

            plugin.saveConfig();
            sender.sendMessage("§aAnnouncement updated!");
            return true;
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("anc")
                && args[1].equalsIgnoreCase("time")
                && args[2].equalsIgnoreCase("set")) {

            String time = args[3];

            if (!time.matches("[1-9][0-9]*[mh]")) {
                sender.sendMessage("§cUse: 10m, 30m, 1h or 2h");
                return true;
            }

            plugin.getConfig().set(
                "discord.announcement.interval", time);

            plugin.saveConfig();
            plugin.getAnnouncementManager().restart();

            sender.sendMessage("§aAnnouncement time set to " + time);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.getAnnouncementManager().restart();
            sender.sendMessage("§aDcLink reloaded!");
            return true;
        }

        sender.sendMessage("§6--- DcLink Commands ---");
        sender.sendMessage("§e/discord");
        sender.sendMessage("§e/discord msg set <message>");
        sender.sendMessage("§e/discord link set <link>");
        sender.sendMessage("§e/discord anc set <message>");
        sender.sendMessage("§e/discord anc time set <time>");
        sender.sendMessage("§e/discord reload");

        return true;
    }

    private String color(String message) {
        return message == null ? "" : message.replace("&", "§");
    }
}
