package Plugin.meta;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Plugin.Main;

public class SetXray implements CommandExecutor{
	private Main plugin;
	public SetXray(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("str.setxrayprefix"))) {
			sender.sendMessage("§cУ вас нет прав");
			return true;
		}
		if (args.length==1) {
			if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
				Player t = (Bukkit.getPlayerExact(args[0]));
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user " + t.getName() + " parent add xray");
				return true;
			}
			return true;
		}else {
			sender.sendMessage("§c/setxray <игрок>");
			return true;
		}
	}

}
