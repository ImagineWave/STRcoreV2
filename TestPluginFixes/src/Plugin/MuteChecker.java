package Plugin;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Plugin.MessageManager.MessageType;

public class MuteChecker implements Listener{

	private Main plugin;
	
	public MuteChecker(Main plugin) {
		this.plugin = plugin;
	}

@EventHandler
public void MuteCheckers(AsyncPlayerChatEvent e) {
	Player p = e.getPlayer();
	File muted = new File(plugin.getDataFolder() + File.separator + "muted.yml");
	 FileConfiguration m = YamlConfiguration.loadConfiguration(muted);
	 List<String> list = m.getStringList("users");
	if(!list.contains(p.getName())) return;
	{
		int index = list.indexOf(p.getName());
		int time = index + 1;
		int res = index + 2;
		Long duration = Long.parseLong(list.get(time));
		Long qtime = System.currentTimeMillis();
		if(duration>qtime) {
			e.setCancelled(true);
			MessageManager.getManager().msg(p, MessageType.BAD, "У вас блокировка чата еще §6"+ (duration - qtime)/1000 + " §cсекунд по причине §6" + list.get(res));
			return;
		}
	}
	
	
}
}
