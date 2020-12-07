package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;

public class setspawn implements CommandExecutor{
	
	private Main plugin;
	public setspawn(Main plugin) {
		this.plugin = plugin;	
		}



	public void locToConfig (Location loc) {
		 File spawn = new File(plugin.getDataFolder() + File.separator + "spawn.yml");
		 FileConfiguration h = YamlConfiguration.loadConfiguration(spawn);
		h.set("locations..world", loc.getWorld().getName());
		h.set("locations.x", loc.getBlockX());
		h.set("locations.y", loc.getBlockY());
		h.set("locations.z", loc.getBlockZ());
				try {
			h.save(spawn);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	public boolean checksender (CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}
		return true;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (checksender(sender)) {
			MessageManager.getManager().msg(sender, MessageType.BAD, "Only players can use this command");
			return true;	
		}
		Player p = (Player) sender;
		if (!sender.hasPermission("str.setspawn")) {
        	MessageManager.getManager().msg(p, MessageType.BAD, "� ��� ��� ���� ��������� ������!");
            return true;
        }
		locToConfig(p.getLocation());
		MessageManager.getManager().msg(sender, MessageType.INFO, "����� ����������!");
		return false;
	}

}
