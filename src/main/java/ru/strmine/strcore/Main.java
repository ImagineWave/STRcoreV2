package ru.strmine.strcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import plugin.fixes.*;
import plugin.inventory.*;
import plugin.meta.*;
import plugin.warns.*;
import ru.strmine.strcore.fixes.AntiXrayListener;
import ru.strmine.strcore.fixes.TntToggleCommand;
import ru.strmine.strcore.fixes.XrayListCMD;
import ru.strmine.strcore.inventory.Crasher;
import ru.strmine.strcore.inventory.EndInvCheck;
import ru.strmine.strcore.inventory.InvCheck;
import ru.strmine.strcore.meta.*;
import ru.strmine.strcore.warns.WarnPlayer;

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
		Bukkit.getServer().getPluginManager().registerEvents(new AntiXrayListener(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new TntToggleCommand(this), this);
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
		getCommand("endercheck").setExecutor(new EndInvCheck(this));
		getCommand("invcheck").setExecutor(new InvCheck(this));
		getCommand("crash").setExecutor(new Crasher(this));
		getCommand("setprefix").setExecutor(new SetPrefix(this));
		getCommand("warn").setExecutor(new WarnPlayer(this));
		getCommand("tnttoggle").setExecutor(new TntToggleCommand(this));
		//XRAY ПРИКОЛЫ
		getCommand("setxray").setExecutor(new SetXray(this));
		getCommand("xraylist").setExecutor(new XrayListCMD(this));
		//КЛАНОВЫЕ АНЕКДОТЫ
		getCommand("clancreate").setExecutor(new CreateClan(this));
		getCommand("claninvite").setExecutor(new ClanInvite(this));
		getCommand("clanaccept").setExecutor(new ClanAccept(this));
		getCommand("clandecline").setExecutor(new ClanDecline(this));
		getCommand("clanleave").setExecutor(new ClanLeave(this));
		getCommand("clanmodify").setExecutor(new ClanModify(this));
		getCommand("clanhelp").setExecutor(new ClanHelp(this));
		getCommand("clanchat").setExecutor(new ClanChat(this));
		getCommand("clanlist").setExecutor(new ClanList(this));
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

