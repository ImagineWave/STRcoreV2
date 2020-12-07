package Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	public static Main instance() {
		return instance;
	}
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Handler(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MuteChecker(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new vanish(this), this);
		getCommand("gm").setExecutor(new gm(this));
		getCommand("sethome").setExecutor(new home(this));
		getCommand("savespawn").setExecutor(new setspawn(this));
		getCommand("home").setExecutor(new tphome(this));
		getCommand("spawn").setExecutor(new tpSpawn(this));
		getCommand("tempmute").setExecutor(new MuteSetter(this));
		getCommand("pm").setExecutor(new PrivateMsgs(this));
		getCommand("vanish").setExecutor(new vanish(this));
		getCommand("broadcast").setExecutor(new broadcast(this));
		getCommand("sreload").setExecutor(new ReloadCMD(this));
		getCommand("endercheck").setExecutor(new Plugin.Inventory.EndInvCheck(this));
		getCommand("invcheck").setExecutor(new Plugin.Inventory.InvCheck(this));
		getCommand("invcheck").setExecutor(new Plugin.Inventory.InvCheck(this));
		Bukkit.getServer().getPluginManager().registerEvents(new Plugin.Fixes.Checkers(this), this);
		File homes = new File(getDataFolder() + File.separator + "homes.yml");
		
		if (!homes.exists()) {
			try {
				homes.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File players = new File(getDataFolder() + File.separator + "players.yml");
		if (!players.exists()) {
			try {
				players.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	public void onDisable() {
		
	}

	
}

