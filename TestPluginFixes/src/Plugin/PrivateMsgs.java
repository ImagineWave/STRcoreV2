package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Plugin.MessageManager.MessageType;
public class PrivateMsgs implements CommandExecutor{
	
	private Main plugin;

public PrivateMsgs(Main plugin) {
	this.plugin = plugin;
}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(isPlayerMuted(p)) {
			return true;
		}
		if (args.length == 0) {
			p.sendMessage("§6[§4ЛС§6] §cИспользование /pm <Игрок> <сообщение>");
			return true;
		}
		
		if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
			p.sendMessage("§6[§4ЛС§6] §cИгрок не в сети");
			return true;
		}
		Player t = Bukkit.getPlayerExact(args[0]);
		if(p.getName() == t.getName()) {
			p.sendMessage("§6[§4ЛС§6] §cВы не можете писать самому себе!");
			return true;
		}
		if (args.length == 1) {
			p.sendMessage("§6[§4ЛС§6] §cВведите ваше сообщение");
			return true;
		}
			String message = "";
			for (int i = 1; i != args.length; i++) {
				message += args[i] +" ";
			}
			p.sendMessage("§6[§4ЛС§6] §8[§bВЫ §a>>> §b"+t.getName()+"§8]: §6"+ message);
			t.sendMessage("§6[§4ЛС§6] §8[§b"+p.getName()+" §a>>> §b ВЫ§8]: §6"+ message);
			for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
				if (pls.hasPermission("str.spy")) {
					pls.sendMessage("§7[SPY]: "+p.getName()+" >>> "+ t.getName()+": "+ message);
				}
			}
			
			return true;
	}
	public boolean isPlayerMuted(Player p) {
		StrPlayer spl = new StrPlayer(p,plugin);
		Long duration = spl.getMuteTime();
		Long qtime = System.currentTimeMillis();
		String reason = spl.getMuteReason();
		String msgduration = formatDuration((duration-qtime)/1000);
		if(duration>qtime) {
				MessageManager.getManager().msg(p, MessageType.BAD, "У вас блокировка чата еще §6"+ msgduration + " §cсекунд по причине §6" + reason);
				return true;
			}
		return false;
	}
	public String formatDuration(Long time) {
		long hours = time/3600;
		long minutes = time%3600/60;
		long seconds = time%3600%60;
		String sthours = Long.toString(hours);
		String stminutes = Long.toString(minutes);
		String stseconds = Long.toString(seconds);
		String msgduration = "";
		if(hours != 0) msgduration += sthours + " час(ов) ";
		if(minutes != 0) msgduration += stminutes + " минут(а) ";
		if(seconds != 0) msgduration += stseconds + " секунд(а) ";
		return msgduration;
	}

}
