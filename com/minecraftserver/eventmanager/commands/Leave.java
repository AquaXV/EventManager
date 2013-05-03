package com.minecraftserver.eventmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.minecraftserver.eventmanager.EventManager;

public class Leave {

    public static boolean run(CommandSender sender, EventManager em) {
        Player p = (Player) sender;
        PermissionUser user = PermissionsEx.getUser(p);
        if (em.running && em.pName.contains(p.getName())) {
            Bukkit.getServer().getPlayer(p.getName()).setHealth(0);
            if (em.props.get("mcmmo") == false) {
                user.removePermission("-mcmmo.ability.*");
                user.removePermission("-mcmmo.skills.*");
                user.removePermission("-mcmmo.perks.*");
            }
            if (em.props.get("magic") == false) {
                user.removePermission("-magicspells.*");
            }
            em.pName.remove(p.getName());
            em.pName(em.pName);
            return true;
        }
        return false;
    }
}
