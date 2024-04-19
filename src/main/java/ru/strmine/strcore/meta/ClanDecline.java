package ru.strmine.strcore.meta;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.strmine.strcore.Main;
import ru.strmine.strcore.MessageManager;

public class ClanDecline implements CommandExecutor{
	private Main plugin;
	public ClanDecline(Main plugin) {
		this.plugin =plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		declineClan(p);
		return true;
	}
	private void declineClan(Player p) { 
		String clanName = getClanName(p);
		if((clanName ==null)||(clanName.equals("0"))) {
			MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "У вас нет активных приглашений");
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		c.set("Players."+p.getName()+".invite", "0");
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageManager.getManager().msg(p, MessageManager.MessageType.BAD, "Вы отклонили приглашение в клан §6"+clanName);
	}
	private String getClanName(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p.getName()+".invite");
		
		return output;
	}
}
