package com.minecraftserver.eventmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.minecraftserver.eventmanager.commands.CommandHandler;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EventManager extends JavaPlugin implements Listener {
	// /event team assign Blue Red

    CommandHandler ch;
    
	public Map<String, Location> warps = new HashMap<String, Location>();
	public List<String> pName = new ArrayList<String>();
	public Map<String, Boolean> props =  new HashMap<String,Boolean>();
	public List<String> blockedcmd = new ArrayList<String>();
	public boolean running = false;
	public boolean closed = false;
	public String warpName;
	public int id;
	public Location loc;
	public double x, y, z;
	public World w;
	public boolean pointA = false;
	public boolean pointB = false;
	public boolean teams = false;
	public Location loc1;
	public Location loc2;
	public List<String> TeamA;
    public List<String> TeamB;
	
    ScoreboardManager manager = null;
    public Scoreboard board = null;
    public Team teamA = null;
    public Team teamB = null;
    public Objective obj = null;
    public Objective objs = null;
    public Objective obj2 = null;
    public Objective objs2 = null;
    public Score scoreA = null;
    public Score scoreB = null;
    
    
    @Override
	public void onEnable()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info("EventHandler Activated!");
		getCommand("event").setExecutor(new CommandHandler(this));
		Startup();
		
		manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();


        
	}
    
	private void Startup() {
        //Add disabled commands
        blockedcmd.add("home");
        blockedcmd.add("warp");
        blockedcmd.add("spawn");
        blockedcmd.add("tpaccept");
        blockedcmd.add("tpa");
        blockedcmd.add("tpaccept");
        blockedcmd.add("clan home");
        blockedcmd.add("j");
        blockedcmd.add("jumpto");
        blockedcmd.add("thru");
        blockedcmd.add("fly");
        
        props.put("canFly", false);
        props.put("clearInventory", true);
        props.put("mcmmo", false);
        props.put("reminder", true);
        props.put("magic", false);
        props.put("playerheads", true);
    }

    @Override
	public void onDisable()
	{
		this.getLogger().info("EventHandler Deactivated!");
	}
	
    @EventHandler()
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled() || !running) return;
        Player player = event.getPlayer();
        boolean notallowed = false;
        String command = event.getMessage().toLowerCase().substring(1, event.getMessage().length());
        if (pName.contains(player.getName())) {
            for (String cmd : blockedcmd)
                if (command.toLowerCase().startsWith(cmd)) {
                    notallowed = true;
                    break;
                }
            if (notallowed) {
                if (command.toLowerCase().startsWith("fly") && player.getAllowFlight()) return;
                player.sendMessage(ChatColor.RED + "/" + command + " is disabled while in an event");
                event.setCancelled(true);
                return;
            }
        }
    }

//    @Override
//	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//	    if (!(cmd.getName().equalsIgnoreCase("event"))) {
//	        return false;
//	    }
//	    if (!(sender instanceof Player)) {
//	        sender.sendMessage(ChatColor.RED + "These commands can only be run from ingame!");
//	        return false;
//	    }
//	    ch.Handler(sender, cmd, label, args);
//	    return true;
//	}
	            
	public int reminder() {
			int id = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override 
				public void run() {
					if(running == true && closed == false) {
						if(props.get("reminder") == true) {
							int size = Bukkit.getServer().getOnlinePlayers().length;
							if(!(size*50/100 == pName.size()))
							Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Events" + ChatColor.GRAY + "] " + ChatColor.DARK_AQUA + "An event is in progress! Store your inventory and type /event join " + ChatColor.GOLD + warpName);
						}
					}
				}
			}, 20L, 3000L);	
			return this.id = id;
	}
	
    @EventHandler
    public void PlayerLogOut(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        PermissionUser user = PermissionsEx.getUser(p);
        if (running && pName.contains(event.getPlayer().getName())) {
            Bukkit.getServer().getPlayer(p.getName()).setHealth(0);
            if (props.get("mcmmo") == false) {
                user.removePermission("-mcmmo.ability.*");
                user.removePermission("-mcmmo.skills.*");
                user.removePermission("-mcmmo.perks.*");
            }
            if (props.get("magic") == false) {
                user.removePermission("-magicspells.*");
            }
            if (props.get("playerheads") == false) {
                user.removePermission("-playerheads.canloosehead");
            }
            pName.remove(p.getName());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.isCancelled() || (event.getDamage() == 0) || !running) {
            return;
        }
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Entity dmgr = e.getDamager();
            if (dmgr instanceof Projectile) {
                dmgr = ((Projectile) dmgr).getShooter();
            }
            if ((dmgr instanceof Player) && (e.getEntity() instanceof Player)) {
                Player damager = (Player) dmgr;
                Player receiver = (Player) e.getEntity();
                if (pName.contains(damager.getName()) && pName.contains(receiver.getName())){
                    if ((TeamA.contains(damager.getName()) && TeamA.contains(receiver.getName())) || (TeamB.contains(damager.getName()) && TeamB.contains(receiver.getName())) && teams){
                        event.setCancelled(true);
                        damager.sendMessage(ChatColor.RED + "Do not attack your teammates!");
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        if (running) {
            Player killer = event.getEntity().getKiller();
            Player killed = event.getEntity();
            if (pName.contains(killed.getName()) && pName.contains(killer.getName())) {
                if (TeamA.contains(killer.getName())) {
                    scoreA = objs.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Kills:"));
                    int Score = scoreA.getScore();
                    scoreA.setScore(Score + 1);
                } else if (TeamB.contains(killer.getName())) {
                    scoreB = objs2.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Kills:"));
                    int Score = scoreB.getScore();
                    scoreB.setScore(Score + 1);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event){
        final Player p = event.getPlayer();
        final Location warpLoc = warps.get(warpName);
        new BukkitRunnable(){
            @Override
            public void run(){
                if (warpLoc != null && pName.contains(p.getName())){
                    if (event.getRespawnLocation() != warps.get(warpName)){
                        if (teams == true){
                            if (TeamA.contains(p.getName())){
                                p.teleport(loc1);
                            } else if (TeamB.contains(p.getName())){
                                p.teleport(loc2);
                            }
                        } else if (teams == false){
                            p.teleport(warpLoc);
                        }
                    }
                }
            }
          }.runTaskLater(this, 1L);
    }

    public void pName(List<String> pName) {
        this.pName = pName;
    }

    public void warpName(String warpName) {
        this.warpName = warpName;
    }

    public void warps(Map<String, Location> warps) {
        this.warps = warps;
    }

    public void running(boolean running) {
        this.running = running;
    }

    public void closed(boolean closed) {
        this.closed = closed;
    }
    
    public void teams(boolean teams){
        this.teams = teams;
    }

    public void props(Map<String, Boolean> props) {
        this.props = props;
    }
    
    public void pointA(boolean pointA) {
        this.pointA = pointA;
    }

    public void pointB(boolean pointB) {
        this.pointB = pointB;
    }

    public void bcmd(List<String> blockedcmd) {
        this.blockedcmd = blockedcmd;
    }

    public void locA(Location loc1) {
        this.loc1 = loc1;
    }
    
    public void locB(Location loc2) {
        this.loc2 = loc2;
    }
}

