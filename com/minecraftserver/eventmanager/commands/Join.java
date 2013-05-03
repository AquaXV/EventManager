package com.minecraftserver.eventmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minecraftserver.eventmanager.EventManager;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Join {
    
    static String warpName;
    
    public static void run(CommandSender sender, String[] args, EventManager em){
        if(sender.hasPermission("event.use")) {
            if(em.warps != null) {
                warpName = args[1];
                if (em.closed){
                    sender.sendMessage(ChatColor.RED + "This event is closed!");
                } else if(em.warps.get(warpName) != null) {
                    Location warpLoc = em.warps.get(warpName);
                    ((Player) sender).teleport(warpLoc);
                    em.pName.add(sender.getName());
                    Player p = (Player) sender;
                    PermissionUser user = PermissionsEx.getUser(p);
                    if(em.props.get("clearInventory") == true) {
                        if(p.getInventory().iterator().hasNext()) {
                            if(p.getInventory().iterator().next()!= null) {
                                sender.sendMessage(ChatColor.DARK_AQUA + "Clear your inventory first!");
                            }
                        }
                        p.getActivePotionEffects().clear();
                    }
                    if(em.props.get("canFly") == false) {                                           
                        p.setFlying(false);
                        p.setAllowFlight(false);
                    }
                    if (em.props.get("mcmmo") == false) {
                        user.addPermission("-mcmmo.ability.*");
                        user.addPermission("-mcmmo.skills.*");
                        user.addPermission("-mcmmo.perks.*");
                    }
                    if (em.props.get("magic") == false) {
                        user.addPermission("-magicspells.*");
                    }
                    if (em.props.get("playerheads") == false) {
                        user.addPermission("-playerheads.canloosehead");
                    }
                    em.pName(em.pName);
                    sender.sendMessage(ChatColor.AQUA + "You are now participating in the event: " + ChatColor.GOLD + warpName);
                } else {
                    sender.sendMessage(ChatColor.RED + "Specified event not found. Event name is Case Sensitive.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No event with that name found!\n" + ChatColor.DARK_AQUA + "Currently running event: " + ChatColor.GOLD + warpName);
            }
        }  else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command.");
        }
    }
    
}
