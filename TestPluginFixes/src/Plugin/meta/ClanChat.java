package Plugin.meta;

import java.io.File;

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

public class ClanChat implements CommandExecutor{
	private Main plugin;
	public ClanChat(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("str.clanmember")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		if (args.length == 0) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "/cc <Сообщение>");
			return true;
		}
		Player p = (Player) sender;
		if(getClanName(p).equals("0")) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Вы не состоите в клане");
			return true;
		}
		String message = "";
		for (int i = 0; i != args.length; i++) {
			message += args[i] +" ";
		}
		for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
			if (getClanName(pls).equals(getClanName(p))) {
				pls.sendMessage("§6[§2K§6] [§b"+p.getName()+"§6]:§3 "+ message);
			} else if(pls.hasPermission("str.spy")) {
				pls.sendMessage("§7[SPY] [K] ["+p.getName()+"]: "+message);
			}
		}
		return true;
	}
	private String getClanName(Player p) {
		File clans = new File(plugin.getDataFolder() + File.separator + "Clans.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(clans);
		String output = c.getString("Players."+p.getName()+".clan");
		if(output == null) {
			return "0";
		}
		return output;
	}
}
