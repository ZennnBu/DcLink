
package com.dclink;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class DiscordAdminCommand implements CommandExecutor {

    private final DcLink plugin;

    public DiscordAdminCommand(DcLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,
            Command command, String label, String[] args) {

        if (!sender.hasPermission("dclink.admin")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("msg")
                && args[1].equalsIgnoreCase("set")) {

            String message = String.join(" ",
                    java.util.Arrays.copyOfRange(args, 2, args.length));

            plugin.getConfig().set("discord.message", message);
            plugin.saveConfig();

            sender.sendMessage("§aDiscord message updated!");
            return true;
        }

        if (args.length >= 3 && args[0].equalsIgnoreCase("link")
                && args[1].equalsIgnoreCase("set")) {

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

        if (args.length >= 2 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§aDcLink config reloaded!");
            return true;
        }

        sender.sendMessage("§6/discord msg set <message>");
        sender.sendMessage("§6/discord link set <link>");
        sender.sendMessage("§6/discord reload");

        return true;
    }
}
