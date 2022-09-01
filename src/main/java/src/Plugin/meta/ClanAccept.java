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

public class ClanAccept implements CommandExecutor{
	private Main plugin;
	public ClanAccept(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		joinClan(p);
		return true;
	}
	private void joinClan(Player p) {
		String inviteName = getInviteName(p);
		String clanName = getClanName(p.getName());
		if((inviteName ==null)||(inviteName.equals("0"))) {
			MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет активных приглашений");
			return;
		}
		if(!((clanName ==null)||(clanName.equals("0") ))) {
			MessageManager.getManager().msg(p, MessageType.BAD, "Сначала покиньте ваш клан!");
			return;
		}
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		List<String> listmembers = c.getStringList("Clans."+inviteName+".members");
		c.set("Players."+p.getName()+".clan", inviteName);
		c.set("Players."+p.getName()+".invite", "0");
		listmembers.add(p.getName());
		c.set("Clans."+inviteName+".members", listmembers);
		try {
			c.save(clans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user "+p.getName()+" parent add clan."+inviteName);
		MessageManager.getManager().msg(p, MessageType.GOOD, "Вы вступили в клан §6"+inviteName+"§a перезайдите в игру для обновления в TAB");
	}
	private String getInviteName(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p.getName()+".invite");
		return output;
	}
	private String getClanName(String p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p+".clan");
		return output;
	}
}
