package main.java.src.Plugin.meta;

import java.io.File;
import java.io.IOException;
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

public class ClanLeave implements CommandExecutor{
	private Main plugin;
	public ClanLeave (Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Сначала используйте /deop "+sender.getName());
			return true;
		}
		if(!sender.hasPermission("str.clanmember")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		Player p = (Player) sender;
		if(checkOwner(p)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Владелец не может покинуть клан, используйте /clanmodify delete <название>");
			return true;
		}
		leaveClan(p);
		return true;
	}
	private void leaveClan(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" parent remove clan."+clanName);
		c.set("Players."+p.getName()+".clan", "0");
		List<String> listmembers = c.getStringList("Clans."+clanName+".members");
		listmembers.remove(p.getName());
		c.set("Clans."+clanName+".members", listmembers);
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы покинули клан §6"+clanName+"§a перезайдите в игру для обновления в TAB");
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getClanName(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p.getName()+".clan");
		return output;
	}
	private String getClanName(String p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p+".clan");
		return output;
	}
	private boolean checkOwner(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p.getName());
		String ownerName = c.getString("Clans."+clanName+".owner");
		return p.getName().equals(ownerName);
	}
}
