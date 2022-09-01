package main.java.src.Plugin.meta;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.java.src.Plugin.Main;
import main.java.src.Plugin.MessageManager;
import main.java.src.Plugin.MessageManager.MessageType;

public class ClanList implements CommandExecutor{
	private Main plugin;
	public ClanList(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.clanmember")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		Player p = (Player) sender;
		List<String> members = getMembers(p.getName());
		p.sendMessage("§aСписок участников клана §6"+getClanName(p.getName()));
		for(String member:members) {
			p.sendMessage("§a"+member);
		}
		return true;
	}
	private List<String> getMembers(String p){
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p);
		List<String> listmembers = c.getStringList("Clans."+clanName+".members");
		return listmembers;
	}
	private String getClanName(String p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p+".clan");
		return output;
	}
}
