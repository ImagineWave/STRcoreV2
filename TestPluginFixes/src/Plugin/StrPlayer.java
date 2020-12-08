package Plugin;


public class StrPlayer {
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
	
	
	
	
	
	
	
}
