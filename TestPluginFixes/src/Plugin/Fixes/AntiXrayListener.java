package Plugin.Fixes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import Plugin.Main;

public class AntiXrayListener implements Listener{
	private Main plugin;
	public AntiXrayListener(Main plugin) {
		this.plugin = plugin;	
	}
	public HashMap<Player , ArrayList<Block>> ores = new HashMap<Player , ArrayList<Block>>();
	public HashMap<Player , ArrayList<Block>> oresNether = new HashMap<Player , ArrayList<Block>>();
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(!b.getWorld().getName().equalsIgnoreCase("world"))return;
		if(b.getY()>16) {
			ores.remove(p);
			return;
		}
		if(b.getType().equals(Material.DIAMOND_ORE))return;
		if(p.hasPermission("str.admin")) return;
		if(isOreNear(b)) {
			Block ore = nearOreXYZ(b);
			ArrayList<Block> oreCluster = clusterDetect(ore);
			if(ores.get(p)!=null) {
				if(ores.get(p).equals(oreCluster)) {
					return;
				}
			}
			if(isClusterHidden(oreCluster,b)) {
				ores.put(p, oreCluster);
				oreToConfig(p);
				if(isXrayEnabled(p)){
					p.sendMessage("ÁÀÍ");
				}
			}
			return;
		}
		nonOreToConfig(p);
		return;
	}
	@EventHandler
	public void onBlockBreakNether(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(!b.getWorld().getName().equalsIgnoreCase("world_nether"))return;
		if(b.getY()>22) {
			return;
		}
		if(b.getType().equals(Material.ANCIENT_DEBRIS))return;
		if(p.hasPermission("str.admin")) return;
		if(isOreNearNether(b)) {
		Block ore = nearOreXYZNether(b);
		ArrayList<Block> oreCluster = clusterDetectNether(ore);
		if(oresNether.get(p)!=null) {
			if(oresNether.get(p).equals(oreCluster)) {
				return;
			}
		}
		oresNether.put(p, oreCluster);
		oreToConfigNether(p);
		}
		nonOreToConfigNether(p);
		return;
	}
	public void oreToConfig(Player p) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		String name = p.getName();
		int ore = h.getInt("AntiXray." + name + ".diamondOre");
		ore++;
		h.set("AntiXray." + name + ".diamondOre", ore);
				try {
			h.save(homes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	public void oreToConfigNether(Player p) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		String name = p.getName();
		int ore = h.getInt("AntiXray." + name + ".ancientDerbis");
		ore++;
		h.set("AntiXray." + name + ".ancientDerbis", ore);
				try {
			h.save(homes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	public void nonOreToConfig(Player p) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		String name = p.getName();
		int block = h.getInt("AntiXray." + name + ".nonOre");
		block++;
		h.set("AntiXray." + name + ".nonOre", block);
				try {
			h.save(homes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	public void nonOreToConfigNether(Player p) {
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		String name = p.getName();
		int block = h.getInt("AntiXray." + name + ".nonOreNether");
		block++;
		h.set("AntiXray." + name + ".nonOreNether", block);
				try {
			h.save(homes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
	public boolean isXrayEnabled(Player p){
		File homes = new File(plugin.getDataFolder() + File.separator + "AntiXray.yml");
		FileConfiguration h = YamlConfiguration.loadConfiguration(homes);
		String name = p.getName();
		int ore = h.getInt("AntiXray." + name + ".diamondOre");
		int block = h.getInt("AntiXray." + name + ".nonOre");
		Double xrayValue = h.getDouble("AAXray");
		Double warnValue = h.getDouble("AWarnXray");
		if(ore>2) {
			Double playerValue = (double) (block/ore);
			if(playerValue<xrayValue) {
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban " + p.getName() + " §cXray violation §a"+ playerValue);	
				Bukkit.broadcastMessage("§7[§cÑÈÑÒÅÌÀ§7]: §6"+ p.getName()+" §4çàáëîêèðîâàí §açà èñïîëüçîâàíèå §6X-RAY");
				return true;
			}
			if(playerValue<warnValue) {
					int warns = h.getInt("AntiXray." + name + ".warn");
					if(warns==0){
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "lp user " + p.getName() + " parent add xray");	
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "kick " + p.getName() + " §cXray violation §a"+ playerValue);	
				Bukkit.broadcastMessage("§7[§cÑÈÑÒÅÌÀ§7]: §6"+ p.getName()+" §bïîäîçðåâàåòñÿ §aâ èñïîëüçîâàíèè §6X-RAY");
					}
					if(warns==2) {
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ban " + p.getName() + " §cXray violation §a"+ playerValue);	
						Bukkit.broadcastMessage("§7[§cÑÈÑÒÅÌÀ§7]: §6"+ p.getName()+" §4çàáëîêèðîâàí §açà èñïîëüçîâàíèå §6X-RAY");
						return true;
					}
					warns++;
					h.set("AntiXray." + name + ".warn", warns);
					try {
						h.save(homes);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				return false;
			}
		}
		return false;
	}
	public boolean isOreNear(Block b) {
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.DIAMOND_ORE)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean isOreNearNether(Block b) {
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.ANCIENT_DEBRIS)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public Block nearOreXYZ(Block b) {
		Block ore = b;
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.DIAMOND_ORE)) {
						ore = b.getRelative(x, y, z);
						return ore;
					}
				}
			}
		}
		return ore;
	}
	public Block nearOreXYZNether(Block b) {
		Block ore = b;
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.ANCIENT_DEBRIS)) {
						ore = b.getRelative(x, y, z);
						return ore;
					}
				}
			}
		}
		return ore;
	}
	public ArrayList<Block> clusterDetect(Block b) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(int x = -2; x < 3; x++) {
			for(int y = -2; y < 3; y++) {
				for (int z = -2; z < 3; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.DIAMOND_ORE)) {
						blocks.add(b.getRelative(x, y, z));
					}
				}
			}
		}
		return blocks;
	}
	public ArrayList<Block> clusterDetectNether(Block b) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(int x = -2; x < 3; x++) {
			for(int y = -2; y < 3; y++) {
				for (int z = -2; z < 3; z++) {
					if(b.getRelative(x, y, z).getType().equals(Material.ANCIENT_DEBRIS)) {
						blocks.add(b.getRelative(x, y, z));
					}
				}
			}
		}
		return blocks;
	}
	public boolean isClusterHidden(ArrayList<Block> ores, Block broken) {
		Location brokenBlockLoc = broken.getLocation();
		int counter = 0;
		for(int i = 0; i<ores.size(); i++) {
			for(int x = -1; x < 2; x++) {
				for(int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {
						if((ores.get(i).getRelative(x,y,z).getType().equals(Material.AIR) || ores.get(i).getRelative(x,y,z).getType().equals(Material.WATER)) && ores.get(i).getRelative(x,y,z).getLocation()!= brokenBlockLoc) {
							counter++;
						}
					}
				}
			}
		}
		if(counter==0) return true;
		return false;
	}
}
