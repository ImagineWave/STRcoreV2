package main.java.src.Plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import main.java.src.Plugin.MessageManager.MessageType;

public class MuteChecker implements Listener{

	private Main plugin;
	
	public MuteChecker(Main plugin) {
		this.plugin = plugin;
	}

@EventHandler
public void MuteCheckers2(AsyncPlayerChatEvent e) {
	Player p = e.getPlayer();
	StrPlayer spl = new StrPlayer(p,plugin);
	Long duration = spl.getMuteTime();
	Long qtime = System.currentTimeMillis();
	String reason = spl.getMuteReason();
	String msgduration = formatDuration2((duration-qtime)/1000);
	if(duration>qtime) {
			e.setCancelled(true);
			MessageManager.getManager().msg(p, MessageType.BAD, "У вас блокировка чата еще §6"+ msgduration + " §c по причине §6" + reason);
			return;
		}
}



@EventHandler
public void guestToMute(AsyncPlayerChatEvent e) {
	Player p = e.getPlayer();
	if(!p.hasPermission("str.guest")) return;
	if(p.isOp()) return;
	StrPlayer spl = new StrPlayer(p,plugin);
	spl.setMuted(true);
	spl.setMuteReason("Задержка чата");
	spl.setMuteTime(System.currentTimeMillis()+5000);
	spl.setMutedBy("console");
	spl.setPlayerCfg(spl);
}



public String formatDuration(Long time) {
	long hours = time/3600;
	long minutes = time%3600/60;
	long seconds = time%3600%60;
	String sthours = Long.toString(hours);
	String stminutes = Long.toString(minutes);
	String stseconds = Long.toString(seconds);
	String msgduration = "";
	if(hours != 0) msgduration += sthours + " hour(s) ";
	if(minutes != 0) msgduration += stminutes + " minute(s) ";
	if(seconds != 0) msgduration += stseconds + " second(s) ";
	return msgduration;
}
public String formatDuration2(Long time) {
	long days = time/86400;
	long hours = time%86400/3600;
	long minutes = time%86400%3600/60;
	long seconds = time%86400%3600%60;
	String stdays = Long.toString(days);
	String sthours = Long.toString(hours);
	String stminutes = Long.toString(minutes);
	String stseconds = Long.toString(seconds);
	String msgduration = "§6";
	if(days != 0) msgduration += stdays + " §4Day(s)§6 ";
	if(hours != 0) msgduration += sthours + " §4Hour(s)§6 ";
	if(minutes != 0) msgduration += stminutes + " §4Minute(s)§6 ";
	if(seconds != 0) msgduration += stseconds + " §4Second(s) ";
	return msgduration;
}
}
