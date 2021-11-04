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
		createClan(p, clanName, clanTag);
		return true;
	}
	private void createClan(Player p, String name, String tag) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp creategroup clan."+name);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+name+" meta setsuffix &f["+tag+"&f]");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp group clan."+name+" permission set str.clanmember true");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" parent add clan."+name);
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" permission set str.clan.owner true");
		//Добавить клан в файл
		c.set("Players."+p.getName()+".clan", name);
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
}