package Plugin.meta;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;
import Plugin.StrPlayer;
import RaceRise.RiseOfTheRaceAPI;

public class SetPrefix implements CommandExecutor{
	private Main plugin;
	public SetPrefix(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(args.length==0){
			if(sender.hasPermission("str.setprefix")) {
				MessageManager.getManager().msg(sender, MessageType.BAD, "/setprefix <Prefix>");
				return true;
			}
			if(sender.hasPermission("str.setprefix.others")) {
				MessageManager.getManager().msg(sender, MessageType.BAD, "/setprefix <Игрок> <Prefix>");
				return true;
			}
		}
		if(args.length==1){
			if(!(sender instanceof Player)) {
				MessageManager.getManager().msg(sender, MessageType.BAD, "/setprefix <Игрок> <Prefix>");
				return true;
			}
			if(!p.hasPermission("str.setprefix")) {
				MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав");
				return true;
			}
			String prefix = args[0];
			checkPrefixAndBypass(p,prefix);
			return true;
		}
		if(args.length==2) {
			if(!sender.hasPermission("str.setprefix.others")) {
				MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав");
				return true;
			}
			if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
				MessageManager.getManager().msg(p, MessageType.BAD, "Игрок не в сети");
				return true;
			}
			Player target = (Bukkit.getPlayerExact(args[0]));
			String prefix = args[1];
			setPrefixToCFG(p,prefix);
			setPrefixToGame(p);
			MessageManager.getManager().msg(sender, MessageType.GOOD, "Вы установили префикс §6"+prefix+" §aигроку §6"+target.getName());
			return true;
		}
		return false;
	}
	private void checkPrefixAndBypass(Player p, String prefix) {
		if(p.hasPermission("str.prefix.bypass")) {
			if(isThereShittySymbols(prefix)) {
				MessageManager.getManager().msg(p, MessageType.BAD, "Никаких §kкринж §cв префиксе");
				return;
			}
			setPrefixToCFG(p,prefix);
			setPrefixToGame(p);
			MessageManager.getManager().msg(p, MessageType.GOOD, "Префикс установлен, перезайдите на сервер");
			return;
		}
		if(isPrefixAllowed(p,prefix)) {
			setPrefixToCFG(p,prefix);
			setPrefixToGame(p);
			MessageManager.getManager().msg(p, MessageType.GOOD, "Префикс установлен, перезайдите на сервер");
			return;
		}
	}
	private boolean isPrefixAllowed(Player p, String prefix) {
		if(getPrefixLenth(prefix)>10) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Длина префикса не более 10 симоволов");
			return false;
		}
		if(!checkSpecSymbols(prefix)) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Разрешены буквы (A-z) (А-я), цифры и смвол &");
			return false;
		}
		if(isThereShittySymbols(prefix)) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Никаких §kкринж §cв префиксе");
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
	private void setPrefixToCFG(Player p, String prefix) {
		StrPlayer spl = new StrPlayer(p, plugin);
		spl.setPrefix(prefix);
		spl.setPlayerCfg(spl);
		return;
	}
	private void setPrefixToGame(Player p) {
		StrPlayer spl = new StrPlayer(p, plugin);
		String prefix = spl.getPrefix();
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user " + p.getName() + " meta removeprefix 100");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user " + p.getName() + " meta addprefix 100 &r["+prefix+"&r]");
		return;
	}
	private String modifyRaceAndLevelToPrefix(String race, Integer level) {
		String output = "";
		switch(race) {
			case("Human"):{
				output = "&e("+level.toString()+")";
				break;
			}
			case("Elf"):{
				output = "&a("+level.toString()+")";
				break;
			}
			case("Dwarf"):{
				output = "&9("+level.toString()+")";
				break;
			}
			case("Orc"):{
				output = "&c("+level.toString()+")";
				break;
			}
			case("Naga"):{
				output = "&b("+level.toString()+")";
				break;
			}
			case("Sky"):{
				output = "&f("+level.toString()+")";
				break;
			}
		}
		return null;
	}
}
