package Plugin.meta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;

public class ClanModify implements CommandExecutor{
	
	private Main plugin;
	public ClanModify(Main plugin) {
		this.plugin =plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.clanmember")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		if(args.length < 1) {
			MessageManager.getManager().msg(sender, MessageType.INFO, "/clanmodify tag/kick/promote/demote/delete, tag/playerName/clanName");
			return true;
		}
		String option = args[0];
		Player p = (Player) sender;
		switch (option) {
			case("tag"):{
				if(checkTagAndBypass(p,args[1])) {
					changeClanTag(p,args[1]);
				}
				break;
			}
			case("kick"):{
				kickClanMember(p,args[1]);
				break;
			}
			case("delete"):{
				deleteClanName(p,args[1]);
				break;
			}
			case("promote"):{
				promotePlayer(p, args[1]);
				break;
			}
			case("demote"):{
				demotePlayer(p, args[1]);
				break;
			}
			default:{
				MessageManager.getManager().msg(sender, MessageType.INFO, "/clanmodify tag/kick/delete, tag/playerName/clanName");
				break;
			}
		}
		return true;
	}
	private void changeClanTag(Player p, String tag) {
		String clanName = getClanName(p.getName());
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+clanName+" meta setsuffix &f["+tag+"&f]");
		MessageManager.getManager().msg(p, MessageType.GOOD, "Новый тег установлен, перезайдите в игру для отображения");
		return;
	}
	private void kickClanMember(Player p, String name) {
		if(!isPlayerExists(name)) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Игрока с ником §6"+name+"§c не существует");
			return;
		}
		String clanName = getClanName(p.getName());
		String targetsClanName = getClanName(name);
		if(!clanName.equals(targetsClanName)){
			MessageManager.getManager().msg(p, MessageType.BAD, "Игрок §6"+name+"§c из другого клана!");
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> listmembers = c.getStringList("Clans."+clanName+".members");
		listmembers.remove(name);
		c.set("Clans."+clanName+".members", listmembers);
		c.set("Players."+name+".clan", "0");
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+name+" parent remove clan."+clanName);
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы выгнали из клана игрока §6"+name);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "kick "+name+" §4Вас выгнали из клана!");
		return;
		}
	private void deleteClanName(Player p, String clan) {
		String clanName = getClanName(p.getName());
		if(!clanName.equals(clan)) {
			MessageManager.getManager().msg(p, MessageType.INFO, "Подтвердите удаление вашего клана /clanmodify delete "+clanName);
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> list = c.getStringList("clansList");
		list.remove(clan);
		c.set("clansList", list);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+clanName+" clear");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp deletegroup clan."+clanName);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" permission unset str.clan.owner true");
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы удалили клан "+clanName);
	}
	private String getClanName(String p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p+".clan");
		return output;
	}
	private boolean isPlayerExists(String name) {
		File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
		FileConfiguration users = YamlConfiguration.loadConfiguration(players);
		List<String> list = users.getStringList("users");//govno ebanoe
		return list.contains(name);
	}
	///////////////////
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
	private boolean checkOwner(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String clanName = getClanName(p.getName());
		String ownerName = c.getString("Clans."+clanName+".owner");
		return p.getName().equals(ownerName);
	}
	private void promotePlayer(Player sender, String target) {
		String clanName = getClanName(sender.getName());
		String targetsClan = getClanName(target);
		if(!checkOwner(sender)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return;
		}
		if(!clanName.equals(targetsClan)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Игрок из другого клана");
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> officers = c.getStringList("Clans."+clanName+".officers");
		officers.add(target);
		c.set("Clans."+clanName+".officers", officers);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void demotePlayer(Player sender, String target) {
		String clanName = getClanName(sender.getName());
		String targetsClan = getClanName(target);
		if(!checkOwner(sender)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "У вас нет прав");
			return;
		}
		if(!clanName.equals(targetsClan)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Игрок из другого клана");
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> officers = c.getStringList("Clans."+clanName+".officers");
		officers.remove(target);
		c.set("Clans."+clanName+".officers", officers);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
