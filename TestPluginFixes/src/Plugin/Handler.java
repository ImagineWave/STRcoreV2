package Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Plugin.MessageManager.MessageType;

public class Handler implements Listener {
	
private Main plugin;
	
public Handler(Main plugin) {
	this.plugin = plugin;
}

@EventHandler
	public void spawnplayer(PlayerJoinEvent e) {
	Player p = e.getPlayer();
	//Location spawn = SpawnToLoc();
	//p.setBedSpawnLocation(spawn, true);
	String name = p.getName();
	MessageManager.getManager().msg(p, MessageType.INFO, "Добро пожаловать на сервер, "+ name);
	File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
	FileConfiguration users = YamlConfiguration.loadConfiguration(players);
	List<String> list = users.getStringList("users");//govno ebanoe
	if(list.contains(p.getName())) return;
	{
		list.add(p.getName());
		Location spawn = SpawnToLoc();
		p.teleport(spawn);
		Bukkit.broadcastMessage("§aПоприветствуем нового игрока §d§l" + p.getName()+"§a на §b§lSTR§a§lmine§a!");
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 600, 4), true);
		p.setBedSpawnLocation(spawn, true);
	}
	users.set("users", list);
	try {
		users.save(players);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
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
public Location SpawnToLoc () {		
	 File homes = new File(plugin.getDataFolder() + File.separator + "spawn.yml");
	 FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
	Location spawn = new Location(Bukkit.getServer().getWorld(h.getString("locations.world")),
			h.getDouble("locations.x"),
			h.getDouble("locations.y"),
			h.getDouble("locations.z"));
	return spawn;
}
}
	