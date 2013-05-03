package com.minecraftserver.eventmanager.commands;

import java.util.ArrayList;
import java.util.Collections;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.minecraftserver.eventmanager.EventManager;

public class Teams {

    static Location loc1;
    static Location loc2;    
    
    static ArrayList<String> TeamA;
    static ArrayList<String> TeamB;
    
    static boolean teams;
    
    public static void run(CommandSender sender, String[] args, EventManager em){
        if(sender.hasPermission("event.manage")) {
            if(em.running == true) {   
                if (args[1].equalsIgnoreCase("spawn")) {
                    if (args[2].equalsIgnoreCase("set")) {
                        Player p = (Player) sender;
                        if (args[3].equalsIgnoreCase("1")){
                            if (em.pointA == true){
                                em.pointA = false;
                            }
                            em.loc1 = p.getLocation();
                            em.pointA = true;
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + "Set spawnpoint 1.");
                            em.locA(em.loc1);
                            em.pointA(em.pointA);
                        } else if (args[3].equalsIgnoreCase("2")){
                            if (em.pointB == true){
                                em.pointB = false;
                            }
                            em.loc2 = p.getLocation();
                            em.pointB = true;
                            sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + "Set spawnpoint 2.");
                            em.locB(em.loc2);
                            em.pointB(em.pointB);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid usage! Usage:");
                            sender.sendMessage(ChatColor.RED + "/event teams spawn set 1|2");
                        }
                    }
                } else if(args[1].equalsIgnoreCase("assign")) {
                    if ((em.pointA == false) || (em.pointB == false)) {
                        sender.sendMessage(ChatColor.RED + "Set the teamspawns first! Usage:");
                        sender.sendMessage(ChatColor.RED + "/event teams spawn set 1|2");
                    } if (!em.closed) {
                        sender.sendMessage(ChatColor.RED + "Close the event before assigning teams!");
                        return;
                    }
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + "Teaming everybody.");
                    Collections.shuffle(em.pName);
                    int middle = em.pName.size()/2;
                    
                    em.TeamA = new ArrayList<String>();
                    em.TeamB = new ArrayList<String>();
                    em.teams = true;
                    for(int i=0; i<middle;i++) {
                        em.TeamA.add(em.pName.get(i));                                
                    }
                    for(int j = em.pName.size()-1; j>=middle; j--) {
                        em.TeamB.add(em.pName.get(j));
                    }
                    for(String l : em.TeamA) {
                        Player p = Bukkit.getPlayer(l);
                        p.sendMessage(ChatColor.DARK_AQUA + "You are on team: " + ChatColor.GOLD + args[2]);
                        p.teleport(em.loc1);
                    } for(String l : em.TeamB) {
                        Player p = Bukkit.getPlayer(l);
                        p.sendMessage(ChatColor.DARK_AQUA + "You are on team: " + ChatColor.GOLD + args[3]);
                        p.teleport(em.loc2);
                    }
                    em.pName(em.pName);
                    
                    em.teamA = em.board.registerNewTeam("TeamA");
                    em.teamB = em.board.registerNewTeam("TeamB");
                    
                    em.teamA.setDisplayName(args[2]);
                    em.teamB.setDisplayName(args[3]);
                    
                    em.obj = em.board.registerNewObjective(em.teamA.getDisplayName(), "dummy");
                    em.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                    em.objs = em.board.registerNewObjective("ScoreA", "dummy");
                    em.objs.setDisplaySlot(DisplaySlot.SIDEBAR);
                    
                    em.obj2 = em.board.registerNewObjective(em.teamB.getDisplayName(), "dummy");
                    em.obj2.setDisplaySlot(DisplaySlot.SIDEBAR);
                    em.objs2 = em.board.registerNewObjective("ScoreB", "dummy");
                    em.objs2.setDisplaySlot(DisplaySlot.SIDEBAR);

                    em.obj.setDisplayName(em.teamA.getDisplayName());
                    em.scoreA = em.objs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:"));
                    em.scoreA.setScore(0);
                    
                    em.obj2.setDisplayName(em.teamB.getDisplayName());
                    em.scoreB = em.objs2.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Kills:"));
                    em.scoreB.setScore(0);
                    
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.setScoreboard(em.board);
                    }
                    
                    
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid usage! Usage:");
                    sender.sendMessage(ChatColor.RED + "/event teams spawn set 1|2");
                    sender.sendMessage(ChatColor.RED + "/event teams assign <TeamA> <TeamB>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No event is running right now! Start an event before using this option.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command!");
        }
    }
    
}
