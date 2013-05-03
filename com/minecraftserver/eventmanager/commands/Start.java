package com.minecraftserver.eventmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftserver.eventmanager.EventManager;


public class Start {
    
    public static void run(CommandSender sender, String[] args, EventManager em) {
        if (em.running) {
            sender.sendMessage(ChatColor.RED + "An event is already running!");
            return;
        }
        if(sender.hasPermission("event.start")) {
            Player player = (Player) sender;
            String warpName = args[1];
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + player.getName() + " started event: " + ChatColor.GOLD + warpName);
            Location loc = player.getLocation();                             
            em.warps.put(warpName, loc);
            em.running = true;
            em.reminder();
            
            em.running(em.running);
            em.warps(em.warps);
            em.warpName(warpName);
            em.pointA(em.pointA);
            em.pointB(em.pointB);
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command!");
        }
    }
}
