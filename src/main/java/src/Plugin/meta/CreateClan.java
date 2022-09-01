package main.java.src.Plugin.meta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class CreateClan implements CommandExecutor{
	private Main plugin;
	public CreateClan(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.clan.create")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return true;
		}
		Player p = (Player) sender;
		if(args.length<2) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "/clancreate <название> <тег>");
			return true;
		}
		String clanName = args[0];
		String clanTag = args[1];
		if((sender.hasPermission("str.clanmember"))&&(!p.isOp())) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы уже состоите в клане");
			return true;
		}
		if(!isClanNameAllowed(clanName)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Это имя клана уже занято");
			return true;
		}
		if(checkTagAndBypass(p, clanTag)) {
			createClan(p, clanName, clanTag);
			return true;
		}
		return true;
	}
	private void createClan(Player p, String name, String tag) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp creategroup clan."+name);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+name+" meta setsuffix &f["+tag+"&f]");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+name+" permission set str.clanmember true");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" parent add clan."+name);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" permission set str.clan.owner true");//TODO УБРАТЬ
		//Добавить клан в файл
		c.set("Players."+p.getName()+".clan", name);
		c.set("Clans."+name+".owner", p.getName());
		List<String> listmembers = new ArrayList<>();
		listmembers.add(p.getName());
		c.set("Clans."+name+".members", listmembers);
		List<String> list = c.getStringList("clansList");
		list.add(name);
		c.set("clansList", list);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageManager.getManager().msg(p, MessageType.GOOD, "Клан создан, перезайдите в игру для обновления в TAB");
	}
	private boolean isClanNameAllowed(String name) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> list = c.getStringList("clansList");
		if(list.contains(name)) {
			return false;
		}
		return true;
	}
	private boolean checkTagAndBypass(Player p, String prefix) {
		if(p.hasPermission("str.prefix.bypass")) {
			if(isThereShittySymbols(prefix)) {
				MessageManager.getManager().msg(p, MessageType.BAD, "Никаких §kкринж §cв теге");
				return false;
			}
			return true;
		}
		if(isPrefixAllowed(p,prefix)) {
			return true;
		}
		return false;
	}
	private boolean isPrefixAllowed(Player p, String prefix) {
		if(getPrefixLenth(prefix)>7) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Длина Тега не более 7 симоволов");
			return false;
		}
		if(!checkSpecSymbols(prefix)) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Разрешены буквы (A-z) (А-я), цифры и смвол &");
			return false;
		}
		if(isThereShittySymbols(prefix)) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Никаких §kкринж §cв теге");
			return false;
		}
		return true;
	}
	private boolean isThereShittySymbols (String prefix) {
		for (int i = 0; i<prefix.length()-1; i++) {
			if(prefix.charAt(i)=='&') {
				if(prefix.charAt(i+1)=='k') {
					return true;
				}
			}
		}
		return false;
	}
	private int getPrefixLenth(String prefix) {
		int length = prefix.length();
		return length;
	}
	
	private boolean checkSpecSymbols(String prefix) {
		int count = 0;
		for( int i = 0; i <prefix.length()-1; i++) {
			if(!Character.isAlphabetic(prefix.charAt(i))) {
				if(!Character.isDigit(prefix.charAt(i))){
					if(!Character.isSpaceChar(prefix.charAt(i))) {
						if(prefix.charAt(i) != '&') {
							count++;
						}
					}
				}
			}
		}
		if (count==0) {
			return true;
		}
		return false;
	}
}
