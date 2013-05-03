package com.minecraftserver.eventmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.minecraftserver.eventmanager.EventManager;

public class Help {

    public static void run(CommandSender sender, EventManager em) {
        
        String eventname = "<eventname>";

        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + "Available commands:");
        
        if (em.running){
            eventname = em.warpName;
        }
        if (sender.hasPermission("event.use")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event join " + ChatColor.AQUA + eventname);
            sender.sendMessage(ChatColor.DARK_AQUA + "/event leave - Only works if you are in an event");
        }
        if (sender.hasPermission("event.setprops")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event set <property> <value>");
            sender.sendMessage(ChatColor.DARK_AQUA + "/event bcmd add|remove <command>");
        }
        if (sender.hasPermission("event.start")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event start " + ChatColor.AQUA + eventname);
        }
        if (sender.hasPermission("event.close")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event close " + ChatColor.AQUA + eventname);
        }
        if (sender.hasPermission("event.end")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event end " + ChatColor.AQUA + eventname);
        }
        if (sender.hasPermission("event.manage")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/event teams spawn set 1|2");
            sender.sendMessage(ChatColor.DARK_AQUA + "/event teams assign <TeamA> <TeamB>");
            sender.sendMessage(ChatColor.DARK_AQUA + "You must close event and set both spawns before you can assign teams!");
        }
        
    }
    
}
