//This work is licensed under the Creative Commons Attribution-NoDerivs 3.0 United States License. 
//To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/3.0/us/ 
//or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

// WANNA USE MY CODE???? GIVE ME CREDIT!
package us.william.modsiren;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.logging.Logger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import com.matejdro.bukkit.jail.*;

public class ModSiren extends JavaPlugin {
	private final MSPlayerListener PlayerListener = new MSPlayerListener(this);
	private static final Logger log = Logger.getLogger("Minecraft");
	public static PermissionHandler permissionHandler;
	public static JailAPI jail;
	public Bukkit server;
	public Configuration config;
	public BukkitScheduler scheduler;
	protected static String SIRENSENT_MESSAGE = "Help is being sent!";
	protected static String MODSIREN_MESSAGE = " needs your help";
	protected static String SIRENREPORT_MESSAGE = "Mod Will Recieve The Report Soon!";
	protected static String MODREPORT_MESSAGE = " Reported Illegal Activity!";
	protected static String HELPTITLE_MESSAGE = "------~Siren Help~------";
	protected static String HELPREPORT_MESSAGE = "/report";
	protected static String HELPSIREN_MESSAGE = "/siren";
	protected static String HELPREPLY_MESSAGE = " /reply * Mods Only! * ";
	protected static String REPLYMAIN_MESSAGE = "Sent to the accused!";
	protected static String REPLYSECONDARY_MESSAGE = " isn't online right now! ";
	protected static String ILLEGALBLOCKONE_MESSAGE = " No Littering! It Makes Lag ";
	protected static String ILLEGALBLOCKTWO_MESSAGE = " --Dropped An Illegal Item";
	protected static String REPEATTITLE_MESSAGE = " Your Moderators Are - ";
	protected static String REPEATMOD_MESSAGE = "Put your Moderator Names Here.";
	protected static String REPEATMODTWO_MESSAGE = "Secondary Line-for extraspace!";

	@Override
	public void onDisable()
	{
		log.info(getDescription().getName() + " version " + getDescription().getVersion() + " unloaded.");
	}
	@Override
	public void onEnable() {
		final Plugin plugin = getServer().getPluginManager().getPlugin("Jail");
		if (plugin != null){
			jail = ((Jail) plugin).API;
		}else{
		}
		loadConfig();
		setupPermissions();
		log.info(getDescription().getName() + " version " + getDescription().getVersion() + " loaded.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, this.PlayerListener, Event.Priority.High, this);
	}
	public void loadConfig(){
		Configuration config = getConfiguration();
		config.load();
		SIRENSENT_MESSAGE = config.getString("ModSiren.SirenSent-Message", SIRENSENT_MESSAGE);
		MODSIREN_MESSAGE = config.getString("ModSiren.ModSiren-Message", MODSIREN_MESSAGE);
		SIRENREPORT_MESSAGE = config.getString("ModSiren.SirenReport-Message", SIRENREPORT_MESSAGE);
		MODREPORT_MESSAGE = config.getString("ModSiren.ModReport-Message", MODREPORT_MESSAGE);
		HELPTITLE_MESSAGE = config.getString("ModSiren.HelpTitle-Message", HELPTITLE_MESSAGE);
		HELPREPORT_MESSAGE = config.getString("ModSiren.HelpReport-Message", HELPREPORT_MESSAGE);
		HELPSIREN_MESSAGE = config.getString("ModSiren.HelpSiren-Message", HELPSIREN_MESSAGE);
		HELPREPLY_MESSAGE = config.getString("ModSiren.HelpReply-Message", HELPREPLY_MESSAGE);
		REPLYMAIN_MESSAGE = config.getString("ModSiren.ReplyMain-Message", REPLYMAIN_MESSAGE);
		REPLYSECONDARY_MESSAGE = config.getString("ModSiren.ReplySecondary-Message", REPLYSECONDARY_MESSAGE);
		ILLEGALBLOCKONE_MESSAGE = config.getString("ModSiren.IllegalBlockone-Message", ILLEGALBLOCKONE_MESSAGE);
		ILLEGALBLOCKTWO_MESSAGE = config.getString("ModSiren.IllegalBlocktwo-Message", ILLEGALBLOCKTWO_MESSAGE);
		REPEATTITLE_MESSAGE = config.getString("ModSiren.RepeatTitle-Message", REPEATTITLE_MESSAGE);
		REPEATMOD_MESSAGE = config.getString("ModSiren.RepeatMod-Message", REPEATMOD_MESSAGE);
		REPEATMODTWO_MESSAGE = config.getString("ModSiren.RepeatModTwo-Message", REPEATMODTWO_MESSAGE);
		config.save(); 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (sender instanceof Player) ? (Player)sender : null;
		if (player == null) return false;
		if (label.equalsIgnoreCase("siren")) {
			player.sendMessage(ChatColor.GREEN + ModSiren.SIRENSENT_MESSAGE);
			if (permissionHandler.has((Player)sender, "Mod.Siren")) {
				player.sendMessage(ChatColor.GRAY + player.getName() + ChatColor.DARK_RED + ModSiren.MODSIREN_MESSAGE);
				return true;
			}
		} else if (label.equalsIgnoreCase("report")) {
			player.sendMessage(ChatColor.GREEN + ModSiren.SIRENREPORT_MESSAGE);
			if (permissionHandler.has((Player)sender, "Mod.Report")) {
				player.sendMessage(ChatColor.GRAY + player.getName() + ChatColor.DARK_RED + ModSiren.MODREPORT_MESSAGE);
				return true;
			}
		} else {
			if (label.equalsIgnoreCase("Shelp")) {
				player.sendMessage(ChatColor.DARK_RED + ModSiren.HELPTITLE_MESSAGE);
				player.sendMessage(ChatColor.DARK_RED + ModSiren.HELPREPORT_MESSAGE);
				player.sendMessage(ChatColor.DARK_RED + ModSiren.HELPSIREN_MESSAGE);
				player.sendMessage(ChatColor.DARK_RED + ModSiren.HELPREPLY_MESSAGE);
				return true;
			}
			if ((label.equalsIgnoreCase("reply")) && 
					(permissionHandler.has((Player)sender, "Mod.Reply")) && 
					(args.length == 1)) {
				String name = args[0];
				Player tpTarget = getServer().getPlayer(name);
				if (tpTarget != null) {
					player.teleport(tpTarget);
					player.sendMessage(ChatColor.RED + ModSiren.REPLYMAIN_MESSAGE);
				}
				else {
					player.sendMessage(ChatColor.DARK_RED + name + ChatColor.GRAY + ModSiren.REPLYSECONDARY_MESSAGE);
				}return true;
			}
			if ((label.equalsIgnoreCase("jsiren")) && 
					(permissionHandler.has((Player)sender, "Mod.Jail")) &&
					(args.length == 3)) {
				String name = args[0];
				int time = 0;
				String reason = args[2];
				jail.jailPlayer(name, time, null, reason);
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + name + ChatColor.GRAY + " Was Jailed For" + ChatColor.RED + time + " Minute(s) " +  " Becuase Of " + ChatColor.RED + reason);
			}return true;
		}
		return false;
	}
	private void setupPermissions() {
		if (permissionHandler != null) {
			return;
		}
		Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");
		if (permissionsPlugin == null) {
			log.info("[MOD SIREN] Permission system not detected, defaulting to OP");
			return;
		}
		permissionHandler = ((Permissions)permissionsPlugin).getHandler();
		log.info("[MOD SIREN] Found and will use plugin " + ((Permissions)permissionsPlugin).getDescription().getFullName());{
		}
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + ModSiren.REPEATTITLE_MESSAGE);
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + ModSiren.REPEATMOD_MESSAGE);
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + ModSiren.REPEATMODTWO_MESSAGE);
			}}, 20L, 10400L); 
	}
}