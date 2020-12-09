package Plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author 600798
 *
 */
public class StrPlayer {
	
	private Main plugin;
	public StrPlayer(Main plugin) {
		this.plugin = plugin;
	}
	
	public StrPlayer(String ip, boolean boolean1, long long1, String string2, boolean boolean2, long long2,
			String string3, boolean boolean3, String string4, boolean boolean4, boolean boolean5, boolean boolean6,
			long long3) {
		// TODO Auto-generated constructor stub
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
	
	
	// --------------------------------------------------------
	
	
	private String nickname = "Default";
	private String ip = "";
	private Boolean banned = false;
	private long banTime = 0;
	private String banReason = "§4Ваш аккаунт был заблокирован";
	private Boolean muted = false;
	private long muteTime = 0;
	private String muteReason = "Вы были заглушены";
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
		f.set(nickname+".muted",muted);
		f.set(nickname+".muteTime",muteTime);
		f.set(nickname+".muteReason",muteReason);
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
				f.getBoolean(nickname+".banned"),
				f.getLong(nickname+".banTime"),
				f.getString(nickname+".banReason"),
				f.getBoolean(nickname+".muted"),
				f.getLong(nickname+".muteTime"),
				f.getString(nickname+".muteReason"),
				f.getBoolean(nickname+".flying"),
				f.getString(nickname+".flyReason"),
				f.getBoolean(nickname+".invurable"),
				f.getBoolean(nickname+".vanished"),
				f.getBoolean(nickname+".saturated"),
				f.getLong(nickname+".playTime"));
		return p;
	}
	
	
	
	
}