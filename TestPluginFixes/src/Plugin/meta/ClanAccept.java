package Plugin.meta;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.Main;
import Plugin.MessageManager;
import Plugin.MessageManager.MessageType;

public class ClanAccept implements CommandExecutor{
	private Main plugin;
	public ClanAccept(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender.hasPermission("str.clanmember"))&&(!sender.isOp())) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы уже состоите в клане");
			return true;
		}
		Player p = (Player) sender;
		joinClan(p);
		return true;
	}
	private void joinClan(Player p) {
		String clanName = getClanName(p);
		p.sendMessage(clanName);//TODO УБРАТЬ ПОЗЖЕ
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		c.set("Players."+p.getName()+".clan", clanName);
		c.set("Players."+p.getName()+".invite", "0");
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" parent add clan."+clanName);
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы вступили в клан §6"+clanName+"§a перезайдите в игру для обновления в TAB");
	}
	private String getClanName(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p.getName()+".invite");
		return output;
	}
}
