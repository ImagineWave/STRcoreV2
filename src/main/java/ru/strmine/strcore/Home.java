package ru.strmine.strcore;

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

public class Home implements CommandExecutor {
	
	private Main plugin;
	public Home(Main plugin) {
		this.plugin = plugin;	
	}
	public boolean checkSender (CommandSender sender) {
		return sender instanceof Player;
	}
	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
			if (!checkSender(sender)){
				MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "Only players can use this command");
			return true;	
			}
			Player p = (Player) sender;
			if (!p.hasPermission("str.home")){
				MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "У вас нет прав");
				return true;
			}
			if(p.getLocation().getWorld().getName().equalsIgnoreCase("world_the_end")){
			        MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "sethome в мире world_the_end запрещен");
			        p.kickPlayer("§csethome в мире world_the_end запрещен");
			        Bukkit.broadcastMessage(p.getName() + " §cНе читал правил");
			        return true;      
			}
			locToConfig(p.getName(), p.getLocation());
			MessageManager.getManager().msg(sender, MessageManager.MessageType.INFO, "Дом установлен!");
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
	