package com.minecraftserver.eventmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.minecraftserver.eventmanager.EventManager;

public class Set {
    
    public static void run(CommandSender sender, String[] args, EventManager em){
        if(sender.hasPermission("event.setprops"))
        {
            boolean val = true;
            String property = args[1];
            try {
                val = Boolean.parseBoolean(args[2]);
                if(property.equalsIgnoreCase("canFly")) {
                    em.props.put("canFly", val);
                    sender.sendMessage(ChatColor.GOLD + "canFly set to " + args[2]);
                    updateP(em);
                } else if(property.equalsIgnoreCase("clearInventory")) {
                    em.props.put("clearInventory", val);
                    sender.sendMessage(ChatColor.GOLD + "clearInventory set to " + args[2]);
                    updateP(em);
                } else if(property.equalsIgnoreCase("mcmmo")) {
                    em.props.put("mcmmo", val);
                    sender.sendMessage(ChatColor.GOLD + "mcmmo set to " + args[2]);
                    updateP(em);
                }  else if(property.equalsIgnoreCase("reminder")) {
                    em.props.put("reminder", val);
                    sender.sendMessage(ChatColor.GOLD + "reminder set to " + args[2]);
                    updateP(em);
                } else if(property.equalsIgnoreCase("magic")) {
                    em.props.put("magic", val);
                    sender.sendMessage(ChatColor.GOLD + "magic set to " + args[2]);
                    updateP(em);
                } else if (property.equalsIgnoreCase("playerheads")){
                    em.props.put("playerheads", val);
                    sender.sendMessage(ChatColor.GOLD + "playerheads drop set to " + args[2]);
                    updateP(em);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid property! Valid properties are:");
                    sender.sendMessage(ChatColor.RED + "canFly, clearInventory, mcmmo, reminder.");
                }
                em.props(em.props);
            } catch(Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid value. It can either be true or false");
            }                           
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command!");
        }
    }
    
    public static void updateP(EventManager em){

        if (em.warps.get(em.warpName) != null) {
            Integer i = 0;
            
            while (em.pName.iterator().hasNext()) {
                try {
                    String player_event = em.pName.get(i);
                    PermissionUser user = PermissionsEx.getUser(Bukkit.getServer().getPlayer(player_event));
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
                    if (em.props.get("mcmmo") == true) {
                        user.removePermission("-mcmmo.ability.*");
                        user.removePermission("-mcmmo.skills.*");
                        user.removePermission("-mcmmo.perks.*");
                    }
                    if (em.props.get("magic") == true) {
                        user.removePermission("-magicspells.*");
                    }
                    if (em.props.get("playerheads") == true) {
                        user.removePermission("-playerheads.canloosehead");
                    }
                    if (i >= em.pName.size()-1){
                        break;
                    }
                    i++;
                } catch (Exception e) {
                    Bukkit.getLogger().info("EventHandler gave me an incorrect number....");
                }
            }
        }
    }
    
}
