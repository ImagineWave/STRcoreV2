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
		Bukkit.getServer().getPluginManager().registerEvents(new Vanish(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BedSetCfg(this), this);
		getCommand("bedtp").setExecutor(new BedTp(this));
		getCommand("gm").setExecutor(new Gm(this));
		getCommand("sethome").setExecutor(new Home(this));
		getCommand("savespawn").setExecutor(new SetSpawn(this));
		getCommand("home").setExecutor(new TpHome(this));
		getCommand("spawn").setExecutor(new TpSpawn(this));
		getCommand("tempmute").setExecutor(new MuteSetter(this));
		getCommand("pm").setExecutor(new PrivateMsgs(this));
		getCommand("vanish").setExecutor(new Vanish(this));
		getCommand("broadcast").setExecutor(new Broadcast(this));
		getCommand("sreload").setExecutor(new ReloadCMD(this));
		getCommand("endercheck").setExecutor(new Plugin.Inventory.EndInvCheck(this));
		getCommand("invcheck").setExecutor(new Plugin.Inventory.InvCheck(this));
		getCommand("crash").setExecutor(new Plugin.Inventory.Crasher(this));
		Bukkit.getServer().getPluginManager().registerEvents(new Plugin.Fixes.Checkers(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Plugin.Fixes.AntiXrayListener(this), this);
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

