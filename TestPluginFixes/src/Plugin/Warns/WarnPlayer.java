package Plugin.Warns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;

public class WarnPlayer implements CommandExecutor{
	
	private Main plugin;
	public WarnPlayer(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.warn")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return true;
		}
		if(args.length<2) {
			MessageManager.getManager().msg(sender, MessageType.INFO, "/warn <игрок> <причина> - Выдать варн");
			MessageManager.getManager().msg(sender, MessageType.INFO, "/warn <list/remove> <игрок> - Список варнов игрока/Удалить все варны");
			return true;
		}
		Player p = (Player) sender;
		switch (args[0]) {
			case("list"):{
				String targetName = args[1];
				if(!isPlayerExist(targetName)) {
					MessageManager.getManager().msg(sender, MessageType.BAD, "Игрока "+targetName+" не существует");
					return true;
				}
				showWarnList(p, targetName);
				break;
			}
			case("remove"):{
				String targetName = args[1];
				if(!isPlayerExist(targetName)) {
					MessageManager.getManager().msg(sender, MessageType.BAD, "Игрока "+targetName+" не существует");
					return true;
				}
				removeWarns(p,targetName);
				break;
			}
			default:{
				String targetName = args[0];
				if(!isPlayerExist(targetName)) {
					MessageManager.getManager().msg(sender, MessageType.BAD, "Игрока "+targetName+" не существует");
					return true;
				}
				String reason = "";
				for (int i = 1; i != args.length; i++) {
					reason += args[i] +" ";
				}
				if(reason.contains("|")) {
					MessageManager.getManager().msg(sender, MessageType.BAD, "Символ §6|§c запрещен");
					return true;
				}
				setWarn(p,targetName,reason);
				break;
			}
		}
		return true;
	}
	private void setWarn(Player p, String target, String reason) {
		File warns = new File(plugin.getDataFolder() + File.separator + "Warns.yml");
		FileConfiguration w = YamlConfiguration.loadConfiguration(warns);
		Long time = System.currentTimeMillis()/1000;
		List<String> list = w.getStringList("Player."+target+".warnlist");
		list.add(time+"|"+p.getName()+"|"+reason);
		w.set("Player."+target+".warnlist", list);
		try {
			w.save(warns);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> actualList = getActualWarns(target);
		Integer counter = actualList.size();
		Bukkit.broadcastMessage("§7[§cНаказание§7]: §6"+ p.getName()+" §bвыдал варн игроку §6"+target+" ("+counter+"/3) §b по причине §6"+ reason);
	}
	private void showWarnList(Player p, String targetName) {
		List<String> list = getActualWarns(targetName);
		p.sendMessage("§bСписок варнов игрока §6"+targetName);
		for(String warn:list) {
			p.sendMessage(warn);
		}
	}
	private void removeWarns(Player p, String targetName) {
		File warns = new File(plugin.getDataFolder() + File.separator + "Warns.yml");
		FileConfiguration w = YamlConfiguration.loadConfiguration(warns);
		List<String> list = w.getStringList("Player."+targetName+".warnlist");
		list.clear();
		w.set("Player."+targetName+".warnlist", list);
		try {
			w.save(warns);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.broadcastMessage("§7[§cНаказание§7]: §6"+ p.getName()+" §aснял все варны с игрока §6"+targetName);
	}
	private List<String> getActualWarns(String name) {
		File warns = new File(plugin.getDataFolder() + File.separator + "Warns.yml");
		FileConfiguration w = YamlConfiguration.loadConfiguration(warns);
		Long time = System.currentTimeMillis()/1000;
		List<String> list = w.getStringList("Player."+name+".warnlist");
		List<String> actualList = new ArrayList<>();
		for(String str:list) {
			String[] splittedString = str.split("\\|");
			Long warnTime = Long.parseLong(splittedString[0]);
			warnTime = warnTime+604800l;
			if(warnTime>time) {//604800 НЕДЕЛЯ
				actualList.add("§4" +(splittedString[1])+" §6по причине §4"+splittedString[2]);
			}
		}
		return actualList;
	}
	private boolean isPlayerExist(String name) {
		File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		return list.contains(name);
	}
}
