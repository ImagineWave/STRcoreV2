package Plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;

public class tpSpawn implements CommandExecutor {
	
	private Main plugin;
	public tpSpawn(Main plugin) {
		this.plugin = plugin;	
		}
	public boolean checksender (CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}
		return true;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (checksender(sender)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Only players can use this command");
		return true;	
		}
		if (!sender.hasPermission("str.spawn")) {
			  MessageManager.getManager().msg(sender, MessageType.GOOD, "Координаты спауна x=50, z=470");
	            return true;
	        }
		Player p = (Player) sender;
		Location spawn = SpawnToLoc();
		p.teleport(spawn);
		MessageManager.getManager().msg(p, MessageType.INFO, "Добро пожаловать на спаун");
		return false;
	}
	
	public Location SpawnToLoc () {		
		 File homes = new File(plugin.getDataFolder() + File.separator + "spawn.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		Location spawn = new Location(Bukkit.getServer().getWorld(h.getString("locations.world")),
				h.getDouble("locations.x"),
				h.getDouble("locations.y"),
				h.getDouble("locations.z"));
		return spawn;
	}
}
