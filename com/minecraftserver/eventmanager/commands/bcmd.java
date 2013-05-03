package com.minecraftserver.eventmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.minecraftserver.eventmanager.EventManager;

public class bcmd {

    public static void run(CommandSender sender, String[] args, EventManager em){
        if (sender.hasPermission("event.setprops")){
            if (args[1].equalsIgnoreCase("add")){
                em.blockedcmd.add(args[2]);
            } else if (args[1].equalsIgnoreCase("remove")){
                try {
                    em.blockedcmd.remove(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + args[2] + " was not found on the list of blocked commands!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect usage! Usage:");
                sender.sendMessage(ChatColor.RED + "/event bcmd add|remove <command>");
            }
            em.bcmd(em.blockedcmd);
        }
    }
    
}
