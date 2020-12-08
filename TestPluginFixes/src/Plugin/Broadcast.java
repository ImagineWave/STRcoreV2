package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Plugin.MessageManager.MessageType;

public class Broadcast implements CommandExecutor {

	public Broadcast(Main main) {}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 if (!sender.hasPermission("str.broadcast")) {
	        	MessageManager.getManager().msg(sender, MessageType.BAD, "Ó âàñ íåò ïğàâ");
	            return true;
	        }
		 String message = "";
			for (int i = 0; i != args.length; i++) {
				message += args[i] +" ";
			}
			Bukkit.broadcastMessage("§r§a[§4§nÎáúÿâëåíèå§a]: §b"+ message);
		return true;
	}

}
