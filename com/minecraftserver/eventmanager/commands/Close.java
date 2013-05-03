package com.minecraftserver.eventmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftserver.eventmanager.EventManager;

public class Close {

    public static void run(CommandSender sender, String[] args, EventManager em){
        if (sender.hasPermission("event.close")){
            Player p = (Player) sender;
            String warpName = args[1];
            if(em.warps.get(warpName)!=null){
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + p.getName() + " closed event: " + ChatColor.GOLD + warpName);
                em.closed = true;
            }
            em.closed(em.closed);
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
        }
        
    }
    
}
