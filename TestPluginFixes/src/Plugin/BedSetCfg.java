package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.ArrayList;
public class BedSetCfg implements Listener{
	private Main plugin;
	public BedSetCfg(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerToBed(PlayerBedEnterEvent e) {
		Player p = e.getPlayer();
		Location loc = e.getBed().getLocation();
		if (!loc.getWorld().getName().equalsIgnoreCase("world")) return;
 		locToConfig(p.getName(), loc);
		return;
	}
	@EventHandler
	public void PlayerClickBed(PlayerInteractEvent e) {
		if (e.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
		ArrayList<Material> bed = new ArrayList<Material>();
		bed.add(Material.BLACK_BED);
		bed.add(Material.CYAN_BED);
		bed.add(Material.BLUE_BED);
		bed.add(Material.BROWN_BED);
		bed.add(Material.GRAY_BED);
		bed.add(Material.GREEN_BED);
		bed.add(Material.LIGHT_BLUE_BED);
		bed.add(Material.LIGHT_GRAY_BED);
		bed.add(Material.LIME_BED);
		bed.add(Material.LIME_BED);
		bed.add(Material.ORANGE_BED);
		bed.add(Material.PINK_BED);
		bed.add(Material.PURPLE_BED);
		bed.add(Material.RED_BED);
		bed.add(Material.WHITE_BED);
		bed.add(Material.YELLOW_BED);
		if(!bed.contains(e.getClickedBlock().getType())) return;
		if(e.getClickedBlock().getLocation().getWorld().getName()!="world") return;
		Player p = e.getPlayer();
		Location loc = e.getClickedBlock().getLocation();
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
