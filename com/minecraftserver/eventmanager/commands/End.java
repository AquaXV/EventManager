package com.minecraftserver.eventmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftserver.eventmanager.EventManager;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class End {
    
    public static void run(CommandSender sender, String[] args, EventManager em){
        if(sender.hasPermission("event.end")) {
            Player player = (Player) sender;
            String warpName = args[1];
            
            if(em.warps.get(warpName)!=null) {
                em.closed = false;
                em.running = false;
                em.teams = false;
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + player.getName() + " ended event: " + ChatColor.GOLD + warpName);
                Integer i = 0;
                
                while (em.pName.iterator().hasNext()) {
                    try {
                        String player_event = em.pName.get(i);
                        PermissionUser user = PermissionsEx.getUser(Bukkit.getServer().getPlayer(player_event));
                        if (em.props.get("mcmmo") == false) {
                            user.removePermission("-mcmmo.ability.*");
                            user.removePermission("-mcmmo.skills.*");
                            user.removePermission("-mcmmo.perks.*");
                        }
                        if (em.props.get("magic") == false) {
                            user.removePermission("-magicspells.*");
                        }
                        if (em.props.get("playerheads") == false) {
                            user.removePermission("-playerheads.canloosehead");
                        }
                        Bukkit.getServer().getPlayer(player_event).setHealth(0);
                        if (i >= em.pName.size()-1){
                            break;
                        }
                        i++;
                    } catch (Exception e){
                        Bukkit.getLogger().info("EventHandler gave me an incorrect number....");
                    }
                }
                em.pName.clear();
                em.warps.remove(warpName);
                Bukkit.getServer().getScheduler().cancelTask(em.id);
                em.pName(em.pName);
                em.warps(em.warps);
            } else {
                player.sendMessage(ChatColor.RED + "Specified event not found. Events are Case Sensitive");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command!");
        }
    }
}
