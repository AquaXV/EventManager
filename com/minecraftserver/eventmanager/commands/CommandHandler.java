package com.minecraftserver.eventmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.minecraftserver.eventmanager.EventManager;

public class CommandHandler extends EventManager {
    
    EventManager em;
    
    public CommandHandler(EventManager em){
        this.em = em;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (args[0].equalsIgnoreCase("start")) {
            Start.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("end")) {
            End.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("check")) {
            Check.run(sender, em);
        } else if (args[0].equalsIgnoreCase("close")) {
            Close.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("set")) {
            Set.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("bcmd")) {
            bcmd.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("join")) {
            Join.run(sender, args, em);
        } else if (args[0].equalsIgnoreCase("leave")) {
            Leave.run(sender, em);
        } else if (args[0].equalsIgnoreCase("teams")) {
            Teams.run(sender, args, em);
        } else if (args.length < 1) {
            Help.run(sender, em);
        } else {
            Help.run(sender, em);
        }
        return true;
    }   
}
