package plugin.fixes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import plugin.Main;

public class XrayListCMD implements CommandExecutor{
	private Main plugin;
	public XrayListCMD(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("str.setxrayprefix"))) {
			sender.sendMessage("§cУ вас нет прав");
			return true;
		}
		Player p = (Player) sender;
		if(args.length==0) {
			showSusList(p);
			return true;
		}
		if(args.length==1) {
			p.sendMessage("/xraylist remove <ник>");
			return true;
		}
		if(args.length==2) {
			if(args[0].equals("remove")) {
				String name = args[1];
				if(removeNameFromList(name)) {
					p.sendMessage("§aВы убрали §6"+name+"§a из списка подозреваемых");
					return true;
				}
				p.sendMessage("§cИгрока §6"+name+"§c нет в списке подозреваемых");
				return true;
			}
		}
		return true;
	}
	
	private void showSusList(Player p) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		List<String> list = h.getStringList("suspiciousPlayers");
		for(String name: list) {
			p.sendMessage("§b"+name);
		}
		p.sendMessage("§4После проверки игрока УБИРАЙТЕ его из списка: /xraylist remove <ник>");
	}
	private boolean removeNameFromList(String name) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		List<String> list = h.getStringList("suspiciousPlayers");
		if(list.contains(name)) {
			list.remove(name);
			h.set("suspiciousPlayers", list);
			try {
				h.save(homes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
