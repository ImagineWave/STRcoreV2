package Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
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
	String name = p.getName();
	MessageManager.getManager().msg(p, MessageType.INFO, "Добро пожаловать на сервер, "+ name);
	File players = new File(plugin.getDataFolder() + File.separator + "players.yml");
	FileConfiguration users = YamlConfiguration.loadConfiguration(players);
	List<String> list = users.getStringList("users");//govno ebanoe
	if(list.contains(p.getName())) return;
	{
		Home home = new Home(plugin); // Штука для установки точки дома
		list.add(p.getName());
		Location spawn = SpawnToLoc();
		p.teleport(spawn);
		Bukkit.broadcastMessage("§aПоприветствуем нового игрока §d§l" + p.getName()+"§a на §b§lSTR§a§lmine§a!");
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 600, 4), true);
		p.setBedSpawnLocation(spawn, true);
		home.locToConfig(p.getName(),spawn); // Установка точки дома на спауне
		try {
		StrPlayer spl = new StrPlayer(plugin);
		spl.setNickname(p.getName());
		spl.setPlayTime(System.currentTimeMillis());
		spl.setPlayerCfg(spl);
		} catch(NullPointerException e1) {
			e1.printStackTrace();
		}
		
	}
	users.set("users", list);
	try {
		users.save(players);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
	}

@EventHandler
public void mobProtect(CreatureSpawnEvent e) {
	Location loc = e.getEntity().getLocation();
	Location spawn = SpawnToLoc();
	int blockX = loc.getBlockX();
	int blockY = loc.getBlockY();
	int blockZ = loc.getBlockZ();
	int modX = Math.abs(blockX);
	int modY = Math.abs(blockY);
	int modZ = Math.abs(blockZ);
	int spawnModX =  Math.abs(spawn.getBlockX());
	int spawnModY =  Math.abs(spawn.getBlockY());
	int spawnModZ =  Math.abs(spawn.getBlockZ());
	if((modX-spawnModX<200)&&(modY-spawnModY<200)&&(modZ-spawnModZ<200)) {
		if(e.getEntityType()!= EntityType.PLAYER) {
			e.getEntity().setHealth(0);
		}
	}
	return;
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
	