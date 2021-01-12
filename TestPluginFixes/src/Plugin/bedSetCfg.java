package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class bedSetCfg implements Listener{
	private Main plugin;
	public bedSetCfg() {
		this.plugin = plugin;
	}

	public bedSetCfg(Main main) {}

	@EventHandler
	public void PlayerToBed(PlayerBedEnterEvent e) {
		Player p = e.getPlayer();
		Location loc = e.getBed().getLocation();
		if (!loc.getWorld().getName().equalsIgnoreCase("world")) return;
 		locToConfig(p.getName(), loc);
		return;
	}

	public void locToConfig (String name, Location loc) {
		 File beds = new File(plugin.getDataFolder() + File.separator + "beds.yml");
		 FileConfiguration b = YamlConfiguration.loadConfiguration(beds);
		b.set("locations." + name + ".world", loc.getWorld().getName());
		b.set("locations." + name + ".x", loc.getBlockX());
		b.set("locations." + name + ".y", loc.getBlockY());
		b.set("locations." + name + ".z", loc.getBlockZ());
				try {
			b.save(beds);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }



}
