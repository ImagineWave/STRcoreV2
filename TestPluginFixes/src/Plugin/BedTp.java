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

public class BedTp implements CommandExecutor{
	private Main plugin;

	public BedTp(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 if (!(sender instanceof Player)) {
	            sender.sendMessage("Only for player usage!");
	            return true;
	        }
		 Player p = (Player) sender;
		 if(!p.hasPermission("str.bedtp")) return true;
		try{
			 	Location bed = configToLoc(args[0]);
				p.teleport(bed);
				MessageManager.getManager().msg(sender, MessageType.INFO, "Вы телепортировались к кровати игрока §6" + args[0]);
				}
				catch (IllegalArgumentException e) {
					MessageManager.getManager().msg(sender, MessageType.BAD, "Игрок незарегистрирован и/или не имеет кровати");
					return true;
			}
		return false;
	}

	
	public Location configToLoc (String name) {
		 File beds = new File(plugin.getDataFolder() + File.separator + "beds.yml");
		 FileConfiguration b = YamlConfiguration.loadConfiguration(beds);
		 Location loc = new Location(Bukkit.getServer().getWorld(b.getString("locations." + name + ".world")),
				b.getDouble("locations." + name + ".x"),
				b.getDouble("locations." + name + ".y"),
				b.getDouble("locations." + name + ".z"));
		return loc;
	}
}
