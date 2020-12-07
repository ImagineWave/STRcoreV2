package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class PrivateMsgs implements CommandExecutor{

public PrivateMsgs(Main plugin) {}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
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

}
