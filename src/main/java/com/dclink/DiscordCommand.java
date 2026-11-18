
package com.dclink;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

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

            String link = plugin.getConfig().getString("discord.link");

            player.sendTitle(
                color(plugin.getConfig().getString("discord.title.line1")),
                color(plugin.getConfig().getString("discord.title.line2")),
                10, 60, 10
            );

            player.sendMessage(color(
                plugin.getConfig().getString("discord.message")
            ));

            player.sendMessage(
                "§d§l[ CLICK TO JOIN DISCORD ]"
            );

            return true;
        }

        if (!sender.hasPermission("dclink.admin")) {
            sender.sendMessage("§cNo permission!");
            return true;
        }

        if (args.length >= 3 &&
                args[0].equalsIgnoreCase("msg") &&
                args[1].equalsIgnoreCase("set")) {

            String msg = String.join(" ",
                Arrays.copyOfRange(args, 2, args.length));

            plugin.getConfig().set("discord.message", msg);
            plugin.saveConfig();

            sender.sendMessage("§aMessage updated!");
            return true;
        }

        if (args.length == 3 &&
                args[0].equalsIgnoreCase("link") &&
                args[1].equalsIgnoreCase("set")) {

            String link = args[2];

            if (!link.startsWith("https://discord.gg/")
                    && !link.startsWith("https://discord.com/")) {
                sender.sendMessage("§cInvalid Discord link!");
                return true;
            }

            plugin.getConfig().set("discord.link", link);
            plugin.saveConfig();

            sender.sendMessage("§aDiscord link updated!");
            return true;
        }

        if (args.length == 1 &&
                args[0].equalsIgnoreCase("reload")) {

            plugin.reloadConfig();
            sender.sendMessage("§aDcLink reloaded!");
            return true;
        }

        sender.sendMessage("§6--- DcLink Commands ---");
        sender.sendMessage("§e/discord");
        sender.sendMessage("§e/discord msg set <message>");
        sender.sendMessage("§e/discord link set <link>");
        sender.sendMessage("§e/discord reload");

        return true;
    }

    private String color(String message) {
        return message == null ? "" : message.replace("&", "§");
    }
}
