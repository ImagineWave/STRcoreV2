package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * @author 600798
 *
 */
public class StrPlayer {
	
	private Main plugin;
	public StrPlayer(Main plugin) {
		this.plugin = plugin;
	}
	
	public StrPlayer(String ip, boolean banned, long banTime, String banReason, String bannedBy, boolean muted, long muteTime,
			String muteReason, String mutedBy, boolean flying, String flyReason, boolean invurable, boolean vanished, boolean saturated,
			long playTime) {
		this.ip = ip;
		this.banned = banned;
		this.banTime = banTime;
		this.banReason = banReason;
		this.bannedBy = bannedBy;
		this.muted = muted;
		this.muteTime = muteTime;
		this.muteReason = muteReason;
		this.mutedBy = mutedBy;
		this.flying = flying;
		this.flyReason = flyReason;
		this.invurable = invurable;
		this.vanished = vanished;
		this.saturated = saturated;
		this.playTime = playTime;
		
		
	}
	
	public StrPlayer(Player p, Main plugin) {
		this.plugin = plugin;
		StrPlayer spl = getPlayerCfg(p.getName());
		this.nickname = p.getName();
		this.ip = spl.getIp();
		this.banned = spl.getBanned();
		this.banTime = spl.getBanTime();
		this.banReason = spl.getBanReason();
		this.bannedBy = spl.getBannedBy();
		this.muted = spl.getMuted();
		this.muteTime = spl.getMuteTime();
		this.muteReason = spl.getMuteReason();
		this.mutedBy = spl.getMutedBy();
		this.flying = spl.getFlying();
		this.flyReason = spl.getFlyReason();
		this.invurable = spl.getInvurable();
		this.vanished = spl.getVanished();
		this.saturated = spl.getSaturated();
		this.playTime = spl.getPlayTime();
	}



	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Boolean getBanned() {
		return banned;
	}
	public void setBanned(Boolean banned) {
		this.banned = banned;
	}
	public long getBanTime() {
		return banTime;
	}
	public void setBanTime(long banTime) {
		this.banTime = banTime;
	}
	public String getBanReason() {
		return banReason;
	}
	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}
	public Boolean getMuted() {
		return muted;
	}
	public void setMuted(Boolean muted) {
		this.muted = muted;
	}
	public long getMuteTime() {
		return muteTime;
	}
	public void setMuteTime(long muteTime) {
		this.muteTime = muteTime;
	}
	public String getMuteReason() {
		return muteReason;
	}
	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}
	public Boolean getFlying() {
		return flying;
	}
	public void setFlying(Boolean flying) {
		this.flying = flying;
	}
	public String getFlyReason() {
		return flyReason;
	}
	public void setFlyReason(String flyReason) {
		this.flyReason = flyReason;
	}
	public Boolean getInvurable() {
		return invurable;
	}
	public void setInvurable(Boolean invurable) {
		this.invurable = invurable;
	}
	public Boolean getVanished() {
		return vanished;
	}
	public void setVanished(Boolean vanished) {
		this.vanished = vanished;
	}
	public Boolean getSaturated() {
		return saturated;
	}
	public void setSaturated(Boolean saturated) {
		this.saturated = saturated;
	}
	public long getPlayTime() {
		return playTime;
	}
	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}
	public String getBannedBy() {
		return bannedBy;
	}

	public void setBannedBy(String bannedBy) {
		this.bannedBy = bannedBy;
	}

	public String getMutedBy() {
		return mutedBy;
	}

	public void setMutedBy(String mutedBy) {
		this.mutedBy = mutedBy;
	}


	
	// --------------------------------------------------------
	
	
	private String nickname = "Default";
	private String ip = "";
	private Boolean banned = false;
	private long banTime = 0;
	private String banReason = "§4Ваш аккаунт был заблокирован";
	private String bannedBy = "Opperator";
	private Boolean muted = false;
	private long muteTime = 0;
	private String muteReason = "Вы были заглушены";
	private String mutedBy = "Opperator";
	private Boolean flying = false;
	private String flyReason = "";
	private Boolean invurable = false;
	private Boolean vanished = false;
	private Boolean saturated = false;
	private long playTime = 0;
	
	// --------------------------------------------------------
	public void setPlayerCfg(StrPlayer p) {
		File cfg = new File(plugin.getDataFolder() + File.separator + "StrPlayers.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(cfg);
		//f.set(p.getNickname()+"."+p.getIp() );
		//h.set("locations." + name + ".x", loc.getBlockX());
		f.set(nickname+".ip",ip);
		f.set(nickname+".banned",banned);
		f.set(nickname+".banTime",banTime);
		f.set(nickname+".banReason",banReason);
		f.set(nickname+".bannedBy",bannedBy);
		f.set(nickname+".muted",muted);
		f.set(nickname+".muteTime",muteTime);
		f.set(nickname+".muteReason",muteReason);
		f.set(nickname+".mutedBy",mutedBy);
		f.set(nickname+".flying",flying);
		f.set(nickname+".flyReason",flyReason);
		f.set(nickname+".invurable",invurable);
		f.set(nickname+".vanished",vanished);
		f.set(nickname+".saturated",saturated);
		f.set(nickname+".playTime",playTime);
		
		try {
			f.save(cfg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public StrPlayer getPlayerCfg(String name) {
		File cfg = new File(plugin.getDataFolder() + File.separator + "StrPlayers.yml");
		FileConfiguration f = YamlConfiguration.loadConfiguration(cfg);
		//h.getDouble("locations." + name + ".x"),
		StrPlayer p = new StrPlayer(
				f.getString(name+".ip"),
				f.getBoolean(name+".banned"),
				f.getLong(name+".banTime"),
				f.getString(name+".banReason"),
				f.getString(name+".bannedBy"),
				f.getBoolean(name+".muted"),
				f.getLong(name+".muteTime"),
				f.getString(name+".muteReason"),
				f.getString(name+".mutedBy"),
				f.getBoolean(name+".flying"),
				f.getString(name+".flyReason"),
				f.getBoolean(name+".invurable"),
				f.getBoolean(name+".vanished"),
				f.getBoolean(name+".saturated"),
				f.getLong(name+".playTime"));
		return p;
	}
	
	
	
	
}