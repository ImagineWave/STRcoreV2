package ru.strmine.strcore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {

	public Broadcast(Main main) {}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 if (!sender.hasPermission("str.broadcast")) {
	        	MessageManager.getManager().msg(sender, MessageManager.MessageType.BAD, "У вас нет прав");
	            return true;
	        }
		 String message = "";
			for (int i = 0; i != args.length; i++) {
				message += args[i] +" ";
			}
			Bukkit.broadcastMessage("§a[§4§nОбъявление§a]: §b"+ message);
		return true;
	}

}
