package Plugin.Fixes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(b.getY()>20) {
			ores.remove(p);
			return;
		}
		if(ores.get(p)!=null) {
			Bukkit.broadcastMessage(ores.toString());//ssssssssssssss
		if(ores.get(p).contains(b)) {
			Bukkit.broadcastMessage("Уже не новое");//ssssssssssssss
			return;
			}
		}
		if(b.getType().equals(Material.DIAMOND_ORE))return;
		if(p.hasPermission("str.admin")) return;
		if(isOreNear(b)) {
			Block ore = nearOreXYZ(b);
			ArrayList<Block> oreCluster = clusterDetect(ore,b);
			if(isClusterHidden(oreCluster,b)) {
				Bukkit.broadcastMessage("Подозрительно");//ssssssssssssss
				ores.put(p, oreCluster);
				Bukkit.broadcastMessage(ores.toString());
			}
			return;
		}
		
	}
	public boolean isOreNear(Block b) {
		ArrayList<Block> ores = new ArrayList<Block>();
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
	public ArrayList<Block> clusterDetect(Block b, Block broken) {
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
	public boolean isClusterHidden(ArrayList<Block> ores, Block broken) {
		Location brokenBlockLoc = broken.getLocation();
		int counter = 0;
		for(int i = 0; i<ores.size(); i++) {
			for(int x = -1; x < 2; x++) {
				for(int y = -1; y < 2; y++) {
					for (int z = -1; z < 2; z++) {
						if(ores.get(i).getRelative(x,y,z).getType().equals(Material.AIR) && ores.get(i).getRelative(x,y,z).getLocation()!= brokenBlockLoc) {
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
