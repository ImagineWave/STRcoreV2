package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;

public class Home implements CommandExecutor {
	
	private Main plugin;
	public Home(Main plugin) {
		this.plugin = plugin;	
	}
	public boolean checksender (CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}
		return true;
	}
	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
			if (checksender(sender)) {
				MessageManager.getManager().msg(sender, MessageType.BAD, "Only players can use this command");
			return true;	
			}
			Player p = (Player) sender;
			if (!p.hasPermission("str.home")){
				MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав");
				return true;
			}
			if(p.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")){
			        MessageManager.getManager().msg(p, MessageType.BAD, "sethome в мире the_end запрещен");
			        p.kickPlayer("§csethome в мире the_end запрещен");
			        Bukkit.broadcastMessage(p.getName() + " §cНе умеет читать");
			        return true;      
			}
			locToConfig(p.getName(), p.getLocation());
			MessageManager.getManager().msg(sender, MessageType.INFO, "Дом установлен!");
		return true;
	}
	
	 public void locToConfig (String name, Location loc) {
		 File homes = new File(plugin.getDataFolder() + File.separator + "homes.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		h.set("locations." + name + ".world", loc.getWorld().getName());
		h.set("locations." + name + ".x", loc.getBlockX());
		h.set("locations." + name + ".y", loc.getBlockY());
		h.set("locations." + name + ".z", loc.getBlockZ());
				try {
			h.save(homes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
}
	