package com.minecraftserver.eventmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.minecraftserver.eventmanager.EventManager;

public class Check {

    public static void run(CommandSender sender, EventManager em) {
        if (sender.hasPermission("event.manage")) {
            if (em.running == true) {
                sender.sendMessage(ChatColor.GOLD + "----" + ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Events" + ChatColor.GRAY + "]" + ChatColor.GOLD + "----");
                sender.sendMessage(ChatColor.DARK_AQUA + "Event name: " + ChatColor.AQUA + em.warpName);
                sender.sendMessage(ChatColor.DARK_AQUA + "   blocked commands: " + ChatColor.GOLD + em.blockedcmd.toString());
                sender.sendMessage(ChatColor.DARK_AQUA + "   canFly: " + ChatColor.GOLD + em.props.get("canFly"));
                sender.sendMessage(ChatColor.DARK_AQUA + "   clearInventory: " + ChatColor.GOLD + em.props.get("clearInventory"));
                sender.sendMessage(ChatColor.DARK_AQUA + "   magic: " + ChatColor.GOLD + em.props.get("magic"));
                sender.sendMessage(ChatColor.DARK_AQUA + "   mcmmo: " + ChatColor.GOLD + em.props.get("mcmmo"));
                sender.sendMessage(ChatColor.DARK_AQUA + "   reminder: " + ChatColor.GOLD + em.props.get("reminder"));
                sender.sendMessage(ChatColor.DARK_AQUA + "   playerheads drop: " + ChatColor.GOLD + em.props.get("playerheads"));
                return;
            } else {
                sender.sendMessage(ChatColor.RED + "No event in progress, feel free to start one!");
                return;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command!");
            return;
        }
    }
}
