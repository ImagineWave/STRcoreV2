package Plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;


public class TpHome implements CommandExecutor {
	
	private Main plugin;
	public TpHome(Main plugin) {
		this.plugin = plugin;	
		}
	public boolean checksender (CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}
		return true;
	}
	@Override
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
		if (checksender(sender)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Only players can use this command");
		return true;	
		}
		if (args.length == 0) {
		Player p = (Player) sender;
		if (!p.hasPermission("str.home")){
			MessageManager.getManager().msg(p, MessageType.BAD, "У вас нет прав");
			return true;
		}
		Location home = configToLoc(p.getName());
		//configToLoc(p.getName());
		p.teleport(home);
		MessageManager.getManager().msg(sender, MessageType.INFO, "Добро пожаловать домой!");
		}
		if (args.length == 1) {
			Player p = (Player) sender;
			  if (!sender.hasPermission("str.home.others")) {
				  Location home = configToLoc(p.getName());
				  p.teleport(home);
				  MessageManager.getManager().msg(sender, MessageType.INFO, "Добро пожаловать домой!");
		            return true;
		        }
			if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
				Player t = (Bukkit.getPlayerExact(args[0]));
				Location home = configToLoc(t.getName());
				p.teleport(home);
				MessageManager.getManager().msg(sender, MessageType.INFO, "Вы телепортировались в дом игрока §6" + t.getName());
				return true;
			}			
			try {
			Location home = configToLoc(args[0]);
			p.teleport(home);
			MessageManager.getManager().msg(sender, MessageType.INFO, "Вы телепортировались в дом игрока §6" + args[0] +" §cВнимание, игрок не в сети");
			}
			catch (IllegalArgumentException e) {
				MessageManager.getManager().msg(sender, MessageType.BAD, "Игрок незарегистрирован и/или не имеет дома");
				return true;
			}
		}
	return true;
}
	public Location configToLoc (String name) {
		 File homes = new File(plugin.getDataFolder() + File.separator + "homes.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		 Location loc = new Location(Bukkit.getServer().getWorld(h.getString("locations." + name + ".world")),
				h.getDouble("locations." + name + ".x"),
				h.getDouble("locations." + name + ".y"),
				h.getDouble("locations." + name + ".z"));
		return loc;
	}
}
