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
		Bukkit.getServer().getPluginManager().registerEvents(new Plugin.Fixes.AntiXrayListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Plugin.Fixes.TntToggleCommand(this), this);
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
		getCommand("setprefix").setExecutor(new Plugin.meta.SetPrefix(this));
		getCommand("warn").setExecutor(new Plugin.Warns.WarnPlayer(this));
		getCommand("tnttoggle").setExecutor(new Plugin.Fixes.TntToggleCommand(this));
		//XRAY ПРИКОЛЫ
		getCommand("setxray").setExecutor(new Plugin.meta.SetXray(this));
		getCommand("xraylist").setExecutor(new Plugin.Fixes.XrayListCMD(this));
		//КЛАНОВЫЕ АНЕКДОТЫ
		getCommand("clancreate").setExecutor(new Plugin.meta.CreateClan(this));
		getCommand("claninvite").setExecutor(new Plugin.meta.ClanInvite(this));
		getCommand("clanaccept").setExecutor(new Plugin.meta.ClanAccept(this));
		getCommand("clandecline").setExecutor(new Plugin.meta.ClanDecline(this));
		getCommand("clanleave").setExecutor(new Plugin.meta.ClanLeave(this));
		getCommand("clanmodify").setExecutor(new Plugin.meta.ClanModify(this));
		getCommand("clanhelp").setExecutor(new Plugin.meta.ClanHelp(this));
		getCommand("clanchat").setExecutor(new Plugin.meta.ClanChat(this));
		getCommand("clanlist").setExecutor(new Plugin.meta.ClanList(this));
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

